package com.victor.Gerador.DAOs;

import com.victor.Gerador.Config.Conexao;
import com.victor.Gerador.Model.Vendedor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class VendedorDAO {
    public void inserirTodos(List<Vendedor> vendedores) throws SQLException {

        String sql = """
                INSERT INTO vendedores
                (id_vendedor, nome, setor, data_admissao)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            for (Vendedor vendedor : vendedores) {

                stmt.setLong(1, vendedor.getId());
                stmt.setString(2, vendedor.getNome());
                stmt.setString(3, vendedor.getSetor());
                stmt.setDate(4, Date.valueOf(vendedor.getDataAdmissao()));

                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

}
