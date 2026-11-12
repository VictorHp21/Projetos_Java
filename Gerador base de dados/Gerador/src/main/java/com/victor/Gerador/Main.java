package com.victor.Gerador;


import com.victor.Gerador.Config.Conexao;
import com.victor.Gerador.Config.ExportadorCSV;
import com.victor.Gerador.DAOs.*;
import com.victor.Gerador.Geradores.*;
import com.victor.Gerador.Model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		try (var conexao = Conexao.conectar()) {

			System.out.println("Conexão estabelecida!");

		} catch (Exception e) {

			System.out.println("Erro ao conectar: " + e.getMessage());
		}

		try {

			System.out.println("====================================");
			System.out.println("       GERADOR DE DADOS BI");
			System.out.println("====================================");



			System.out.println("\n[1/6] Gerando categorias...");

			List<Categoria> categorias =
					GeradorCategoria.gerarCategorias();

			System.out.println("Categorias geradas: " + categorias.size());


			System.out.println("\n[2/6] Gerando clientes...");

			List<Cliente> clientes =
					GeradorCliente.gerarClientes(500);

			System.out.println("Clientes gerados: " + clientes.size());


			System.out.println("\n[3/6] Gerando vendedores...");

			List<Vendedor> vendedores =
					GeradorVendedor.gerarVendedores(30);

			System.out.println("Vendedores gerados: " + vendedores.size());


			System.out.println("\n[4/6] Gerando produtos...");

			List<Produto> produtos =
					GeradorProduto.gerarProdutos(100, categorias);

			System.out.println("Produtos gerados: " + produtos.size());


			System.out.println("\n[5/6] Gerando vendas...");

			List<Venda> vendas =
					GeradorVenda.gerarVendas(
							700,
							clientes,
							vendedores
					);

			System.out.println("Vendas geradas: " + vendas.size());


			System.out.println("\n[6/6] Gerando itens de venda...");

			List<ItemVenda> itens =
					GeradorItemVenda.gerarItensVenda(
							vendas,
							produtos
					);

			System.out.println("Itens gerados: " + itens.size());



			System.out.println("\n====================================");
			System.out.println("       SALVANDO NO MYSQL");
			System.out.println("====================================");

			new CategoriaDAO().inserirTodos(categorias);
			System.out.println("Categorias salvas!");

			new ClienteDAO().inserirTodos(clientes);
			System.out.println("Clientes salvos!");

			new VendedorDAO().inserirTodos(vendedores);
			System.out.println("Vendedores salvos!");

			new ProdutoDAO().inserirTodos(produtos);
			System.out.println("Produtos salvos!");

			new VendaDAO().inserirTodos(vendas);
			System.out.println("Vendas salvas!");

			new ItemVendaDAO().inserirTodos(itens);
			System.out.println("Itens de venda salvos!");


			// =====================================
			// 3. EXPORTAR ARQUIVOS CSV
			// =====================================

			System.out.println("\n====================================");
			System.out.println("       EXPORTANDO ARQUIVOS CSV");
			System.out.println("====================================");

			ExportadorCSV.exportarTodos(
					clientes,
					vendedores,
					categorias,
					produtos,
					vendas,
					itens
			);


			// =====================================
			// 4. RESUMO FINAL
			// =====================================

			int totalRegistros =
					categorias.size()
							+ clientes.size()
							+ vendedores.size()
							+ produtos.size()
							+ vendas.size()
							+ itens.size();

			System.out.println("\n====================================");
			System.out.println("          RESUMO FINAL");
			System.out.println("====================================");

			System.out.println("Categorias: " + categorias.size());
			System.out.println("Clientes: " + clientes.size());
			System.out.println("Vendedores: " + vendedores.size());
			System.out.println("Produtos: " + produtos.size());
			System.out.println("Vendas: " + vendas.size());
			System.out.println("Itens de venda: " + itens.size());

			System.out.println("------------------------------------");

			System.out.println("TOTAL DE REGISTROS: " + totalRegistros);

			System.out.println("------------------------------------");

			System.out.println("Banco de dados: MySQL");
			System.out.println("Arquivos: CSV");
			System.out.println("Status: CONCLUIDO");

			System.out.println("====================================");

		} catch (Exception e) {

			System.err.println("\nERRO DURANTE A GERACAO DOS DADOS!");
			System.err.println("Motivo: " + e.getMessage());

			e.printStackTrace();
		}
	}

	}

