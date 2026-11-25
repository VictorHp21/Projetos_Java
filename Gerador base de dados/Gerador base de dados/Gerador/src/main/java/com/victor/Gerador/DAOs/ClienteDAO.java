package com.victor.Gerador.DAOs;

import com.victor.Gerador.Config.Conexao;
import com.victor.Gerador.Model.Cliente;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ClienteDAO {

    public void inserirTodos(List<Cliente> clientes) throws SQLException{
        String sql = """
                INSERT INTO clientes
                (id_cliente, nome, email, cidade, estado, data_cadastro)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {
            for (Cliente cliente : clientes) {

                stmt.setLong(1, cliente.getId());
                stmt.setString(2, cliente.getNome());
                stmt.setString(3, cliente.getEmail());
                stmt.setString(4, cliente.getCidade());
                stmt.setString(5, cliente.getEstado());
                stmt.setDate(6, Date.valueOf(cliente.getDataCadastro()));

                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }


}
