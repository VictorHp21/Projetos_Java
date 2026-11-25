package com.victor.Gerador.DAOs;

import com.victor.Gerador.Config.Conexao;
import com.victor.Gerador.Model.Venda;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class VendaDAO {
    public void inserirTodos(List<Venda> vendas) throws SQLException {

        String sql = """
                INSERT INTO vendas
                (id_venda, id_cliente, id_vendedor,
                 data_venda, valor_total, forma_pagamento, status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            for (Venda venda : vendas) {

                stmt.setLong(1, venda.getId());
                stmt.setLong(2, venda.getCliente().getId());
                stmt.setLong(3, venda.getVendedor().getId());

                // Timestamp guarda data E hora (a coluna é DATETIME)
                stmt.setTimestamp(4, Timestamp.valueOf(venda.getDataVenda()));
                stmt.setBigDecimal(5, venda.getValorTotal());
                stmt.setString(6, venda.getFormaPagamento());
                stmt.setString(7, venda.getStatus());

                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }
}
