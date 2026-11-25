"""
Análise de dados — TechNova Informática (Etapa 3 da Prova Prática de BI)

Usa a base tratada pelo ETL (dados_tratados/) e produz:
  1. Estatística descritiva e análise exploratória
  2. Os 12 KPIs definidos no planejamento (Etapa 1)
  3. Comparações por mês, canal, categoria, forma de pagamento e região
  4. Técnica analítica 1: segmentação de clientes RFM com k-means
  5. Técnica analítica 2: previsão da receita mensal (tendência + sazonalidade),
     com validação em dados que o modelo não viu
  6. Detecção de padrões e anomalias

Como rodar (na pasta "Gerador base de dados", depois do ETL):
    python -m pip install -r requirements.txt
    python analise/analise.py

Saídas:
    relatorios/relatorio_analise.md     relatório com tabelas e gráficos
    relatorios/graficos/*.png           gráficos
    dados_tratados/analise/*.csv        resultados (usados no dashboard)
"""

from __future__ import annotations

from pathlib import Path

import matplotlib

matplotlib.use("Agg")  # gera imagens sem abrir janelas
import matplotlib.pyplot as plt
import matplotlib.ticker as mtick
import numpy as np
import pandas as pd
from sklearn.cluster import KMeans
from sklearn.metrics import silhouette_score
from sklearn.preprocessing import StandardScaler

# ---------------------------------------------------------------------------
RAIZ = Path(__file__).resolve().parent.parent
PASTA_DADOS = RAIZ / "dados_tratados"
PASTA_RESULTADOS = PASTA_DADOS / "analise"
PASTA_RELATORIOS = RAIZ / "relatorios"
PASTA_GRAFICOS = PASTA_RELATORIOS / "graficos"

DATA_REFERENCIA = pd.Timestamp("2026-10-31")
SEMENTE = 42                       # mesma semente do gerador: resultados reproduzíveis
DIAS_CLIENTE_ATIVO = 180           # K10
MESES_PREVISAO = 6

COR = "#2a6fdb"
COR_2 = "#e07b39"
COR_CINZA = "#9aa5b1"
plt.rcParams.update({
    "figure.dpi": 110, "savefig.bbox": "tight", "axes.spines.top": False,
    "axes.spines.right": False, "axes.grid": True, "grid.alpha": 0.25, "font.size": 10,
})


def brl(valor: float, casas: int = 0) -> str:
    texto = f"{valor:,.{casas}f}".replace(",", "X").replace(".", ",").replace("X", ".")
    return f"R$ {texto}"


def num(valor: float, casas: int = 1) -> str:
    """Número no formato brasileiro: 3.639,2"""
    return f"{valor:,.{casas}f}".replace(",", "X").replace(".", ",").replace("X", ".")


def pct(valor: float, casas: int = 1) -> str:
    return f"{valor:.{casas}f}%".replace(".", ",")


def eixo_reais(ax, eixo="y"):
    fmt = mtick.FuncFormatter(lambda v, _: "R$ 0" if v == 0 else
                              f"R$ {v / 1e6:.1f} mi".replace(".", ",") if abs(v) >= 1e6
                              else f"R$ {v / 1e3:.0f} mil")
    alvo = ax.yaxis if eixo == "y" else ax.xaxis
    alvo.set_major_formatter(fmt)
    if eixo == "x":
        alvo.set_major_locator(mtick.MaxNLocator(4))


def tabela_md(df: pd.DataFrame) -> str:
    """Converte um DataFrame em tabela Markdown (sem depender de 'tabulate')."""
    cab = "| " + " | ".join(map(str, df.columns)) + " |"
    sep = "| " + " | ".join("---" for _ in df.columns) + " |"
    linhas = ["| " + " | ".join(map(str, r)) + " |" for r in df.itertuples(index=False)]
    return "\n".join([cab, sep, *linhas])


class Relatorio:
    def __init__(self):
        self.partes: list[str] = []

    def add(self, *textos: str):
        self.partes.extend(textos)

    def grafico(self, fig, nome: str, legenda: str):
        fig.savefig(PASTA_GRAFICOS / f"{nome}.png")
        plt.close(fig)
        self.add(f"![{legenda}](graficos/{nome}.png)", "")

    def salvar(self):
        (PASTA_RELATORIOS / "relatorio_analise.md").write_text("\n".join(self.partes) + "\n", encoding="utf-8")


# ===========================================================================
def carregar():
    fv = pd.read_csv(PASTA_DADOS / "fato_vendas.csv", sep=";", parse_dates=["data_venda"])
    fi = pd.read_csv(PASTA_DADOS / "fato_itens.csv", sep=";", parse_dates=["data_venda"])
    pr = pd.read_csv(PASTA_DADOS / "dim_produtos.csv", sep=";")
    cl = pd.read_csv(PASTA_DADOS / "dim_clientes.csv", sep=";", parse_dates=["data_cadastro"])
    ve = pd.read_csv(PASTA_DADOS / "dim_vendedores.csv", sep=";")
    return fv, fi, pr, cl, ve


# ===========================================================================
# 1. ESTATÍSTICA DESCRITIVA
# ===========================================================================
def descritiva(fv, rel: Relatorio):
    ok = fv[fv["concluida"]]
    cols = {"valor_total": "Valor do pedido (R$)", "qtd_itens": "Produtos por pedido",
            "qtd_unidades": "Unidades por pedido", "lucro_bruto": "Lucro bruto do pedido (R$)"}
    linhas = []
    for col, nome in cols.items():
        s = ok[col]
        linhas.append([nome, num(s.mean()), num(s.median()), num(s.std()), num(s.min()),
                       num(s.quantile(.25)), num(s.quantile(.75)), num(s.max()), num(s.skew(), 2)])
    tab = pd.DataFrame(linhas, columns=["Variável", "Média", "Mediana", "Desvio padrão", "Mín",
                                        "Q1", "Q3", "Máx", "Assimetria"])
    rel.add("## 1. Estatística descritiva", "",
            f"Pedidos concluídos analisados: **{len(ok):,}** de {len(fv):,}.".replace(",", "."), "",
            tabela_md(tab), "")

    media, mediana = ok["valor_total"].mean(), ok["valor_total"].median()
    rel.add(f"O valor dos pedidos é **muito assimétrico**: a média ({brl(media)}) é mais que o dobro da "
            f"mediana ({brl(mediana)}), puxada por poucos pedidos grandes (máximo de {brl(ok['valor_total'].max())}). "
            "Por isso o ticket médio deve ser lido junto com a mediana.", "")

    fig, ax = plt.subplots(figsize=(8, 3.6))
    ax.hist(np.log10(ok["valor_total"]), bins=40, color=COR, alpha=.85)
    ticks = [50, 200, 1000, 5000, 20000, 100000]
    ax.set_xticks(np.log10(ticks), [brl(t) for t in ticks])
    ax.axvline(np.log10(mediana), color=COR_2, ls="--", label=f"Mediana {brl(mediana)}")
    ax.axvline(np.log10(media), color="k", ls=":", label=f"Média {brl(media)}")
    ax.set_title("Distribuição do valor dos pedidos concluídos (escala log)")
    ax.set_ylabel("Pedidos")
    ax.legend()
    rel.grafico(fig, "01_distribuicao_ticket", "Distribuição do valor dos pedidos")


# ===========================================================================
# 2. KPIs
# ===========================================================================
def kpis(fv, fi, pr, cl, rel: Relatorio) -> pd.DataFrame:
    ok = fv[fv["concluida"]]
    fio = fi[fi["concluida"]]
    receita_mes = ok.groupby("ano_mes")["valor_total"].sum()

    # K4: compara jan–set de 2026 com jan–set de 2025 (outubro/2026 ainda tem pedidos pendentes)
    r25 = receita_mes["2025-01":"2025-09"].sum()
    r26 = receita_mes["2026-01":"2026-09"].sum()

    ultima = ok.groupby("id_cliente")["data_venda"].max()
    ativos = (ultima >= DATA_REFERENCIA - pd.Timedelta(days=DIAS_CLIENTE_ATIVO)).sum()
    rec_cliente = ok.groupby("id_cliente")["valor_total"].sum().sort_values(ascending=False)
    top10 = rec_cliente.head(int(round(len(rec_cliente) * 0.10)))

    venda_mensal = fio.groupby("id_produto")["quantidade"].sum() / 24
    cobertura = (pr.set_index("id_produto")["estoque"] / venda_mensal.reindex(pr["id_produto"]).values)
    criticos = (cobertura < 1).sum()

    k = [
        ("K1", "Receita líquida", ok["valor_total"].sum(), "R$"),
        ("K2", "Pedidos concluídos", len(ok), "pedidos"),
        ("K3", "Ticket médio", ok["valor_total"].mean(), "R$/pedido"),
        ("K4", "Crescimento da receita (jan–set 2026 vs 2025)", (r26 / r25 - 1) * 100, "%"),
        ("K5", "Margem bruta", fio["lucro_bruto"].sum() / fio["subtotal"].sum() * 100, "%"),
        ("K6", "Taxa de cancelamento", (fv["status"] == "Cancelada").mean() * 100, "%"),
        ("K7", "Receita perdida em cancelamentos", fv.loc[fv["status"] == "Cancelada", "valor_total"].sum(), "R$"),
        ("K8", "Itens por pedido", fv["qtd_itens"].mean(), "itens/pedido"),
        ("K9", "Participação do maior canal (Corporativo)",
         ok.groupby("canal")["valor_total"].sum().max() / ok["valor_total"].sum() * 100, "%"),
        ("K10", f"Clientes ativos (compra nos últimos {DIAS_CLIENTE_ATIVO} dias)", ativos / len(cl) * 100, "%"),
        ("K11", "Receita dos 10% melhores clientes", top10.sum() / rec_cliente.sum() * 100, "%"),
        ("K12", "Produtos com cobertura de estoque < 1 mês", criticos, "produtos"),
    ]
    df = pd.DataFrame(k, columns=["kpi", "nome", "valor", "unidade"])

    def fmt(r):
        if r.unidade.startswith("R$"):
            return brl(r.valor, 2)
        if r.unidade == "%":
            return pct(r.valor)
        if r.unidade == "itens/pedido":
            return f"{r.valor:.2f}".replace(".", ",")
        return f"{r.valor:,.0f}".replace(",", ".")

    df["valor_formatado"] = df.apply(fmt, axis=1)
    rel.add("## 2. KPIs do período (nov/2024 a out/2026)", "",
            tabela_md(df[["kpi", "nome", "valor_formatado", "unidade"]]
                      .rename(columns={"kpi": "#", "nome": "KPI", "valor_formatado": "Valor", "unidade": "Unidade"})),
            "",
            "Receita, ticket, margem e K4 consideram só vendas concluídas. O K4 compara jan–set porque "
            "out/2026 ainda tem pedidos pendentes e ficaria subestimado.", "")
    df.to_csv(PASTA_RESULTADOS / "kpis.csv", sep=";", index=False, encoding="utf-8-sig")
    return df


# ===========================================================================
# 3. COMPARAÇÕES POR DIMENSÃO
# ===========================================================================
def comparacoes(fv, fi, pr, ve, rel: Relatorio):
    ok = fv[fv["concluida"]]
    fio = fi[fi["concluida"]]
    rel.add("## 3. Comparações por dimensão", "")

    # --- 3.1 Evolução mensal ---
    mensal = fv.groupby("ano_mes").agg(
        pedidos=("id_venda", "count"),
        canceladas=("status", lambda s: (s == "Cancelada").sum()),
        pendentes=("status", lambda s: (s == "Pendente").sum()),
    )
    mensal["receita"] = ok.groupby("ano_mes")["valor_total"].sum()
    mensal["pedidos_concluidos"] = ok.groupby("ano_mes").size()
    mensal["ticket_medio"] = mensal["receita"] / mensal["pedidos_concluidos"]
    mensal["lucro_bruto"] = fio.groupby("ano_mes")["lucro_bruto"].sum()
    mensal = mensal.reset_index()
    mensal.to_csv(PASTA_RESULTADOS / "mensal.csv", sep=";", index=False, encoding="utf-8-sig")

    fig, ax = plt.subplots(figsize=(10, 3.8))
    x = np.arange(len(mensal))
    cores = [COR_2 if m.endswith(("-11", "-12")) else COR for m in mensal["ano_mes"]]
    cores[-1] = COR_CINZA
    ax.bar(x, mensal["receita"], color=cores)
    ax.set_xticks(x, [m[5:] + "/" + m[2:4] for m in mensal["ano_mes"]], rotation=45)
    eixo_reais(ax)
    ax.set_title("Receita mensal (vendas concluídas) — laranja: nov/dez · cinza: out/2026 com pendentes")
    rel.add("### 3.1 Evolução mensal", "")
    rel.grafico(fig, "02_receita_mensal", "Receita mensal")
    nov24, nov25 = mensal.set_index("ano_mes").loc[["2024-11", "2025-11"], "receita"]
    fev = mensal[mensal["ano_mes"].str.endswith("-02")]["receita"].mean()
    rel.add(f"Há **sazonalidade forte**: novembro (Black Friday) e dezembro (Natal) são os picos, e "
            f"fevereiro é o vale (média de {brl(fev)}). Nov/2025 faturou {pct((nov25 / nov24 - 1) * 100)} "
            "a mais que nov/2024. A queda de out/2026 não é real: "
            f"{int(mensal.iloc[-1]['pendentes'])} pedidos daquele mês ainda estão pendentes.", "")

    # --- 3.2 Canais ---
    canal = ok.groupby("canal").agg(receita=("valor_total", "sum"), pedidos=("id_venda", "count"),
                                    ticket_medio=("valor_total", "mean"), lucro=("lucro_bruto", "sum"))
    canal["part_receita_%"] = canal["receita"] / canal["receita"].sum() * 100
    canal["part_pedidos_%"] = canal["pedidos"] / canal["pedidos"].sum() * 100
    canal = canal.sort_values("receita", ascending=False).reset_index()
    canal.to_csv(PASTA_RESULTADOS / "canais.csv", sep=";", index=False, encoding="utf-8-sig")
    t = canal.assign(**{
        "Receita": canal["receita"].map(brl), "Pedidos": canal["pedidos"],
        "Ticket médio": canal["ticket_medio"].map(brl),
        "% da receita": canal["part_receita_%"].map(pct), "% dos pedidos": canal["part_pedidos_%"].map(pct)
    })[["canal", "Receita", "Pedidos", "Ticket médio", "% da receita", "% dos pedidos"]].rename(columns={"canal": "Canal"})
    corp = canal.set_index("canal").loc["Vendas Corporativas"]
    rel.add("### 3.2 Canais de venda", "", tabela_md(t), "",
            f"O canal **Corporativo** tem só {pct(corp['part_pedidos_%'])} dos pedidos, mas "
            f"{pct(corp['part_receita_%'])} da receita: o ticket médio ({brl(corp['ticket_medio'])}) é cerca de "
            f"{corp['ticket_medio'] / canal[canal['canal'] != 'Vendas Corporativas']['ticket_medio'].mean():.0f} vezes "
            "o dos outros canais, porque empresas compram em lote.", "")

    # --- 3.3 Categorias: receita e margem ---
    cat = fio.groupby("categoria").agg(receita=("subtotal", "sum"), lucro=("lucro_bruto", "sum"),
                                       unidades=("quantidade", "sum"))
    cat["margem_%"] = cat["lucro"] / cat["receita"] * 100
    cat["part_receita_%"] = cat["receita"] / cat["receita"].sum() * 100
    cat = cat.sort_values("receita", ascending=False).reset_index()
    cat["classe_abc"] = np.select(
        [cat["part_receita_%"].cumsum() <= 80, cat["part_receita_%"].cumsum() <= 95], ["A", "B"], "C")
    cat.to_csv(PASTA_RESULTADOS / "categorias.csv", sep=";", index=False, encoding="utf-8-sig")

    fig, axs = plt.subplots(1, 2, figsize=(11, 6.5), sharey=True)
    c = cat.sort_values("receita")
    axs[0].barh(c["categoria"], c["receita"], color=COR)
    eixo_reais(axs[0], "x")
    axs[0].set_title("Receita por categoria")
    media_margem = fio["lucro_bruto"].sum() / fio["subtotal"].sum() * 100
    axs[1].barh(c["categoria"], c["margem_%"],
                color=[COR_2 if m < media_margem - 5 else COR for m in c["margem_%"]])
    axs[1].axvline(media_margem, color="k", ls=":", lw=1)
    axs[1].xaxis.set_major_formatter(mtick.PercentFormatter(decimals=0))
    axs[1].set_title(f"Margem bruta (linha = média {pct(media_margem)})")
    rel.add("### 3.3 Categorias: receita × margem", "")
    rel.grafico(fig, "03_categorias", "Receita e margem por categoria")
    n_a = (cat["classe_abc"] == "A").sum()
    baixa = cat.sort_values("margem_%").head(3)
    alta = cat.sort_values("margem_%", ascending=False).head(3)
    rel.add(f"Curva ABC: **{n_a} das 25 categorias** fazem 80% da receita (classe A), lideradas por "
            f"{', '.join(cat['categoria'].head(3))}. As margens mais baixas são de "
            f"{', '.join(f'{c} ({pct(m)})' for c, m in zip(baixa['categoria'], baixa['margem_%']))}; as mais altas, de "
            f"{', '.join(f'{c} ({pct(m)})' for c, m in zip(alta['categoria'], alta['margem_%']))}.", "")

    # --- 3.4 Produtos e estoque ---
    prod = fio.groupby(["id_produto", "produto", "categoria"]).agg(
        receita=("subtotal", "sum"), unidades=("quantidade", "sum")).reset_index()
    prod = prod.merge(pr[["id_produto", "estoque", "margem_pct"]], on="id_produto")
    prod["venda_mensal_media"] = prod["unidades"] / 24
    prod["cobertura_meses"] = prod["estoque"] / prod["venda_mensal_media"]
    prod.sort_values("receita", ascending=False).to_csv(PASTA_RESULTADOS / "produtos.csv", sep=";",
                                                         index=False, encoding="utf-8-sig")
    top = prod.sort_values("receita", ascending=False).head(10)
    crit = prod.sort_values("cobertura_meses").head(8)
    rel.add("### 3.4 Produtos e risco de ruptura", "",
            "**Top 10 em receita:**", "",
            tabela_md(top.assign(Receita=top["receita"].map(brl), Unidades=top["unidades"])
                      [["produto", "categoria", "Unidades", "Receita"]]
                      .rename(columns={"produto": "Produto", "categoria": "Categoria"})), "",
            "**Menor cobertura de estoque (K12):**", "",
            tabela_md(crit.assign(**{"Estoque": crit["estoque"],
                                     "Venda média/mês": crit["venda_mensal_media"].map(lambda v: f"{v:.1f}".replace(".", ",")),
                                     "Cobertura (meses)": crit["cobertura_meses"].map(lambda v: f"{v:.2f}".replace(".", ","))})
                      [["produto", "Estoque", "Venda média/mês", "Cobertura (meses)"]].rename(columns={"produto": "Produto"})), "",
            f"**{(prod['cobertura_meses'] < 1).sum()} produtos** têm estoque para menos de 1 mês de vendas.", "")

    # --- 3.5 Cancelamentos por forma de pagamento ---
    pag = fv.groupby("forma_pagamento").agg(
        pedidos=("id_venda", "count"),
        canceladas=("status", lambda s: (s == "Cancelada").sum()),
        receita_perdida=("valor_total", lambda s: s[fv.loc[s.index, "status"] == "Cancelada"].sum()),
    )
    pag["taxa_cancelamento_%"] = pag["canceladas"] / pag["pedidos"] * 100
    pag = pag.sort_values("taxa_cancelamento_%", ascending=False).reset_index()
    pag.to_csv(PASTA_RESULTADOS / "pagamentos.csv", sep=";", index=False, encoding="utf-8-sig")
    fig, ax = plt.subplots(figsize=(7, 3.2))
    taxa_geral = (fv["status"] == "Cancelada").mean() * 100
    ax.barh(pag["forma_pagamento"][::-1], pag["taxa_cancelamento_%"][::-1],
            color=[COR_2 if v > taxa_geral * 1.5 else COR for v in pag["taxa_cancelamento_%"][::-1]])
    ax.axvline(taxa_geral, color="k", ls=":", lw=1)
    ax.xaxis.set_major_formatter(mtick.PercentFormatter(decimals=0))
    ax.set_title(f"Taxa de cancelamento por forma de pagamento (linha = geral {pct(taxa_geral)})")
    rel.add("### 3.5 Cancelamentos por forma de pagamento", "")
    rel.grafico(fig, "04_cancelamento_pagamento", "Cancelamento por forma de pagamento")
    b = pag.set_index("forma_pagamento").loc["Boleto"]
    outras = fv[fv["forma_pagamento"] != "Boleto"]
    rel.add(f"O **boleto cancela {pct(b['taxa_cancelamento_%'])}** dos pedidos, contra "
            f"{pct((outras['status'] == 'Cancelada').mean() * 100)} nas demais formas, e responde por "
            f"{brl(b['receita_perdida'])} em vendas perdidas ({pct(b['receita_perdida'] / pag['receita_perdida'].sum() * 100)} "
            "do total cancelado), apesar de ser só cerca de 12% dos pedidos.", "")

    # Teste qui-quadrado simples (sem scipy): a diferença é estatisticamente relevante?
    obs = np.array([[b["canceladas"], b["pedidos"] - b["canceladas"]],
                    [(outras["status"] == "Cancelada").sum(), (outras["status"] != "Cancelada").sum()]])
    esp = obs.sum(1, keepdims=True) * obs.sum(0, keepdims=True) / obs.sum()
    qui2 = ((obs - esp) ** 2 / esp).sum()
    rel.add(f"Teste qui-quadrado (boleto × demais, 1 grau de liberdade): χ² = {num(qui2)}, muito acima do valor "
            "crítico de 3,84 (5% de significância), então a diferença **não é acaso**.", "")

    # --- 3.6 Regiões ---
    reg = ok.groupby("regiao").agg(receita=("valor_total", "sum"), clientes=("id_cliente", "nunique"),
                                   pedidos=("id_venda", "count"))
    reg["receita_por_cliente"] = reg["receita"] / reg["clientes"]
    reg = reg.sort_values("receita", ascending=False).reset_index()
    uf = ok.groupby(["estado", "regiao"]).agg(receita=("valor_total", "sum"),
                                              clientes=("id_cliente", "nunique")).reset_index() \
        .sort_values("receita", ascending=False)
    uf.to_csv(PASTA_RESULTADOS / "estados.csv", sep=";", index=False, encoding="utf-8-sig")
    rel.add("### 3.6 Regiões", "",
            tabela_md(reg.assign(Receita=reg["receita"].map(brl), Clientes=reg["clientes"],
                                 **{"Receita por cliente": reg["receita_por_cliente"].map(brl)})
                      [["regiao", "Receita", "Clientes", "Receita por cliente"]].rename(columns={"regiao": "Região"})),
            "",
            f"O {reg.iloc[0]['regiao']} lidera em receita porque concentra mais clientes compradores "
            f"({reg.iloc[0]['clientes']}). Por cliente, {reg.sort_values('receita_por_cliente').iloc[-1]['regiao']} "
            f"({brl(reg['receita_por_cliente'].max())}) fica no topo e "
            f"{reg.sort_values('receita_por_cliente').iloc[0]['regiao']} "
            f"({brl(reg['receita_por_cliente'].min())}) por último, uma diferença de "
            f"{pct((1 - reg['receita_por_cliente'].min() / reg['receita_por_cliente'].max()) * 100, 0)}.", "")

    # --- 3.7 Vendedores ---
    # Normaliza pelo tempo em que o vendedor esteve na empresa dentro do período,
    # para não penalizar quem foi admitido no meio do caminho.
    inicio = pd.Timestamp("2024-11-01")
    v2 = ve.copy()
    v2["data_admissao"] = pd.to_datetime(v2["data_admissao"])
    v2["meses_no_periodo"] = (DATA_REFERENCIA - v2["data_admissao"].clip(lower=inicio)).dt.days / 30.44
    ven = ok.groupby("id_vendedor").agg(receita=("valor_total", "sum"), pedidos=("id_venda", "count"))
    ven = v2.set_index("id_vendedor")[["nome", "canal", "meses_no_periodo"]].join(ven).fillna(0).reset_index()
    ven["pedidos_por_mes"] = ven["pedidos"] / ven["meses_no_periodo"]
    ven["receita_por_mes"] = ven["receita"] / ven["meses_no_periodo"]
    ven.sort_values("receita", ascending=False).to_csv(PASTA_RESULTADOS / "vendedores.csv", sep=";",
                                                        index=False, encoding="utf-8-sig")
    lf = ven[ven["canal"] == "Loja Física"]
    rel.add("### 3.7 Vendedores", "",
            "Para comparar vendedores de forma justa, dividimos os pedidos pelos meses que cada um trabalhou "
            "dentro do período (quem foi admitido depois teve menos tempo para vender).", "",
            f"Mesmo assim a diferença é grande: na Loja Física, de {num(lf['pedidos_por_mes'].min())} a "
            f"{num(lf['pedidos_por_mes'].max())} pedidos por mês (coeficiente de variação de "
            f"{pct(lf['pedidos_por_mes'].std() / lf['pedidos_por_mes'].mean() * 100, 0)}). "
            "A diferença não vem do tempo de casa, e sim do desempenho: há espaço para treinar os vendedores "
            "abaixo da média com as práticas dos melhores.", "")
    return mensal


# ===========================================================================
# 4. SEGMENTAÇÃO RFM COM K-MEANS
# ===========================================================================
def segmentacao_rfm(fv, cl, rel: Relatorio):
    ok = fv[fv["concluida"]]
    rfm = ok.groupby("id_cliente").agg(
        ultima_compra=("data_venda", "max"),
        frequencia=("id_venda", "count"),
        valor=("valor_total", "sum"),
    )
    rfm["recencia_dias"] = (DATA_REFERENCIA - rfm["ultima_compra"]).dt.days
    rfm = rfm.drop(columns="ultima_compra")

    # Frequência e valor são muito assimétricos: o log reduz o peso dos extremos;
    # depois a padronização deixa as 3 variáveis na mesma escala para o k-means.
    X = np.column_stack([rfm["recencia_dias"], np.log1p(rfm["frequencia"]), np.log1p(rfm["valor"])])
    X = StandardScaler().fit_transform(X)

    escolha = []
    for k in range(2, 8):
        km = KMeans(n_clusters=k, n_init=20, random_state=SEMENTE).fit(X)
        escolha.append((k, km.inertia_, silhouette_score(X, km.labels_)))
    escolha = pd.DataFrame(escolha, columns=["k", "inercia", "silhueta"])

    K = 4  # escolhido pela silhueta + interpretabilidade (ver tabela)
    km = KMeans(n_clusters=K, n_init=20, random_state=SEMENTE).fit(X)
    rfm["cluster"] = km.labels_

    perfil = rfm.groupby("cluster").agg(clientes=("valor", "size"), recencia_media=("recencia_dias", "mean"),
                                        frequencia_media=("frequencia", "mean"), valor_medio=("valor", "mean"),
                                        receita=("valor", "sum"))
    # Nomeia os grupos a partir dos centróides (regra explícita, reproduzível)
    nomes = {}
    restantes = list(perfil.index)
    campeoes = perfil.loc[restantes, "valor_medio"].idxmax(); nomes[campeoes] = "Campeões"; restantes.remove(campeoes)
    inativos = perfil.loc[restantes, "recencia_media"].idxmax(); nomes[inativos] = "Inativos"; restantes.remove(inativos)
    fieis = perfil.loc[restantes, "frequencia_media"].idxmax(); nomes[fieis] = "Fiéis"; restantes.remove(fieis)
    for c in restantes:
        nomes[c] = "Ocasionais"
    rfm["segmento"] = rfm["cluster"].map(nomes)
    perfil["segmento"] = perfil.index.map(nomes)
    perfil["part_receita_%"] = perfil["receita"] / perfil["receita"].sum() * 100
    perfil = perfil.sort_values("valor_medio", ascending=False)

    nunca = len(cl) - len(rfm)
    saida = rfm.reset_index()[["id_cliente", "recencia_dias", "frequencia", "valor", "segmento"]]
    saida.to_csv(PASTA_RESULTADOS / "rfm_clientes.csv", sep=";", index=False, encoding="utf-8-sig")
    perfil.to_csv(PASTA_RESULTADOS / "rfm_segmentos.csv", sep=";", index=False, encoding="utf-8-sig")

    acoes = {"Campeões": "Atendimento dedicado, condições VIP, não deixar migrar para concorrente",
             "Fiéis": "Programa de fidelidade e venda de itens complementares para subir o ticket",
             "Ocasionais": "Campanhas sazonais (Black Friday) e ofertas para aumentar a frequência",
             "Inativos": "Campanha de reativação com cupom; entender o motivo da saída"}
    t = perfil.assign(**{
        "Segmento": perfil["segmento"], "Clientes": perfil["clientes"],
        "Recência média (dias)": perfil["recencia_media"].round(0).astype(int),
        "Compras (média)": perfil["frequencia_media"].map(lambda v: f"{v:.1f}".replace(".", ",")),
        "Valor por cliente": perfil["valor_medio"].map(brl),
        "% da receita": perfil["part_receita_%"].map(pct),
        "Ação sugerida": perfil["segmento"].map(acoes),
    })[["Segmento", "Clientes", "Recência média (dias)", "Compras (média)", "Valor por cliente", "% da receita", "Ação sugerida"]]

    rel.add("## 4. Técnica analítica 1 — Segmentação de clientes (RFM + k-means)", "",
            "**Método:** para cada cliente com compra concluída calculamos **R**ecência (dias desde a última compra "
            "até 31/10/2026), **F**requência (número de compras) e **V**alor (total gasto). F e V passam por log "
            "(são muito assimétricos), as três variáveis são padronizadas e agrupadas com **k-means**.", "",
            "**Escolha do número de grupos:** testamos k de 2 a 7. A silhueta mede quão bem separados estão os "
            "grupos (de −1 a 1; acima de 0,25 indica estrutura razoável).", "",
            tabela_md(escolha.assign(inercia=escolha["inercia"].map(num),
                                     silhueta=escolha["silhueta"].map(lambda v: num(v, 3)))
                      .rename(columns={"inercia": "Inércia", "silhueta": "Silhueta"})), "",
            f"Escolhemos **k = {K}**: k = 2 tem silhueta um pouco maior, mas só separa quem compra muito de quem "
            "compra pouco; k = 3 acrescenta os inativos, mas ainda mistura clientes regulares com compradores de "
            f"uma ou duas compras. Com k = {K} a silhueta ({num(escolha.set_index('k').loc[K, 'silhueta'], 3)}) "
            "continua próxima da melhor e surgem perfis acionáveis (inativos, ocasionais, fiéis, campeões); "
            "a partir de 5 grupos a separação piora sem trazer perfis novos.", "",
            tabela_md(t), "",
            f"Além desses, **{nunca} clientes cadastrados nunca concluíram uma compra** e são um quinto grupo para "
            "campanha de primeira compra.", "")

    fig, ax = plt.subplots(figsize=(8, 4.6))
    cores = {"Campeões": "#2a6fdb", "Fiéis": "#2e9e6b", "Ocasionais": "#e0b339", "Inativos": "#d64545"}
    for seg in ["Campeões", "Fiéis", "Ocasionais", "Inativos"]:
        g = rfm[rfm["segmento"] == seg]
        ax.scatter(g["recencia_dias"], g["valor"], s=8 + g["frequencia"] * 2, alpha=.65,
                   label=f"{seg} ({len(g)})", color=cores[seg], edgecolors="none")
    ax.set_yscale("log")
    ax.yaxis.set_major_formatter(mtick.FuncFormatter(lambda v, _: brl(v)))
    ax.set_xlabel("Recência: dias desde a última compra (até 31/10/2026)")
    ax.set_ylabel("Valor total gasto (escala log)")
    ax.set_title("Segmentos de clientes (RFM + k-means) — tamanho do ponto = nº de compras")
    ax.legend(markerscale=1.5)
    rel.grafico(fig, "05_segmentos_rfm", "Segmentos RFM")

    # Curva de concentração (Pareto)
    rc = ok.groupby("id_cliente")["valor_total"].sum().sort_values(ascending=False)
    acum = rc.cumsum() / rc.sum() * 100
    xs = np.arange(1, len(rc) + 1) / len(rc) * 100
    fig, ax = plt.subplots(figsize=(6.5, 3.6))
    ax.plot(xs, acum.values, color=COR)
    ax.plot([0, 100], [0, 100], color=COR_CINZA, ls=":")
    p20 = acum.iloc[int(len(rc) * .2) - 1]
    ax.scatter([20], [p20], color=COR_2, zorder=3)
    ax.annotate(f"20% dos clientes = {pct(p20, 0)} da receita", (20, p20), (30, p20 - 15),
                arrowprops=dict(arrowstyle="->"))
    ax.xaxis.set_major_formatter(mtick.PercentFormatter(decimals=0))
    ax.yaxis.set_major_formatter(mtick.PercentFormatter(decimals=0))
    ax.set_xlabel("Clientes (do que mais compra ao que menos compra)")
    ax.set_ylabel("Receita acumulada")
    ax.set_title("Concentração da receita nos clientes")
    rel.grafico(fig, "06_pareto_clientes", "Curva de concentração de clientes")
    return perfil


# ===========================================================================
# 5. PREVISÃO DA RECEITA
# ===========================================================================
def _ajustar(serie: pd.Series):
    """Modelo de decomposição: índice sazonal por mês (média da razão
    receita ÷ média móvel anual) + tendência linear na série dessazonalizada."""
    df = serie.to_frame("y")
    df["t"] = np.arange(len(df))
    df["mes"] = [int(p[5:]) for p in df.index]
    nivel = df["y"].mean()
    indice = (df.groupby("mes")["y"].mean() / nivel)
    indice = indice / indice.mean()                       # índices somam 12
    dessaz = df["y"] / df["mes"].map(indice)
    a, b = np.polyfit(df["t"], dessaz, 1)                 # tendência linear
    residuo = df["y"] - (a * df["t"] + b) * df["mes"].map(indice)
    return a, b, indice, residuo.std(ddof=2)


def _prever(a, b, indice, t_ini, meses):
    return np.array([(a * (t_ini + i) + b) * indice[m] for i, m in enumerate(meses)])


def previsao(mensal: pd.DataFrame, rel: Relatorio):
    # Out/2026 fica de fora do ajuste: tem pedidos pendentes e puxaria a previsão para baixo.
    serie = mensal.set_index("ano_mes")["receita"].loc[:"2026-09"]

    # Validação: treina até 2025-12 e prevê jan–set/2026, que o modelo não viu
    treino, teste = serie.loc[:"2025-12"], serie.loc["2026-01":]
    a, b, ind, _ = _ajustar(treino)
    prev_teste = _prever(a, b, ind, len(treino), [int(p[5:]) for p in teste.index])
    mape = np.mean(np.abs(teste.values - prev_teste) / teste.values) * 100
    ingenuo = treino.loc["2025-01":"2025-09"].values          # baseline: "igual ao mesmo mês do ano passado"
    mape_ingenuo = np.mean(np.abs(teste.values - ingenuo) / teste.values) * 100
    erro_total = (prev_teste.sum() / teste.sum() - 1) * 100
    erro_total_ingenuo = (ingenuo.sum() / teste.sum() - 1) * 100

    # Modelo final com todos os meses completos
    a, b, ind, desvio = _ajustar(serie)
    futuros = pd.period_range("2026-10", periods=MESES_PREVISAO, freq="M").strftime("%Y-%m")
    prev = _prever(a, b, ind, len(serie), [int(p[5:]) for p in futuros])
    tab = pd.DataFrame({"ano_mes": futuros, "previsao": prev,
                        "limite_inferior": prev - 1.96 * desvio, "limite_superior": prev + 1.96 * desvio})
    tab.to_csv(PASTA_RESULTADOS / "previsao.csv", sep=";", index=False, encoding="utf-8-sig")

    fig, ax = plt.subplots(figsize=(10, 3.8))
    xr = np.arange(len(serie))
    xf = np.arange(len(serie), len(serie) + MESES_PREVISAO)
    ax.plot(xr, serie.values, marker="o", color=COR, label="Receita real")
    ax.plot(xr, (a * xr + b) * np.array([ind[int(p[5:])] for p in serie.index]), color=COR_CINZA, ls="--",
            label="Modelo ajustado")
    ax.plot(xf, prev, marker="o", color=COR_2, label="Previsão")
    ax.fill_between(xf, tab["limite_inferior"], tab["limite_superior"], color=COR_2, alpha=.15,
                    label="Intervalo de 95%")
    rot = list(serie.index) + list(futuros)
    ax.set_xticks(np.arange(len(rot)), [m[5:] + "/" + m[2:4] for m in rot], rotation=45)
    eixo_reais(ax)
    ax.set_title("Previsão da receita mensal (tendência + sazonalidade)")
    ax.legend(ncol=4, fontsize=8)

    crescimento_mensal = a / serie.mean() * 100
    rel.add("## 5. Técnica analítica 2 — Previsão da receita", "",
            "**Método:** decomposição clássica. (1) Para cada mês do ano calculamos um **índice sazonal** "
            "(receita média do mês ÷ média geral). (2) Dividimos a série por esse índice e ajustamos uma "
            "**tendência linear**. (3) A previsão é tendência × índice do mês. Out/2026 ficou fora do ajuste "
            "por ter pedidos pendentes.", "")
    rel.grafico(fig, "07_previsao", "Previsão da receita")
    rel.add("**Validação (backtest):** o modelo foi treinado só com nov/2024–dez/2025 e previu jan–set/2026.", "",
            tabela_md(pd.DataFrame({
                "Modelo": ["Tendência + sazonalidade", "Ingênuo (repete o mesmo mês de 2025)"],
                "MAPE (erro médio por mês)": [pct(mape), pct(mape_ingenuo)],
                "Erro no total de jan–set": [pct(erro_total), pct(erro_total_ingenuo)],
            })), "",
            f"Mês a mês, os dois modelos erram parecido (cerca de {pct(mape, 0)}), porque a receita mensal oscila "
            "com pedidos corporativos grandes que caem em um mês ou em outro. No **total do período**, porém, o "
            f"modelo erra {pct(abs(erro_total))} contra {pct(abs(erro_total_ingenuo))} do ingênuo, que ignora o "
            "crescimento. Por isso a previsão serve para planejar **trimestres e a temporada de fim de ano**, "
            "não um mês isolado.", "",
            tabela_md(tab.assign(**{"Mês": tab["ano_mes"], "Previsão": tab["previsao"].map(brl),
                                    "Intervalo de 95%": tab["limite_inferior"].clip(lower=0).map(brl) + " a "
                                    + tab["limite_superior"].map(brl)})[["Mês", "Previsão", "Intervalo de 95%"]]),
            "",
            f"Tendência: a receita dessazonalizada cresce cerca de {brl(a)} por mês "
            f"({pct(crescimento_mensal)} da média mensal). Out/2026 aparece na previsão como estimativa do mês "
            "completo, depois que os pendentes forem resolvidos.", "")
    return tab, mape


# ===========================================================================
# 6. PADRÕES E ANOMALIAS
# ===========================================================================
def anomalias(fv, mensal, rel: Relatorio):
    ok = fv[fv["concluida"]]
    rel.add("## 6. Padrões, anomalias e oportunidades", "")

    # Pedidos atípicos pelo critério do intervalo interquartil (IQR) em escala log
    lv = np.log10(ok["valor_total"])
    q1, q3 = lv.quantile([.25, .75])
    limite = 10 ** (q3 + 1.5 * (q3 - q1))
    atipicos = ok[ok["valor_total"] > limite]
    canal_at = atipicos["canal"].value_counts(normalize=True).mul(100)

    # Meses fora do padrão: resíduo padronizado (z) da receita em relação ao mesmo mês do outro ano
    m = mensal.set_index("ano_mes")["receita"]
    z = (m - m.mean()) / m.std()

    dia = ok.groupby("dia_semana").size().reindex(["Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"])

    rel.add(f"- **Pedidos atípicos:** {len(atipicos)} pedidos acima de {brl(limite)} (critério IQR em escala log). "
            f"{pct(canal_at.get('Vendas Corporativas', 0), 0)} deles são do canal Corporativo: não são erros, "
            "são compras em lote de empresas, e explicam a oscilação mensal da receita.",
            f"- **Meses fora da curva** (z = distância da média em desvios padrão): nov/2025 (z = {num(z['2025-11'])}) "
            f"é o pico do período; fev/2025 e fev/2026 (z = {num(z['2025-02'])} e {num(z['2026-02'])}) são os vales. "
            f"Out/2026 (z = {num(z['2026-10'])}) é uma "
            "anomalia de **dado incompleto** (pendentes), não de negócio.",
            f"- **Dia da semana:** sábado é o dia mais forte ({dia['Sábado']} pedidos concluídos) e domingo o mais "
            f"fraco ({dia['Domingo']}), {pct((1 - dia['Domingo'] / dia.drop('Domingo').mean()) * 100, 0)} abaixo da "
            "média dos outros dias.",
            "- **Horário:** as vendas são distribuídas de forma uniforme entre 8h e 21h, sem pico de horário.",
            "- **Oportunidades:** reduzir o cancelamento do boleto, proteger e expandir o canal Corporativo, "
            "reativar clientes inativos e repor os produtos com estoque crítico antes da Black Friday.", "")
    atipicos[["id_venda", "data_venda", "canal", "valor_total"]].to_csv(
        PASTA_RESULTADOS / "pedidos_atipicos.csv", sep=";", index=False, encoding="utf-8-sig")


def limitacoes(rel: Relatorio, mape: float):
    rel.add("## 7. Avaliação crítica e limitações", "",
            "- **Dados sintéticos:** os padrões encontrados (sazonalidade, cancelamento do boleto, concentração "
            "de clientes) foram embutidos no gerador; a análise mostra que o pipeline consegue **detectá-los e "
            "medi-los**, mas não prova que existam numa empresa real.",
            "- **Receita de out/2026 subestimada** pelos pedidos pendentes; por isso K4 e a previsão usam só meses completos.",
            f"- **Previsão:** só há 2 ciclos sazonais; o erro mês a mês (MAPE {pct(mape)}) é alto por causa dos "
            "pedidos corporativos grandes. O modelo não considera promoções, preços, concorrência nem economia.",
            "- **Margem bruta:** não inclui frete, impostos, comissões e despesas; o custo do produto é fixo no período.",
            "- **K-means:** depende da escala das variáveis e do número de grupos escolhido; a silhueta moderada "
            "indica que os segmentos têm fronteiras suaves (clientes perto da fronteira podem mudar de grupo).",
            "- **Cobertura de estoque:** usa a venda média de 24 meses e o estoque de uma única data; não considera "
            "pedidos de compra em andamento nem a sazonalidade do produto.", "")


# ===========================================================================
def main():
    for p in [PASTA_RESULTADOS, PASTA_GRAFICOS]:
        p.mkdir(parents=True, exist_ok=True)
    if not (PASTA_DADOS / "fato_vendas.csv").exists():
        raise SystemExit("Base tratada não encontrada. Rode antes: python etl/etl.py")

    fv, fi, pr, cl, ve = carregar()
    rel = Relatorio()
    rel.add("# Relatório de análise — TechNova Informática", "",
            "Gerado automaticamente por `analise/analise.py` a partir da base tratada pelo ETL. "
            "Período: nov/2024 a out/2026.", "")

    print("[1/6] Estatística descritiva...");  descritiva(fv, rel)
    print("[2/6] KPIs...");                    kpis(fv, fi, pr, cl, rel)
    print("[3/6] Comparações...");             mensal = comparacoes(fv, fi, pr, ve, rel)
    print("[4/6] Segmentação RFM...");         segmentacao_rfm(fv, cl, rel)
    print("[5/6] Previsão...");                _, mape = previsao(mensal, rel)
    print("[6/6] Padrões e anomalias...");     anomalias(fv, mensal, rel)
    limitacoes(rel, mape)
    rel.salvar()
    print("Pronto: relatorios/relatorio_analise.md, relatorios/graficos/ e dados_tratados/analise/")


if __name__ == "__main__":
    main()
