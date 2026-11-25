package com.victor.Gerador.Geradores;

import com.victor.Gerador.Config.Parametros;
import com.victor.Gerador.Config.Sorteio;
import com.victor.Gerador.Model.Vendedor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GeradorVendedor {
    private static final Random random = Parametros.RANDOM;

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

    /**
     * Setor = canal de venda em que o vendedor atua.
     * A maior parte da equipe fica na loja física.
     */
    public static final String LOJA_FISICA = "Loja Física";
    public static final String VENDAS_ONLINE = "Vendas Online";
    public static final String TELEVENDAS = "Televendas";
    public static final String CORPORATIVO = "Vendas Corporativas";

    private static final String[] setores = {LOJA_FISICA, VENDAS_ONLINE, TELEVENDAS, CORPORATIVO};
    private static final double[] pesosSetores = {40, 30, 15, 15};

    public static List<Vendedor> gerarVendedores(int quantidade) {

        List<Vendedor> vendedores = new ArrayList<>();
        Set<String> nomesUsados = new HashSet<>();

        if (quantidade > nomes.length * sobrenomes.length) {
            throw new IllegalArgumentException("Não há combinações de nomes suficientes.");
        }

        for (int i = 1; i <= quantidade; i++) {

            Vendedor vendedor = new Vendedor();

            // Sorteia de novo até achar um nome completo ainda não usado
            String nomeCompleto;
            do {
                nomeCompleto = nomes[random.nextInt(nomes.length)]
                        + " " + sobrenomes[random.nextInt(sobrenomes.length)];
            } while (!nomesUsados.add(nomeCompleto));

            vendedor.setId((long) i);
            vendedor.setNome(nomeCompleto);
            vendedor.setSetor(Sorteio.comPeso(setores, pesosSetores));

            // Admitido nos últimos 8 anos
            vendedor.setDataAdmissao(
                    Parametros.DATA_REFERENCIA.minusDays(random.nextInt(2920))
            );

            vendedores.add(vendedor);
        }

        return vendedores;
    }
}
