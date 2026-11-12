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
            {"Manaus", "AM"},
            {"Parintins", "AM"},
            {"Belém", "PA"},
            {"Santarém", "PA"},
            {"Ananindeua", "PA"},
            {"Porto Velho", "RO"},
            {"Ji-Paraná", "RO"},
            {"Rio Branco", "AC"},
            {"Cruzeiro do Sul", "AC"},
            {"Boa Vista", "RR"},
            {"Aracaju", "SE"},
            {"Macapá", "AP"},
            {"Palmas", "TO"},
            {"Gurupi", "TO"},


            {"Salvador", "BA"},
            {"Feira de Santana", "BA"},
            {"Vitória da Conquista", "BA"},
            {"Recife", "PE"},
            {"Caruaru", "PE"},
            {"Petrolina", "PE"},
            {"Fortaleza", "CE"},
            {"Juazeiro do Norte", "CE"},
            {"Sobral", "CE"},
            {"São Luís", "MA"},
            {"Imperatriz", "MA"},
            {"Teresina", "PI"},
            {"Parnaíba", "PI"},
            {"Natal", "RN"},
            {"Mossoró", "RN"},
            {"João Pessoa", "PB"},
            {"Campina Grande", "PB"},
            {"Maceió", "AL"},
            {"Arapiraca", "AL"},
            {"Aracaju", "SE"},


            {"Brasília", "DF"},
            {"Goiânia", "GO"},
            {"Anápolis", "GO"},
            {"Rio Verde", "GO"},
            {"Campo Grande", "MS"},
            {"Dourados", "MS"},
            {"Cuiabá", "MT"},
            {"Rondonópolis", "MT"},
            {"Sinop", "MT"},


            {"São Paulo", "SP"},
            {"Campinas", "SP"},
            {"Santos", "SP"},
            {"Ribeirão Preto", "SP"},
            {"São José dos Campos", "SP"},
            {"Rio de Janeiro", "RJ"},
            {"Niterói", "RJ"},
            {"Petrópolis", "RJ"},
            {"Belo Horizonte", "MG"},
            {"Uberlândia", "MG"},
            {"Uberaba", "MG"},
            {"Juiz de Fora", "MG"},
            {"Montes Claros", "MG"},
            {"Vitória", "ES"},
            {"Vila Velha", "ES"},
            {"Cachoeiro de Itapemirim", "ES"},

            {"Curitiba", "PR"},
            {"Londrina", "PR"},
            {"Maringá", "PR"},
            {"Cascavel", "PR"},
            {"Porto Alegre", "RS"},
            {"Caxias do Sul", "RS"},
            {"Pelotas", "RS"},
            {"Santa Maria", "RS"},
            {"Florianópolis", "SC"},
            {"Joinville", "SC"},
            {"Blumenau", "SC"},
            {"Chapecó", "SC"}
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
