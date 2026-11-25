package com.victor.Gerador.Geradores;

import com.victor.Gerador.Config.Parametros;
import com.victor.Gerador.Config.Sorteio;
import com.victor.Gerador.Model.ItemVenda;
import com.victor.Gerador.Model.Produto;
import com.victor.Gerador.Model.Venda;

import java.math.BigDecimal;
import java.util.*;

public class GeradorItemVenda {
    private static final Random random = Parametros.RANDOM;

    /** Quantos produtos diferentes uma venda tem: a maioria tem 1 ou 2. */
    private static final double[] pesosItensPorVenda = {40, 30, 17, 9, 4};      // 1..5 itens

    /** Unidades de cada item no varejo: quase sempre 1. */
    private static final double[] pesosQuantidadeVarejo = {70, 18, 7, 3, 2};    // 1..5 unidades

    public static List<ItemVenda> gerarItensVenda(
            List<Venda> vendas,
            List<Produto> produtos) {

        List<ItemVenda> itens = new ArrayList<>();

        if (vendas == null || vendas.isEmpty()
                || produtos == null || produtos.isEmpty()) {

            throw new IllegalArgumentException(
                    "É necessário gerar vendas e produtos primeiro."
            );
        }

        // Popularidade: produtos baratos (cabos, mouses) vendem mais vezes
        // que produtos caros (workstations), com uma variação aleatória.
        List<Double> popularidade = new ArrayList<>();
        for (Produto produto : produtos) {
            double preco = produto.getPreco().doubleValue();
            popularidade.add(Sorteio.pesoDesigual(0.5) / Math.pow(preco, 0.35));
        }

        long idItem = 1;

        for (Venda venda : vendas) {

            int quantidadeItens = Math.min(
                    Sorteio.indiceComPeso(pesosItensPorVenda) + 1,
                    produtos.size()
            );

            boolean corporativa = GeradorVendedor.CORPORATIVO.equals(venda.getVendedor().getSetor());

            Set<Long> produtosEscolhidos = new HashSet<>();

            for (int i = 0; i < quantidadeItens; i++) {

                Produto produto;

                do {
                    produto = Sorteio.comPeso(produtos, popularidade);
                } while (produtosEscolhidos.contains(produto.getId()));

                produtosEscolhidos.add(produto.getId());

                ItemVenda item = new ItemVenda();

                // Vendas corporativas compram em lote (2 a 10 unidades)
                int quantidade = corporativa
                        ? 2 + random.nextInt(9)
                        : Sorteio.indiceComPeso(pesosQuantidadeVarejo) + 1;

                BigDecimal precoUnitario = produto.getPreco();

                BigDecimal subtotal = precoUnitario.multiply(
                        BigDecimal.valueOf(quantidade)
                );

                item.setId(idItem++);
                item.setVenda(venda);
                item.setProduto(produto);
                item.setQuantidade(quantidade);
                item.setPrecoUnitario(precoUnitario);
                item.setSubtotal(subtotal);

                venda.setValorTotal(
                        venda.getValorTotal().add(subtotal)
                );

                itens.add(item);
            }
        }

        return itens;
    }
}
