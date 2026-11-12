package com.victor.Gerador.DAOs;

import com.victor.Gerador.Config.Conexao;
import com.victor.Gerador.Model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProdutoDAO {
    public void inserirTodos(List<Produto> produtos) throws SQLException {

        String sql = """
                INSERT INTO produtos
                (id_produto, id_categoria, nome, preco, custo, estoque)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            for (Produto produto : produtos) {

                stmt.setLong(1, produto.getId());
                stmt.setLong(2, produto.getCategoria().getId());
                stmt.setString(3, produto.getNome());
                stmt.setBigDecimal(4, produto.getPreco());
                stmt.setBigDecimal(5, produto.getCusto());
                stmt.setInt(6, produto.getEstoque());

                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }
}
