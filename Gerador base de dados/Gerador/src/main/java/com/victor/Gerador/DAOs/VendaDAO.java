package com.victor.Gerador.DAOs;

import com.victor.Gerador.Config.Conexao;
import com.victor.Gerador.Model.Venda;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class VendaDAO {
    public void inserirTodos(List<Venda> vendas) throws SQLException {

        String sql = """
                INSERT INTO vendas
                (id_venda, id_cliente, id_vendedor,
                 data_venda, forma_pagamento, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            for (Venda venda : vendas) {

                stmt.setLong(1, venda.getId());
                stmt.setLong(2, venda.getCliente().getId());
                stmt.setLong(3, venda.getVendedor().getId());

                stmt.setDate(
                        4,
                        Date.valueOf(venda.getDataVenda().toLocalDate())
                );

                stmt.setString(5, venda.getFormaPagamento());
                stmt.setString(6, venda.getStatus());

                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }
}
