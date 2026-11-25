# Relatório de validação da base — TechNova Informática

Gerado automaticamente por `etl/etl.py` em 14/11/2026 13:35.

**Resultado: 37 de 37 checagens sem problemas (0 falha(s) crítica(s)).**

## Volume da base

| Tabela | Linhas | Colunas |
| --- | ---: | ---: |
| categorias | 25 | 3 |
| clientes | 500 | 6 |
| vendedores | 30 | 4 |
| produtos | 100 | 6 |
| vendas | 5.000 | 7 |
| itens_venda | 10.442 | 6 |
| **Total** | **16.097** | |

## Checagens

| Dimensão | Checagem | Problemas | Registros verificados | Resultado |
| --- | --- | ---: | ---: | --- |
| Completude | categorias: células nulas/inválidas | 0 | 75 | OK |
| Completude | clientes: células nulas/inválidas | 0 | 3000 | OK |
| Completude | vendedores: células nulas/inválidas | 0 | 120 | OK |
| Completude | produtos: células nulas/inválidas | 0 | 600 | OK |
| Completude | vendas: células nulas/inválidas | 0 | 35000 | OK |
| Completude | itens_venda: células nulas/inválidas | 0 | 62652 | OK |
| Unicidade | categorias: chave primária (id_categoria) duplicada | 0 | 25 | OK |
| Unicidade | clientes: chave primária (id_cliente) duplicada | 0 | 500 | OK |
| Unicidade | vendedores: chave primária (id_vendedor) duplicada | 0 | 30 | OK |
| Unicidade | produtos: chave primária (id_produto) duplicada | 0 | 100 | OK |
| Unicidade | vendas: chave primária (id_venda) duplicada | 0 | 5000 | OK |
| Unicidade | itens_venda: chave primária (id_item) duplicada | 0 | 10442 | OK |
| Unicidade | clientes: e-mail duplicado | 0 | 500 | OK |
| Unicidade | vendedores: nome duplicado | 0 | 30 | OK |
| Unicidade | itens_venda: mesmo produto repetido na mesma venda | 0 | 10442 | OK |
| Unicidade | linhas totalmente duplicadas (todas as tabelas) | 0 | 16097 | OK |
| Integridade | produtos → categorias (id_categoria inexistente) | 0 | 100 | OK |
| Integridade | vendas → clientes (id_cliente inexistente) | 0 | 5000 | OK |
| Integridade | vendas → vendedores (id_vendedor inexistente) | 0 | 5000 | OK |
| Integridade | itens_venda → vendas (id_venda inexistente) | 0 | 10442 | OK |
| Integridade | itens_venda → produtos (id_produto inexistente) | 0 | 10442 | OK |
| Integridade | vendas sem nenhum item | 0 | 5000 | OK |
| Validade | vendas: status fora de {Concluída, Pendente, Cancelada} | 0 | 5000 | OK |
| Validade | vendas: forma de pagamento desconhecida | 0 | 5000 | OK |
| Validade | clientes: UF inválida | 0 | 500 | OK |
| Validade | clientes: e-mail em formato inválido | 0 | 500 | OK |
| Validade | produtos: preço ou custo ≤ 0 | 0 | 100 | OK |
| Validade | produtos: estoque negativo | 0 | 100 | OK |
| Validade | itens_venda: quantidade ≤ 0 | 0 | 10442 | OK |
| Validade | vendas: data fora do período (nov/2024 a out/2026) | 0 | 5000 | OK |
| Validade | clientes/vendedores: data no futuro | 0 | 530 | OK |
| Consistência | produtos: custo maior ou igual ao preço | 0 | 100 | OK |
| Consistência | itens_venda: subtotal ≠ quantidade × preço unitário | 0 | 10442 | OK |
| Consistência | itens_venda: preço unitário ≠ preço de tabela do produto | 0 | 10442 | OK |
| Consistência | vendas: valor_total ≠ soma dos subtotais dos itens | 0 | 5000 | OK |
| Consistência | vendas antes do cadastro do cliente | 0 | 5000 | OK |
| Consistência | vendas antes da admissão do vendedor | 0 | 5000 | OK |

## Observações e limitações

- 446 dos 500 clientes cadastrados fizeram pelo menos uma compra; os demais são cadastros sem compra (não é erro).
- 231 vendas estão pendentes, a maioria no último mês do período: a receita de out/2026 pode estar subestimada.
- O preço de venda é sempre o preço de tabela (a base não tem descontos) e o custo é fixo no período.
- O estoque é uma fotografia na data de referência, sem histórico de entradas e saídas.
- Os dados são sintéticos; nomes e e-mails de clientes são fictícios e foram removidos da base tratada.
