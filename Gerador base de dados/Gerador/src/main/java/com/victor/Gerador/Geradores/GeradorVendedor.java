package com.victor.Gerador.Geradores;

import com.victor.Gerador.Model.Vendedor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorVendedor {
    private static final Random random = new Random();

    private static final String[] nomes = {
            "André", "Roberto", "Marcos", "Fernanda",
            "Patrícia", "Renato", "Camila", "Eduardo",
            "Daniela", "Ricardo", "Juliana", "Marcelo",
            "Vanessa", "Gustavo", "Carolina", "Thiago",
            "Bruna", "Leonardo", "Aline", "Rodrigo"
    };

    private static final String[] sobrenomes = {
            "Silva", "Santos", "Oliveira", "Souza",
            "Costa", "Pereira", "Rodrigues", "Almeida",
            "Nascimento", "Lima", "Araújo", "Fernandes",
            "Carvalho", "Gomes", "Martins", "Ribeiro"
    };

    private static final String[] setores = {
            "Hardware",
            "Periféricos",
            "Computadores",
            "Notebooks",
            "Componentes",
            "Eletrônicos",
            "Telefonia",
            "Redes",
            "Acessórios",
            "Atendimento",
            "Vendas Online",
            "Vendas Corporativas"
    };

    public static List<Vendedor> gerarVendedores(int quantidade) {

        List<Vendedor> vendedores = new ArrayList<>();

        for (int i = 1; i <= quantidade; i++) {

            Vendedor vendedor = new Vendedor();

            String nome = nomes[random.nextInt(nomes.length)];
            String sobrenome = sobrenomes[random.nextInt(sobrenomes.length)];

            vendedor.setId((long) i);
            vendedor.setNome(nome + " " + sobrenome);

            vendedor.setSetor(setores[random.nextInt(setores.length)]);

            vendedor.setDataAdmissao(
                    LocalDate.now().minusDays(random.nextInt(2920))
            );


            vendedores.add(vendedor);
        }

        return vendedores;
    }
}
