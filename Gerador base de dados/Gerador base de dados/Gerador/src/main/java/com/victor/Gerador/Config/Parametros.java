package com.victor.Gerador.Config;

import java.time.LocalDate;
import java.util.Random;

/**
 * Parâmetros fixos da geração.
 *
 * A semente e as datas de referência são fixas para que a base seja
 * REPRODUZÍVEL: rodar o gerador de novo produz exatamente os mesmos dados.
 * Para gerar uma base diferente, basta trocar a SEMENTE.
 */
public final class Parametros {

    private Parametros() {
    }

    /** Semente do gerador aleatório: mesma semente = mesmos dados. */
    public static final long SEMENTE = 42L;

    /** Único gerador aleatório usado por todas as classes Gerador*. */
    public static final Random RANDOM = new Random(SEMENTE);

    /** Período das vendas: 24 meses completos (nov/2024 a out/2026). */
    public static final LocalDate INICIO_VENDAS = LocalDate.of(2024, 11, 1);
    public static final LocalDate FIM_VENDAS = LocalDate.of(2026, 10, 31);

    /** Data usada como "hoje" para cadastros de clientes e admissões. */
    public static final LocalDate DATA_REFERENCIA = FIM_VENDAS;
}
