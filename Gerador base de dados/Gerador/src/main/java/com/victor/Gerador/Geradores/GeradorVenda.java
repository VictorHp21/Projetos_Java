package com.victor.Gerador.Geradores;

import com.victor.Gerador.Model.Cliente;
import com.victor.Gerador.Model.Venda;
import com.victor.Gerador.Model.Vendedor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorVenda {
    private static final Random random = new Random();

    private static final String[] formasPagamento = {
            "PIX",
            "Crédito",
            "Débito",
            "Dinheiro",
            "Boleto"
    };

    private static final String[] status = {
            "Concluída",
            "Pendente",
            "Cancelada"
    };

    public static List<Venda> gerarVendas(
            int quantidade,
            List<Cliente> clientes,
            List<Vendedor> vendedores) {

        List<Venda> vendas = new ArrayList<>();

        if (clientes == null || clientes.isEmpty()
                || vendedores == null || vendedores.isEmpty()) {

            throw new IllegalArgumentException(
                    "É necessário gerar clientes e vendedores primeiro."
            );
        }

        for (int i = 1; i <= quantidade; i++) {

            Venda venda = new Venda();

            Cliente cliente = clientes.get(
                    random.nextInt(clientes.size())
            );

            Vendedor vendedor = vendedores.get(
                    random.nextInt(vendedores.size())
            );

            venda.setId((long) i);
            venda.setCliente(cliente);
            venda.setVendedor(vendedor);

            venda.setDataVenda(
                    LocalDateTime.now()
                            .minusDays(random.nextInt(730))
                            .minusHours(random.nextInt(24))
            );

            venda.setValorTotal(BigDecimal.ZERO.setScale(2));

            venda.setFormaPagamento(
                    formasPagamento[random.nextInt(formasPagamento.length)]
            );

            venda.setStatus(
                    status[random.nextInt(status.length)]
            );

            vendas.add(venda);
        }

        return vendas;
    }
}
