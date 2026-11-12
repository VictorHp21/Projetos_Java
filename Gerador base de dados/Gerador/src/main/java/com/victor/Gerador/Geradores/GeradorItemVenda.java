package com.victor.Gerador.Geradores;

import com.victor.Gerador.Model.ItemVenda;
import com.victor.Gerador.Model.Produto;
import com.victor.Gerador.Model.Venda;

import java.math.BigDecimal;
import java.util.*;

public class GeradorItemVenda {
    private static final Random random = new Random();

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

        long idItem = 1;

        for (Venda venda : vendas) {

            int quantidadeItens = random.nextInt(
                    Math.min(5, produtos.size())
            ) + 1;

            Set<Long> produtosEscolhidos = new HashSet<>();

            for (int i = 0; i < quantidadeItens; i++) {

                Produto produto;

                do {
                    produto = produtos.get(
                            random.nextInt(produtos.size())
                    );
                } while (produtosEscolhidos.contains(produto.getId()));

                produtosEscolhidos.add(produto.getId());

                ItemVenda item = new ItemVenda();

                int quantidade = random.nextInt(5) + 1;

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
