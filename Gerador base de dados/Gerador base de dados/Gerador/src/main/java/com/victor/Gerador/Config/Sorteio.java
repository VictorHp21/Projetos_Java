package com.victor.Gerador.Config;

import java.util.List;
import java.util.Random;

/**
 * Sorteios com pesos, usados para que os dados tenham proporções realistas
 * (ex.: PIX mais usado que boleto) em vez de tudo sair com a mesma chance.
 * Usa sempre o mesmo Random com semente fixa, então continua reproduzível.
 */
public final class Sorteio {

    private static final Random random = Parametros.RANDOM;

    private Sorteio() {
    }

    /** Sorteia uma opção; a chance de cada uma é proporcional ao seu peso. */
    public static <T> T comPeso(T[] opcoes, double[] pesos) {
        return opcoes[indiceComPeso(pesos)];
    }

    /** Sorteia um item da lista; a chance de cada um é proporcional ao seu peso. */
    public static <T> T comPeso(List<T> itens, List<Double> pesos) {

        double total = 0;
        for (double peso : pesos) {
            total += peso;
        }

        double alvo = random.nextDouble() * total;
        double acumulado = 0;

        for (int i = 0; i < itens.size(); i++) {
            acumulado += pesos.get(i);
            if (alvo < acumulado) {
                return itens.get(i);
            }
        }
        return itens.get(itens.size() - 1);
    }

    public static int indiceComPeso(double[] pesos) {

        double total = 0;
        for (double peso : pesos) {
            total += peso;
        }

        double alvo = random.nextDouble() * total;
        double acumulado = 0;

        for (int i = 0; i < pesos.length; i++) {
            acumulado += pesos[i];
            if (alvo < acumulado) {
                return i;
            }
        }
        return pesos.length - 1;
    }

    /**
     * Peso "desigual": a maioria fica perto de 1 e poucos ficam bem acima
     * (distribuição log-normal). Serve para criar clientes e vendedores
     * que vendem/compram muito mais que a média, como na vida real.
     */
    public static double pesoDesigual(double dispersao) {
        return Math.exp(random.nextGaussian() * dispersao);
    }
}
