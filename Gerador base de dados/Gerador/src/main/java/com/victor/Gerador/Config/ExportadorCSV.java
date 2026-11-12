package com.victor.Gerador.Config;

import com.victor.Gerador.Model.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.function.Function;

public class ExportadorCSV {

    private static final Path PASTA = Path.of("arquivos_csv");

    public static void exportarTodos(
            List<Cliente> clientes,
            List<Vendedor> vendedores,
            List<Categoria> categorias,
            List<Produto> produtos,
            List<Venda> vendas,
            List<ItemVenda> itens) throws IOException {

        Files.createDirectories(PASTA);

        // Clientes
        escreverArquivo(
                "clientes.csv",
                "id_cliente;nome;email;cidade;estado;data_cadastro",
                clientes,
                c -> new String[]{
                        valor(c.getId()),
                        c.getNome(),
                        c.getEmail(),
                        c.getCidade(),
                        c.getEstado(),
                        valor(c.getDataCadastro())
                }
        );

        // Vendedores
        escreverArquivo(
                "vendedores.csv",
                "id_vendedor;nome;setor;data_admissao",
                vendedores,
                v -> new String[]{
                        valor(v.getId()),
                        v.getNome(),
                        v.getSetor(),
                        valor(v.getDataAdmissao()),

                }
        );

        // Categorias
        escreverArquivo(
                "categorias.csv",
                "id_categoria;nome;descricao",
                categorias,
                c -> new String[]{
                        valor(c.getId()),
                        c.getNome(),
                        c.getDescricao()
                }
        );

        // Produtos
        escreverArquivo(
                "produtos.csv",
                "id_produto;id_categoria;nome;descricao;preco;estoque;ativo",
                produtos,
                p -> new String[]{
                        valor(p.getId()),
                        valor(p.getCategoria().getId()),
                        p.getNome(),
                        decimal(p.getPreco()),
                        valor(p.getEstoque()),

                }
        );

        // Vendas
        escreverArquivo(
                "vendas.csv",
                "id_venda;id_cliente;id_vendedor;data_venda;valor_total;forma_pagamento;status",
                vendas,
                v -> new String[]{
                        valor(v.getId()),
                        valor(v.getCliente().getId()),
                        valor(v.getVendedor().getId()),
                        valor(v.getDataVenda()),
                        decimal(v.getValorTotal()),
                        v.getFormaPagamento(),
                        v.getStatus()
                }
        );

        // Itens de venda
        escreverArquivo(
                "itens_venda.csv",
                "id_item;id_venda;id_produto;quantidade;preco_unitario;subtotal",
                itens,
                i -> new String[]{
                        valor(i.getId()),
                        valor(i.getVenda().getId()),
                        valor(i.getProduto().getId()),
                        valor(i.getQuantidade()),
                        decimal(i.getPrecoUnitario()),
                        decimal(i.getSubtotal())
                }
        );

        System.out.println("\nArquivos CSV exportados com sucesso!");
        System.out.println("Pasta: " + PASTA.toAbsolutePath());
    }

    private static <T> void escreverArquivo(
            String nomeArquivo,
            String cabecalho,
            List<T> dados,
            Function<T, String[]> conversor) throws IOException {

        Path caminho = PASTA.resolve(nomeArquivo);

        try (BufferedWriter writer = Files.newBufferedWriter(
                caminho,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            // BOM para facilitar a leitura de acentos no Excel
            writer.write('\uFEFF');

            writer.write(cabecalho);
            writer.newLine();

            for (T objeto : dados) {
                String[] campos = conversor.apply(objeto);

                for (int i = 0; i < campos.length; i++) {
                    campos[i] = escapar(campos[i]);
                }

                writer.write(String.join(";", campos));
                writer.newLine();
            }
        }

        System.out.println("CSV criado: " + nomeArquivo);
    }

    private static String escapar(String texto) {

        if (texto == null) {
            return "";
        }

        if (texto.contains(";")
                || texto.contains("\"")
                || texto.contains("\n")
                || texto.contains("\r")) {

            return "\"" + texto.replace("\"", "\"\"") + "\"";
        }

        return texto;
    }

    private static String valor(Object objeto) {
        return objeto == null ? "" : objeto.toString();
    }

    private static String decimal(BigDecimal numero) {
        return numero == null ? "" : numero.toPlainString();
    }
}
