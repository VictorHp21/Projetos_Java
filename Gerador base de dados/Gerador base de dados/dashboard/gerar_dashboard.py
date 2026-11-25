"""
Gera o dashboard interativo da TechNova Informática (Etapa 4 da Prova Prática de BI).

Lê a base tratada (dados_tratados/) e os resultados da análise
(dados_tratados/analise/), embute os dados num único arquivo HTML e salva
dashboard/dashboard.html. O arquivo abre em qualquer navegador, sem internet
e sem servidor: os filtros e gráficos são recalculados no próprio navegador.

Como rodar (na pasta "Gerador base de dados", depois do ETL e da análise):
    python dashboard/gerar_dashboard.py
"""

from __future__ import annotations

import json
from datetime import datetime
from pathlib import Path

import pandas as pd

RAIZ = Path(__file__).resolve().parent.parent
PASTA_DADOS = RAIZ / "dados_tratados"
PASTA_ANALISE = PASTA_DADOS / "analise"
PASTA_DASH = Path(__file__).resolve().parent


def ler(nome: str, pasta: Path = PASTA_DADOS, **kw) -> pd.DataFrame:
    caminho = pasta / f"{nome}.csv"
    if not caminho.exists():
        raise SystemExit(f"Arquivo não encontrado: {caminho}\n"
                         "Rode antes: python etl/etl.py  e  python analise/analise.py")
    return pd.read_csv(caminho, sep=";", **kw)


def indexar(serie: pd.Series, ordem: list | None = None):
    """Troca textos repetidos por números (índices) para deixar o HTML leve."""
    valores = ordem if ordem is not None else sorted(serie.dropna().unique().tolist())
    mapa = {v: i for i, v in enumerate(valores)}
    return valores, serie.map(mapa).astype(int).tolist()


def brl(v: float) -> str:
    return "R$ " + f"{v:,.0f}".replace(",", ".")


def pct(v: float) -> str:
    return f"{v:.1f}%".replace(".", ",")


def montar_dados() -> dict:
    fv = ler("fato_vendas", parse_dates=["data_venda"]).sort_values("id_venda").reset_index(drop=True)
    fi = ler("fato_itens")
    pr = ler("dim_produtos")
    ve = ler("dim_vendedores")

    meses = sorted(fv["ano_mes"].unique().tolist())
    canais, i_canal = indexar(fv["canal"])
    regioes, i_regiao = indexar(fv["regiao"], ["Norte", "Nordeste", "Centro-Oeste", "Sudeste", "Sul"])
    ufs, i_uf = indexar(fv["estado"])
    pagamentos, i_pag = indexar(fv["forma_pagamento"], ["PIX", "Crédito", "Débito", "Boleto", "Dinheiro"])
    status, i_status = indexar(fv["status"], ["Concluída", "Pendente", "Cancelada"])
    _, i_mes = indexar(fv["ano_mes"], meses)
    vend_ids = ve.sort_values("id_vendedor")["id_vendedor"].tolist()
    _, i_vend = indexar(fv["id_vendedor"], vend_ids)

    categorias = sorted(pr["categoria"].unique().tolist())
    produtos = pr.sort_values("id_produto")
    mapa_cat = {c: i for i, c in enumerate(categorias)}
    mapa_prod = {pid: i for i, pid in enumerate(produtos["id_produto"])}
    pos_venda = {vid: i for i, vid in enumerate(fv["id_venda"])}

    # Cada pedido: [mês, canal, região, UF, pagamento, status, vendedor, cliente]
    pedidos = [list(t) for t in zip(i_mes, i_canal, i_regiao, i_uf, i_pag, i_status, i_vend,
                                    fv["id_cliente"].astype(int).tolist())]
    # Cada item: [pedido, produto, quantidade, subtotal, lucro bruto]
    itens = [[pos_venda[v], mapa_prod[p], int(q), round(float(s), 2), round(float(l), 2)]
             for v, p, q, s, l in zip(fi["id_venda"], fi["id_produto"], fi["quantidade"],
                                      fi["subtotal"], fi["lucro_bruto"])]

    # Resultados da análise (período completo, não mudam com os filtros)
    rfm = ler("rfm_segmentos", PASTA_ANALISE)
    previsao = ler("previsao", PASTA_ANALISE)
    mensal = ler("mensal", PASTA_ANALISE)
    prod_an = ler("produtos", PASTA_ANALISE).sort_values("cobertura_meses").head(8)
    kpis = ler("kpis", PASTA_ANALISE).set_index("kpi")
    canais_an = ler("canais", PASTA_ANALISE).set_index("canal")
    pag_an = ler("pagamentos", PASTA_ANALISE).set_index("forma_pagamento")
    cat_an = ler("categorias", PASTA_ANALISE)

    return {
        "gerado_em": datetime.now().strftime("%d/%m/%Y %H:%M"),
        "meses": meses, "canais": canais, "regioes": regioes, "ufs": ufs,
        "pagamentos": pagamentos, "status": status, "categorias": categorias,
        "produtos": [[n, mapa_cat[c]] for n, c in zip(produtos["nome"], produtos["categoria"])],
        "vendedores": [ve.set_index("id_vendedor").loc[i, "nome"] for i in vend_ids],
        "pedidos": pedidos, "itens": itens,
        "rfm": rfm[["segmento", "clientes", "recencia_media", "frequencia_media", "valor_medio",
                    "part_receita_%"]].round(2).values.tolist(),
        "previsao": previsao.round(2).values.tolist(),
        "historico": mensal.loc[mensal["ano_mes"] <= "2026-09", ["ano_mes", "receita"]].round(2).values.tolist(),
        "estoque": prod_an[["produto", "estoque", "venda_mensal_media", "cobertura_meses"]].round(2).values.tolist(),
        "insights": textos_insights(kpis, canais_an, pag_an, cat_an, rfm, previsao),
    }


def textos_insights(kpis, canais, pag, cat, rfm, previsao) -> dict:
    corp = canais.loc["Vendas Corporativas"]
    b = pag.loc["Boleto"]
    outras = pag.drop(index="Boleto")
    taxa_outras = outras["canceladas"].sum() / outras["pedidos"].sum() * 100
    camp = rfm.set_index("segmento").loc["Campeões"]
    inat = rfm.set_index("segmento").loc["Inativos"]
    baixa = cat.sort_values("margem_%").head(3)
    nov = previsao.set_index("ano_mes").loc["2026-11", "previsao"]
    return {
        "insights": [
            ["Canal Corporativo sustenta a receita",
             f"Com {pct(corp['part_pedidos_%'])} dos pedidos, gera {pct(corp['part_receita_%'])} da receita; "
             f"ticket médio de {brl(corp['ticket_medio'])}, cerca de 4× os demais canais."],
            ["Boleto concentra os cancelamentos",
             f"Cancela {pct(b['taxa_cancelamento_%'])} dos pedidos, contra {pct(taxa_outras)} nas outras formas "
             f"(diferença confirmada por teste qui-quadrado), e já custou {brl(b['receita_perdida'])} em vendas."],
            ["Poucos clientes, muita receita",
             f"Os {int(camp['clientes'])} clientes Campeões geram {pct(camp['part_receita_%'])} da receita; "
             f"{int(inat['clientes'])} clientes estão inativos há mais de um ano, em média."],
            ["Margem desigual entre categorias",
             "Margem bruta de " + pct(kpis.loc["K5", "valor"]) + " no total, mas "
             + ", ".join(f"{c} ({pct(m)})" for c, m in zip(baixa["categoria"], baixa["margem_%"]))
             + " ficam bem abaixo da média."],
            ["Fim de ano decide o resultado",
             f"Novembro e dezembro são os maiores meses; a previsão para nov/2026 é de {brl(nov)}. "
             f"{int(kpis.loc['K12', 'valor'])} produtos têm estoque para menos de 1 mês."],
        ],
        "recomendacoes": [
            ["Reduzir o cancelamento do boleto",
             "Dar desconto no PIX (que é imediato) e lembrete automático antes do vencimento do boleto. "
             "Meta: levar a taxa do boleto de " + pct(b["taxa_cancelamento_%"]) + " para perto de 15%."],
            ["Proteger e expandir o Corporativo",
             "Gerente de contas para as empresas que mais compram e prospecção ativa de novas contas, "
             "já que cada pedido corporativo vale cerca de 4 pedidos do varejo."],
            ["Programa de relacionamento por segmento",
             "Benefícios VIP para Campeões, fidelidade para Fiéis e campanha de reativação com cupom "
             "para Inativos e para os clientes que nunca compraram."],
            ["Rever preço e custo das categorias de margem baixa",
             "Renegociar com fornecedores ou ajustar preços em " + ", ".join(baixa["categoria"])
             + " e priorizar a venda casada com itens de margem alta."],
            ["Repor estoque crítico antes da Black Friday",
             "Comprar já os produtos com cobertura menor que 1 mês, dimensionando pelo pico previsto para novembro."],
        ],
    }


def main():
    dados = montar_dados()
    modelo = (PASTA_DASH / "template.html").read_text(encoding="utf-8")
    json_dados = json.dumps(dados, ensure_ascii=False, separators=(",", ":"))
    html = modelo.replace("/*__DADOS__*/null", json_dados)
    saida = PASTA_DASH / "dashboard.html"
    saida.write_text(html, encoding="utf-8")
    print(f"Dashboard gerado: {saida}  ({len(html) / 1024:.0f} KB)")
    print("Abra o arquivo no navegador (dois cliques).")


if __name__ == "__main__":
    main()
