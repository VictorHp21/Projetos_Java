package com.victor.Gerador;


import com.victor.Gerador.Config.Conexao;
import com.victor.Gerador.Model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {

	public static void main(String[] args) {

		try (var conexao = Conexao.conectar()) {

			System.out.println("Conexão estabelecida!");

		} catch (Exception e) {

			System.out.println("Erro ao conectar: " + e.getMessage());
		}

		Cliente cliente = new Cliente(
				1L,
				"Victor Henrique",
				"victor@email.com",
				"34999999999",
				"Uberlândia",
				"MG",
				LocalDate.now()
		);

		System.out.println(cliente);

		Vendedor vendedor = new Vendedor(
				1L,
				"Carlos Silva",
				"carlos@email.com",
				"34988888888",
				LocalDate.now(),
				true
		);

		System.out.println(vendedor);

		Categoria categoria = new Categoria(
				1L,
				"Informática",
				"Produtos relacionados à tecnologia"
		);

		System.out.println(categoria);



		Produto produto = new Produto(
				1L,
				"Notebook",
				"Notebook para uso profissional",
				new BigDecimal("3500.00"),
				15,
				categoria,
				true
		);

		System.out.println(produto);



		Venda venda = new Venda(
				1L,
				cliente,
				vendedor,
				LocalDateTime.now(),
				new BigDecimal("3500.00"),
				"PIX",
				"CONCLUIDA"
		);

		System.out.println(venda);

		ItemVenda item = new ItemVenda(
				1L,
				venda,
				produto,
				2,
				produto.getPreco(),
				produto.getPreco().multiply(BigDecimal.valueOf(2))
		);

		System.out.println(item);
	}
}
