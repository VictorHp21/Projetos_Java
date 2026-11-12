package com.victor.Gerador.DAOs;

import com.victor.Gerador.Config.Conexao;
import com.victor.Gerador.Model.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CategoriaDAO {
    public void inserirTodos(List<Categoria> categorias) throws SQLException {

        String sql = """
                INSERT INTO categorias
                (id_categoria, nome, descricao)
                VALUES (?, ?, ?)
                """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            for (Categoria categoria : categorias) {

                stmt.setLong(1, categoria.getId());
                stmt.setString(2, categoria.getNome());
                stmt.setString(3, categoria.getDescricao());

                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

}
