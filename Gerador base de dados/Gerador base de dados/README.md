# TechNova Informática — Solução de BI (Prova Prática de Desenvolvimento de BI)

Solução completa de Business Intelligence para uma varejista fictícia de informática: geração de uma base sintética por código, banco MySQL, ETL e validação de qualidade, análise com técnicas analíticas e um dashboard interativo com insights e recomendações.

```
 Gerador Java ──► MySQL (gerador_bi) ──► arquivos_csv/ ──► ETL Python ──► dados_tratados/ ──► Análise Python ──► Dashboard HTML
 (semente 42)        criar_banco.sql        6 CSVs           etl/etl.py     tabelas fato/dim     analise/analise.py   dashboard/dashboard.html
```

## Estrutura do projeto

| Pasta / arquivo | O que tem |
| --- | --- |
| `criar_banco.sql` | Cria o banco `gerador_bi` e as 6 tabelas com chaves primárias e estrangeiras |
| `Gerador/` | Gerador da base em Java 21 + Maven (gera, grava no MySQL e exporta CSV) |
| `env.exemplo` | Modelo das variáveis de conexão com o banco (sem senha real) |
| `arquivos_csv/` | Base gerada: categorias, clientes, vendedores, produtos, vendas, itens_venda |
| `etl/etl.py` | ETL: extração, limpeza, validação (37 checagens), transformação, integração e carga |
| `dados_tratados/` | Base tratada (`fato_vendas`, `fato_itens`, dimensões) e resultados da análise (`analise/`) |
| `analise/analise.py` | Estatística descritiva, 12 KPIs, comparações, segmentação RFM + k-means, previsão |
| `dashboard/` | `gerar_dashboard.py` + `template.html` → `dashboard.html` (abre offline no navegador) |
| `relatorios/` | `relatorio_validacao.md`, `relatorio_analise.md` e `graficos/` (gerados automaticamente) |
| `docs/dicionario_dados.md` | Dicionário de dados, diagrama de relacionamentos e justificativa do volume |
| `rodar_pipeline.bat` | Roda ETL → análise → dashboard com um clique (Windows) |
| `requirements.txt` | Bibliotecas Python |

## Requisitos

- **Java 21** e **IntelliJ IDEA** (ou outra IDE com Maven) para o gerador
- **MySQL 8** (MySQL Workbench opcional)
- **Python 3.10+** com as bibliotecas do `requirements.txt` (pandas, scikit-learn, matplotlib)

## Como reproduzir do zero

### 1. Criar o banco
No MySQL Workbench: **File → Open SQL Script** → `criar_banco.sql` → executar (⚡).
O script apaga e recria as tabelas, então pode ser rodado sempre que for gerar a base de novo.

### 2. Configurar a conexão
O gerador lê a conexão de variáveis de ambiente (a senha nunca fica no código). No IntelliJ:
**Run → Edit Configurations → Main → Environment variables**:
```
DB_URL=jdbc:mysql://localhost:3306/gerador_bi;DB_USER=root;DB_PASSWORD=sua_senha
```

### 3. Gerar a base
Abra a pasta `Gerador/` no IntelliJ e rode `src/main/java/com/victor/Gerador/Main.java`.
Resultado esperado no console: 25 categorias, 500 clientes, 30 vendedores, 100 produtos, 5.000 vendas e 10.442 itens, gravados no MySQL e exportados em `arquivos_csv/`.

> **Reprodutibilidade:** a semente fixa (`Parametros.SEMENTE = 42`) e as datas de referência fixas fazem o gerador produzir **exatamente os mesmos dados** em qualquer computador. Para outra base, troque a semente.

### 4. Instalar as bibliotecas Python
Na pasta do projeto (onde está este README):
```
python -m pip install -r requirements.txt
```

### 5. Rodar ETL, análise e dashboard
Dê dois cliques em **`rodar_pipeline.bat`**, ou rode um por vez:
```
python etl/etl.py
python analise/analise.py
python dashboard/gerar_dashboard.py
```
Resultado esperado:
- ETL: `37/37 checagens OK` e a pasta `dados_tratados/`
- Análise: `relatorios/relatorio_analise.md`, 7 gráficos e 12 tabelas em `dados_tratados/analise/`
- Dashboard: `dashboard/dashboard.html` — abra no navegador (funciona sem internet)

Para ler os relatórios formatados, abra o `.md` no VS Code e use **Ctrl+Shift+V**.

## Modelo de dados

6 tabelas: 4 dimensões (`categorias`, `clientes`, `vendedores`, `produtos`) e 2 fatos (`vendas` = 1 pedido, `itens_venda` = 1 produto dentro de um pedido). Clientes e vendedores se ligam às vendas; vendas e produtos se ligam aos itens; categorias se ligam aos produtos. Detalhes, tipos e diagrama em `docs/dicionario_dados.md`.

## Qualidade, privacidade e segurança

- **Qualidade:** o ETL verifica completude, unicidade, integridade referencial, validade de domínios e consistência entre tabelas (37 checagens) e gera `relatorios/relatorio_validacao.md`. Se houver falha crítica, o script avisa e termina com erro.
- **Privacidade (LGPD — minimização):** os dados são sintéticos; mesmo assim, nome e e-mail dos clientes não passam para a base tratada nem para o dashboard, que mostra apenas dados agregados.
- **Segurança:** credenciais do banco ficam em variáveis de ambiente (`env.exemplo` não tem senha real) e o arquivo `.env`, se usado, não deve ir para o repositório.

## Problemas comuns

| Erro | Solução |
| --- | --- |
| `Unknown database 'gerador_bi'` | Rode o `criar_banco.sql` antes do gerador |
| `Duplicate entry ... for key PRIMARY` | Rode o `criar_banco.sql` de novo para limpar as tabelas |
| `Erro ao conectar` no gerador | Confira as variáveis `DB_URL`, `DB_USER`, `DB_PASSWORD` e se o MySQL está ligado |
| `No module named 'pandas'` (ou sklearn) | Use `python -m pip install -r requirements.txt` (garante o mesmo Python que roda o script) ou troque `python` por `py` |
| `Base tratada não encontrada` | Rode os scripts na ordem: ETL → análise → dashboard |
