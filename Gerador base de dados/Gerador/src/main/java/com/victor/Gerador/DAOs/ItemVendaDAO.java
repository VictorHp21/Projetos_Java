package com.victor.Gerador.DAOs;

import com.victor.Gerador.Config.Conexao;
import com.victor.Gerador.Model.ItemVenda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ItemVendaDAO {
    public void inserirTodos(List<ItemVenda> itens) throws SQLException {

        String sql = """
                INSERT INTO itens_venda
                (id_item, id_venda, id_produto,
                 quantidade, preco_unitario, subtotal)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            for (ItemVenda item : itens) {

                stmt.setLong(1, item.getId());
                stmt.setLong(2, item.getVenda().getId());
                stmt.setLong(3, item.getProduto().getId());
                stmt.setInt(4, item.getQuantidade());
                stmt.setBigDecimal(5, item.getPrecoUnitario());
                stmt.setBigDecimal(6, item.getSubtotal());

                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }
}
