"""
ETL — TechNova Informática (Prova Prática de BI)

Lê os CSVs gerados pelo gerador Java, valida a qualidade dos dados,
limpa e padroniza, cria colunas derivadas, integra as tabelas e grava
a base tratada usada na análise e no dashboard.

Como rodar (na pasta "Gerador base de dados"):
    pip install pandas
    python etl/etl.py

Entrada:  arquivos_csv/*.csv
Saída:    dados_tratados/*.csv
          relatorios/relatorio_validacao.md
"""

from __future__ import annotations

import re
import sys
from datetime import datetime
from pathlib import Path

import pandas as pd

# ---------------------------------------------------------------------------
# Caminhos (relativos à pasta do projeto, independentemente de onde se roda)
# ---------------------------------------------------------------------------
RAIZ = Path(__file__).resolve().parent.parent
PASTA_ENTRADA = RAIZ / "arquivos_csv"
PASTA_SAIDA = RAIZ / "dados_tratados"
PASTA_RELATORIOS = RAIZ / "relatorios"

# Data de referência da base (mesma do gerador: Parametros.DATA_REFERENCIA)
DATA_REFERENCIA = pd.Timestamp("2026-10-31")

STATUS_VALIDOS = {"Concluída", "Pendente", "Cancelada"}
PAGAMENTOS_VALIDOS = {"PIX", "Crédito", "Débito", "Boleto", "Dinheiro"}

REGIAO_POR_UF = {
    "AC": "Norte", "AM": "Norte", "AP": "Norte", "PA": "Norte", "RO": "Norte", "RR": "Norte", "TO": "Norte",
    "AL": "Nordeste", "BA": "Nordeste", "CE": "Nordeste", "MA": "Nordeste", "PB": "Nordeste",
    "PE": "Nordeste", "PI": "Nordeste", "RN": "Nordeste", "SE": "Nordeste",
    "DF": "Centro-Oeste", "GO": "Centro-Oeste", "MS": "Centro-Oeste", "MT": "Centro-Oeste",
    "ES": "Sudeste", "MG": "Sudeste", "RJ": "Sudeste", "SP": "Sudeste",
    "PR": "Sul", "RS": "Sul", "SC": "Sul",
}

DIAS_SEMANA = ["Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"]


# ===========================================================================
# 1. EXTRAÇÃO
# ===========================================================================
def extrair() -> dict[str, pd.DataFrame]:
    """Lê os 6 CSVs como texto (dtype=str) para que nada seja convertido
    'no escuro': os tipos são definidos explicitamente na etapa de limpeza."""
    tabelas = {}
    for nome in ["categorias", "clientes", "vendedores", "produtos", "vendas", "itens_venda"]:
        caminho = PASTA_ENTRADA / f"{nome}.csv"
        if not caminho.exists():
            sys.exit(f"ERRO: arquivo não encontrado: {caminho}\nRode o gerador Java antes do ETL.")
        tabelas[nome] = pd.read_csv(caminho, sep=";", encoding="utf-8-sig", dtype=str, keep_default_na=False)
        print(f"  {nome:<12} {len(tabelas[nome]):>6} linhas lidas")
    return tabelas


# ===========================================================================
# 2. LIMPEZA E PADRONIZAÇÃO
# ===========================================================================
def _texto(serie: pd.Series) -> pd.Series:
    """Remove espaços nas pontas e espaços duplicados; vazio vira nulo."""
    s = serie.str.strip().str.replace(r"\s+", " ", regex=True)
    return s.mask(s == "")


def limpar(t: dict[str, pd.DataFrame]) -> dict[str, pd.DataFrame]:
    """Define tipos, padroniza textos e registra quantos valores não puderam
    ser convertidos (viram nulos e são apontados na validação)."""
    c = {}

    cat = t["categorias"].copy()
    cat["id_categoria"] = pd.to_numeric(cat["id_categoria"], errors="coerce").astype("Int64")
    cat["nome"] = _texto(cat["nome"])
    cat["descricao"] = _texto(cat["descricao"])
    c["categorias"] = cat

    cli = t["clientes"].copy()
    cli["id_cliente"] = pd.to_numeric(cli["id_cliente"], errors="coerce").astype("Int64")
    cli["nome"] = _texto(cli["nome"]).str.title()
    cli["email"] = _texto(cli["email"]).str.lower()
    cli["cidade"] = _texto(cli["cidade"])
    cli["estado"] = _texto(cli["estado"]).str.upper()
    cli["data_cadastro"] = pd.to_datetime(cli["data_cadastro"], format="%Y-%m-%d", errors="coerce")
    c["clientes"] = cli

    ven = t["vendedores"].copy()
    ven["id_vendedor"] = pd.to_numeric(ven["id_vendedor"], errors="coerce").astype("Int64")
    ven["nome"] = _texto(ven["nome"]).str.title()
    ven["setor"] = _texto(ven["setor"])
    ven["data_admissao"] = pd.to_datetime(ven["data_admissao"], format="%Y-%m-%d", errors="coerce")
    c["vendedores"] = ven

    pro = t["produtos"].copy()
    for col in ["id_produto", "id_categoria", "estoque"]:
        pro[col] = pd.to_numeric(pro[col], errors="coerce").astype("Int64")
    for col in ["preco", "custo"]:
        pro[col] = pd.to_numeric(pro[col], errors="coerce")
    pro["nome"] = _texto(pro["nome"])
    c["produtos"] = pro

    vda = t["vendas"].copy()
    for col in ["id_venda", "id_cliente", "id_vendedor"]:
        vda[col] = pd.to_numeric(vda[col], errors="coerce").astype("Int64")
    vda["valor_total"] = pd.to_numeric(vda["valor_total"], errors="coerce")
    vda["data_venda"] = pd.to_datetime(vda["data_venda"], format="%Y-%m-%d %H:%M:%S", errors="coerce")
    vda["forma_pagamento"] = _texto(vda["forma_pagamento"])
    vda["status"] = _texto(vda["status"])
    c["vendas"] = vda

    itv = t["itens_venda"].copy()
    for col in ["id_item", "id_venda", "id_produto", "quantidade"]:
        itv[col] = pd.to_numeric(itv[col], errors="coerce").astype("Int64")
    for col in ["preco_unitario", "subtotal"]:
        itv[col] = pd.to_numeric(itv[col], errors="coerce")
    c["itens_venda"] = itv

    return c


# ===========================================================================
# 3. VALIDAÇÃO DA QUALIDADE
# ===========================================================================
class Validador:
    """Guarda o resultado de cada checagem para montar o relatório."""

    def __init__(self):
        self.resultados: list[dict] = []

    def checar(self, dimensao: str, descricao: str, problemas: int, total: int, critico: bool = True):
        self.resultados.append({
            "dimensao": dimensao,
            "descricao": descricao,
            "problemas": int(problemas),
            "total": int(total),
            "ok": problemas == 0,
            "critico": critico,
        })

    @property
    def falhas_criticas(self) -> int:
        return sum(1 for r in self.resultados if not r["ok"] and r["critico"])


def validar(d: dict[str, pd.DataFrame]) -> Validador:
    v = Validador()
    cat, cli, ven, pro, vda, itv = (d[k] for k in
                                    ["categorias", "clientes", "vendedores", "produtos", "vendas", "itens_venda"])

    # --- Completude: valores ausentes ou que não puderam ser convertidos ---
    for nome, df in d.items():
        v.checar("Completude", f"{nome}: células nulas/inválidas", df.isna().sum().sum(), df.size)

    # --- Unicidade: chaves primárias duplicadas ---
    chaves = {"categorias": "id_categoria", "clientes": "id_cliente", "vendedores": "id_vendedor",
              "produtos": "id_produto", "vendas": "id_venda", "itens_venda": "id_item"}
    for nome, chave in chaves.items():
        v.checar("Unicidade", f"{nome}: chave primária ({chave}) duplicada",
                 d[nome][chave].duplicated().sum(), len(d[nome]))
    v.checar("Unicidade", "clientes: e-mail duplicado", cli["email"].duplicated().sum(), len(cli))
    v.checar("Unicidade", "vendedores: nome duplicado", ven["nome"].duplicated().sum(), len(ven), critico=False)
    v.checar("Unicidade", "itens_venda: mesmo produto repetido na mesma venda",
             itv.duplicated(["id_venda", "id_produto"]).sum(), len(itv))
    v.checar("Unicidade", "linhas totalmente duplicadas (todas as tabelas)",
             sum(df.duplicated().sum() for df in d.values()), sum(len(df) for df in d.values()))

    # --- Integridade referencial: chaves estrangeiras órfãs ---
    v.checar("Integridade", "produtos → categorias (id_categoria inexistente)",
             (~pro["id_categoria"].isin(cat["id_categoria"])).sum(), len(pro))
    v.checar("Integridade", "vendas → clientes (id_cliente inexistente)",
             (~vda["id_cliente"].isin(cli["id_cliente"])).sum(), len(vda))
    v.checar("Integridade", "vendas → vendedores (id_vendedor inexistente)",
             (~vda["id_vendedor"].isin(ven["id_vendedor"])).sum(), len(vda))
    v.checar("Integridade", "itens_venda → vendas (id_venda inexistente)",
             (~itv["id_venda"].isin(vda["id_venda"])).sum(), len(itv))
    v.checar("Integridade", "itens_venda → produtos (id_produto inexistente)",
             (~itv["id_produto"].isin(pro["id_produto"])).sum(), len(itv))
    v.checar("Integridade", "vendas sem nenhum item", (~vda["id_venda"].isin(itv["id_venda"])).sum(), len(vda))

    # --- Validade: domínios e faixas ---
    v.checar("Validade", "vendas: status fora de {Concluída, Pendente, Cancelada}",
             (~vda["status"].isin(STATUS_VALIDOS)).sum(), len(vda))
    v.checar("Validade", "vendas: forma de pagamento desconhecida",
             (~vda["forma_pagamento"].isin(PAGAMENTOS_VALIDOS)).sum(), len(vda))
    v.checar("Validade", "clientes: UF inválida", (~cli["estado"].isin(REGIAO_POR_UF)).sum(), len(cli))
    padrao_email = re.compile(r"^[^@\s]+@[^@\s]+\.[^@\s]+$")
    v.checar("Validade", "clientes: e-mail em formato inválido",
             (~cli["email"].fillna("").map(lambda e: bool(padrao_email.match(e)))).sum(), len(cli))
    v.checar("Validade", "produtos: preço ou custo ≤ 0",
             ((pro["preco"] <= 0) | (pro["custo"] <= 0)).sum(), len(pro))
    v.checar("Validade", "produtos: estoque negativo", (pro["estoque"] < 0).sum(), len(pro))
    v.checar("Validade", "itens_venda: quantidade ≤ 0", (itv["quantidade"] <= 0).sum(), len(itv))
    v.checar("Validade", "vendas: data fora do período (nov/2024 a out/2026)",
             (~vda["data_venda"].between("2024-11-01", "2026-10-31 23:59:59")).sum(), len(vda))
    v.checar("Validade", "clientes/vendedores: data no futuro",
             (cli["data_cadastro"] > DATA_REFERENCIA).sum() + (ven["data_admissao"] > DATA_REFERENCIA).sum(),
             len(cli) + len(ven))

    # --- Consistência: regras de negócio entre tabelas ---
    v.checar("Consistência", "produtos: custo maior ou igual ao preço",
             (pro["custo"] >= pro["preco"]).sum(), len(pro))
    v.checar("Consistência", "itens_venda: subtotal ≠ quantidade × preço unitário",
             ((itv["subtotal"] - itv["quantidade"] * itv["preco_unitario"]).abs() > 0.01).sum(), len(itv))
    preco_tabela = itv.merge(pro[["id_produto", "preco"]], on="id_produto", how="left")
    v.checar("Consistência", "itens_venda: preço unitário ≠ preço de tabela do produto",
             ((preco_tabela["preco_unitario"] - preco_tabela["preco"]).abs() > 0.01).sum(), len(itv))
    soma_itens = itv.groupby("id_venda")["subtotal"].sum()
    dif = (vda.set_index("id_venda")["valor_total"] - soma_itens.reindex(vda["id_venda"]).values).abs()
    v.checar("Consistência", "vendas: valor_total ≠ soma dos subtotais dos itens", (dif > 0.01).sum(), len(vda))
    m = vda.merge(cli[["id_cliente", "data_cadastro"]], on="id_cliente", how="left") \
           .merge(ven[["id_vendedor", "data_admissao"]], on="id_vendedor", how="left")
    v.checar("Consistência", "vendas antes do cadastro do cliente",
             (m["data_venda"].dt.normalize() < m["data_cadastro"]).sum(), len(vda))
    v.checar("Consistência", "vendas antes da admissão do vendedor",
             (m["data_venda"].dt.normalize() < m["data_admissao"]).sum(), len(vda))

    return v


# ===========================================================================
# 4. TRANSFORMAÇÃO E INTEGRAÇÃO
# ===========================================================================
def transformar(d: dict[str, pd.DataFrame]) -> dict[str, pd.DataFrame]:
    cat, cli, ven, pro, vda, itv = (d[k].copy() for k in
                                    ["categorias", "clientes", "vendedores", "produtos", "vendas", "itens_venda"])

    # --- Dimensões enriquecidas ---
    cli["regiao"] = cli["estado"].map(REGIAO_POR_UF)
    # Nome e e-mail não são necessários para a análise: a dimensão tratada
    # fica sem esses dados pessoais (minimização de dados / LGPD).
    dim_clientes = cli.drop(columns=["nome", "email"])

    ven = ven.rename(columns={"setor": "canal"})
    ven["tempo_casa_anos"] = ((DATA_REFERENCIA - ven["data_admissao"]).dt.days / 365.25).round(1)

    pro = pro.merge(cat.rename(columns={"nome": "categoria"})[["id_categoria", "categoria"]],
                    on="id_categoria", how="left")
    pro["margem_unitaria"] = (pro["preco"] - pro["custo"]).round(2)
    pro["margem_pct"] = (pro["margem_unitaria"] / pro["preco"] * 100).round(2)
    pro["faixa_preco"] = pd.cut(pro["preco"], bins=[0, 100, 500, 1500, 5000, float("inf")],
                                labels=["Até R$100", "R$100–500", "R$500–1.500", "R$1.500–5.000", "Acima de R$5.000"])

    # --- Tabela fato de vendas (1 linha = 1 pedido) ---
    resumo_itens = itv.merge(pro[["id_produto", "custo"]], on="id_produto", how="left")
    resumo_itens["custo_total"] = resumo_itens["quantidade"] * resumo_itens["custo"]
    por_venda = resumo_itens.groupby("id_venda").agg(
        qtd_itens=("id_item", "count"),
        qtd_unidades=("quantidade", "sum"),
        custo_total=("custo_total", "sum"),
    ).reset_index()

    fv = vda.merge(por_venda, on="id_venda", how="left") \
            .merge(dim_clientes[["id_cliente", "estado", "regiao"]], on="id_cliente", how="left") \
            .merge(ven[["id_vendedor", "canal"]], on="id_vendedor", how="left")
    fv["custo_total"] = fv["custo_total"].round(2)
    fv["lucro_bruto"] = (fv["valor_total"] - fv["custo_total"]).round(2)
    fv["concluida"] = fv["status"].eq("Concluída")
    fv["data"] = fv["data_venda"].dt.date
    fv["ano"] = fv["data_venda"].dt.year
    fv["mes"] = fv["data_venda"].dt.month
    fv["ano_mes"] = fv["data_venda"].dt.strftime("%Y-%m")
    fv["trimestre"] = fv["data_venda"].dt.year.astype(str) + "-T" + fv["data_venda"].dt.quarter.astype(str)
    fv["dia_semana"] = fv["data_venda"].dt.dayofweek.map(dict(enumerate(DIAS_SEMANA)))
    fv["hora"] = fv["data_venda"].dt.hour

    # --- Tabela fato de itens (1 linha = 1 produto dentro de um pedido) ---
    fi = itv.merge(pro[["id_produto", "nome", "id_categoria", "categoria", "custo"]], on="id_produto", how="left") \
            .rename(columns={"nome": "produto", "custo": "custo_unitario"}) \
            .merge(fv[["id_venda", "data_venda", "ano_mes", "status", "concluida", "canal",
                       "estado", "regiao", "id_cliente", "id_vendedor", "forma_pagamento"]],
                   on="id_venda", how="left")
    fi["custo_total"] = (fi["quantidade"] * fi["custo_unitario"]).round(2)
    fi["lucro_bruto"] = (fi["subtotal"] - fi["custo_total"]).round(2)

    return {
        "dim_clientes": dim_clientes,
        "dim_vendedores": ven,
        "dim_produtos": pro,
        "dim_categorias": cat,
        "fato_vendas": fv,
        "fato_itens": fi,
    }


# ===========================================================================
# 5. CARGA
# ===========================================================================
def carregar(tabelas: dict[str, pd.DataFrame]):
    PASTA_SAIDA.mkdir(exist_ok=True)
    for nome, df in tabelas.items():
        df.to_csv(PASTA_SAIDA / f"{nome}.csv", sep=";", index=False, encoding="utf-8-sig",
                  date_format="%Y-%m-%d %H:%M:%S")
        print(f"  {nome:<15} {len(df):>6} linhas → dados_tratados/{nome}.csv")


def gerar_relatorio(v: Validador, brutos: dict[str, pd.DataFrame], tratados: dict[str, pd.DataFrame]):
    PASTA_RELATORIOS.mkdir(exist_ok=True)
    ok = sum(r["ok"] for r in v.resultados)
    linhas = [
        "# Relatório de validação da base — TechNova Informática",
        "",
        f"Gerado automaticamente por `etl/etl.py` em {datetime.now():%d/%m/%Y %H:%M}.",
        "",
        f"**Resultado: {ok} de {len(v.resultados)} checagens sem problemas"
        f" ({v.falhas_criticas} falha(s) crítica(s)).**",
        "",
        "## Volume da base",
        "",
        "| Tabela | Linhas | Colunas |",
        "| --- | ---: | ---: |",
    ]
    for nome, df in brutos.items():
        linhas.append(f"| {nome} | {len(df):,} | {df.shape[1]} |".replace(",", "."))
    linhas.append(f"| **Total** | **{sum(len(df) for df in brutos.values()):,}** | |".replace(",", "."))
    linhas += [
        "",
        "## Checagens",
        "",
        "| Dimensão | Checagem | Problemas | Registros verificados | Resultado |",
        "| --- | --- | ---: | ---: | --- |",
    ]
    for r in v.resultados:
        resultado = "OK" if r["ok"] else ("FALHA" if r["critico"] else "Atenção")
        linhas.append(f"| {r['dimensao']} | {r['descricao']} | {r['problemas']} | {r['total']} | {resultado} |")

    fv = tratados["fato_vendas"]
    linhas += [
        "",
        "## Observações e limitações",
        "",
        f"- {fv['id_cliente'].nunique()} dos {len(brutos['clientes'])} clientes cadastrados fizeram pelo menos "
        f"uma compra; os demais são cadastros sem compra (não é erro).",
        f"- {(fv['status'] == 'Pendente').sum()} vendas estão pendentes, a maioria no último mês do período: "
        "a receita de out/2026 pode estar subestimada.",
        "- O preço de venda é sempre o preço de tabela (a base não tem descontos) e o custo é fixo no período.",
        "- O estoque é uma fotografia na data de referência, sem histórico de entradas e saídas.",
        "- Os dados são sintéticos; nomes e e-mails de clientes são fictícios e foram removidos da base tratada.",
    ]
    (PASTA_RELATORIOS / "relatorio_validacao.md").write_text("\n".join(linhas) + "\n", encoding="utf-8")
    print("  relatorios/relatorio_validacao.md")


# ===========================================================================
def main():
    print("[1/5] Extraindo CSVs...")
    brutos = extrair()

    print("[2/5] Limpando e padronizando tipos...")
    limpos = limpar(brutos)

    print("[3/5] Validando a qualidade...")
    validador = validar(limpos)
    for r in validador.resultados:
        if not r["ok"]:
            print(f"  ! {r['descricao']}: {r['problemas']} problema(s)")
    print(f"  {sum(r['ok'] for r in validador.resultados)}/{len(validador.resultados)} checagens OK")
    if validador.falhas_criticas:
        print("ERRO: a base tem falhas críticas; corrija o gerador antes de seguir.")

    print("[4/5] Transformando e integrando...")
    tratados = transformar(limpos)

    print("[5/5] Gravando a base tratada e o relatório...")
    carregar(tratados)
    gerar_relatorio(validador, brutos, tratados)

    print("ETL concluído.")
    return 1 if validador.falhas_criticas else 0


if __name__ == "__main__":
    sys.exit(main())
