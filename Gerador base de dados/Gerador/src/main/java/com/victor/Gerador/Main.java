package com.victor.Gerador;


import com.victor.Gerador.Config.Conexao;

public class Main {

	public static void main(String[] args) {

		try (var conexao = Conexao.conectar()) {

			System.out.println("Conexão estabelecida!");

		} catch (Exception e) {

			System.out.println("Erro ao conectar: " + e.getMessage());
		}
	}
}
