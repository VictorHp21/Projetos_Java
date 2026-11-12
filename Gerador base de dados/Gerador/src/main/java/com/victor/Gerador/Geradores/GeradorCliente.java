package com.victor.Gerador.Geradores;

import com.victor.Gerador.Model.Cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorCliente {
    private static final Random random = new Random();

    private static final String[] nomes = {
            "Victor", "Maria", "João", "Ana", "Carlos",
            "Mariana", "Lucas", "Beatriz", "Gabriel", "Juliana",
            "Pedro", "Camila", "Rafael", "Larissa", "Felipe",
            "Amanda", "Bruno", "Isabela", "Matheus", "Fernanda"
    };

    private static final String[] sobrenomes = {
            "Silva", "Santos", "Oliveira", "Souza", "Costa",
            "Pereira", "Rodrigues", "Almeida", "Nascimento", "Lima",
            "Araújo", "Fernandes", "Carvalho", "Gomes", "Martins",
            "Ribeiro", "Barbosa", "Monteiro", "Cardoso", "Correia"
    };

    private static final String[][] cidadesEstados = {
            {"Uberlândia", "MG"},
            {"Belo Horizonte", "MG"},
            {"São Paulo", "SP"},
            {"Rio de Janeiro", "RJ"},
            {"Goiânia", "GO"},
            {"Curitiba", "PR"},
            {"Brasília", "DF"},
            {"Salvador", "BA"},
            {"Fortaleza", "CE"},
            {"Recife", "PE"}
    };

    public static List<Cliente> gerarClientes(int quantidade) {

        List<Cliente> clientes = new ArrayList<>();

        for (int i = 1; i <= quantidade; i++) {

            Cliente cliente = new Cliente();

            String nome = nomes[random.nextInt(nomes.length)];
            String sobrenome = sobrenomes[random.nextInt(sobrenomes.length)];

            int indiceCidade = random.nextInt(cidadesEstados.length);

            cliente.setId((long) i);
            cliente.setNome(nome + " " + sobrenome);
            cliente.setEmail("cliente" + i + "@email.com");
            

            cliente.setCidade(cidadesEstados[indiceCidade][0]);
            cliente.setEstado(cidadesEstados[indiceCidade][1]);

            cliente.setDataCadastro(
                    LocalDate.now().minusDays(random.nextInt(1826))
            );

            clientes.add(cliente);
        }

        return clientes;
    }
}
