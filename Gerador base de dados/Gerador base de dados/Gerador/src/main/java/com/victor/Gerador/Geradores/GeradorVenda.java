package com.victor.Gerador.Geradores;

import com.victor.Gerador.Config.Parametros;
import com.victor.Gerador.Config.Sorteio;
import com.victor.Gerador.Model.Cliente;
import com.victor.Gerador.Model.Venda;
import com.victor.Gerador.Model.Vendedor;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GeradorVenda {

    private static final Random random = Parametros.RANDOM;

    // ------------------------------------------------------------------
    // Formas de pagamento: PIX e crédito dominam, dinheiro é raro.
    // ------------------------------------------------------------------
    private static final String[] formasPagamento = {"PIX", "Crédito", "Débito", "Boleto", "Dinheiro"};
    private static final double[] pesosPagamento = {35, 30, 15, 12, 8};

    // ------------------------------------------------------------------
    // Sazonalidade: peso relativo de cada mês (jan..dez).
    // Novembro (Black Friday) e dezembro (Natal) vendem mais;
    // fevereiro (pós-férias) vende menos.
    // ------------------------------------------------------------------
    private static final double[] sazonalidadeMes = {
            0.85, 0.75, 0.90, 0.90, 1.00, 0.95,
            1.00, 0.95, 0.95, 1.00, 1.60, 1.40
    };

    /** Crescimento do volume de vendas por ano (20% a.a.). */
    private static final double CRESCIMENTO_ANUAL = 0.20;

    /** Vendas dos últimos dias ainda podem estar aguardando pagamento/entrega. */
    private static final int DIAS_EM_ABERTO = 30;

    public static List<Venda> gerarVendas(
            int quantidade,
            List<Cliente> clientes,
            List<Vendedor> vendedores) {

        List<Venda> vendas = new ArrayList<>();

        if (clientes == null || clientes.isEmpty()
                || vendedores == null || vendedores.isEmpty()) {

            throw new IllegalArgumentException(
                    "É necessário gerar clientes e vendedores primeiro."
            );
        }

        // Cada cliente e cada vendedor recebe uma "propensão": poucos compram
        // (ou vendem) muito, a maioria compra pouco. Isso cria clientes VIP,
        // clientes esporádicos e vendedores com desempenho diferente.
        Map<Long, Double> propensaoCliente = new HashMap<>();
        for (Cliente cliente : clientes) {
            propensaoCliente.put(cliente.getId(), Sorteio.pesoDesigual(1.0));
        }

        Map<Long, Double> propensaoVendedor = new HashMap<>();
        for (Vendedor vendedor : vendedores) {
            propensaoVendedor.put(vendedor.getId(), Sorteio.pesoDesigual(0.35));
        }

        for (int i = 1; i <= quantidade; i++) {

            Venda venda = new Venda();

            // 1) Sorteia o dia respeitando sazonalidade e crescimento.
            LocalDate dia = sortearDia();

            // 2) Escolhe um cliente já cadastrado e um vendedor já admitido
            //    nesse dia: a venda nunca acontece antes de eles existirem.
            Cliente cliente = sortearCliente(clientes, propensaoCliente, dia);
            Vendedor vendedor = sortearVendedor(vendedores, propensaoVendedor, dia);

            venda.setId((long) i);
            venda.setCliente(cliente);
            venda.setVendedor(vendedor);

            // Horário comercial: entre 08:00 e 21:59
            venda.setDataVenda(
                    dia.atTime(8 + random.nextInt(14), random.nextInt(60), random.nextInt(60))
            );

            venda.setValorTotal(BigDecimal.ZERO.setScale(2));

            String pagamento = Sorteio.comPeso(formasPagamento, pesosPagamento);
            venda.setFormaPagamento(pagamento);
            venda.setStatus(sortearStatus(dia, pagamento));

            vendas.add(venda);
        }

        return vendas;
    }

    /**
     * Sorteia um dia do período. Um dia candidato é aceito com probabilidade
     * proporcional ao seu peso (mês, tendência de crescimento e dia da semana),
     * então meses fortes recebem mais vendas.
     */
    private static LocalDate sortearDia() {

        long diasNoPeriodo = ChronoUnit.DAYS.between(Parametros.INICIO_VENDAS, Parametros.FIM_VENDAS);
        double pesoMaximo = 1.60 * (1 + CRESCIMENTO_ANUAL * 2) * 1.10;

        while (true) {
            LocalDate dia = Parametros.INICIO_VENDAS.plusDays(random.nextLong(diasNoPeriodo + 1));

            double anosDesdeInicio = ChronoUnit.DAYS.between(Parametros.INICIO_VENDAS, dia) / 365.0;
            double peso = sazonalidadeMes[dia.getMonthValue() - 1]
                    * (1 + CRESCIMENTO_ANUAL * anosDesdeInicio)
                    * pesoDiaSemana(dia.getDayOfWeek());

            if (random.nextDouble() * pesoMaximo < peso) {
                return dia;
            }
        }
    }

    /** Sábado é o dia mais forte; domingo, o mais fraco. */
    private static double pesoDiaSemana(DayOfWeek diaSemana) {
        return switch (diaSemana) {
            case SATURDAY -> 1.10;
            case SUNDAY -> 0.60;
            default -> 1.00;
        };
    }

    /**
     * Status depende da idade da venda e da forma de pagamento:
     * - vendas recentes ainda podem estar pendentes;
     * - boleto é cancelado com mais frequência (o cliente não paga).
     */
    private static String sortearStatus(LocalDate dia, String pagamento) {

        long diasAteFim = ChronoUnit.DAYS.between(dia, Parametros.FIM_VENDAS);
        double chanceCancelar = pagamento.equals("Boleto") ? 0.28 : 0.09;

        if (diasAteFim < DIAS_EM_ABERTO) {
            // Venda recente: boa parte ainda não foi finalizada
            double sorteio = random.nextDouble();
            if (sorteio < 0.55) return "Pendente";
            if (sorteio < 0.55 + chanceCancelar / 2) return "Cancelada";
            return "Concluída";
        }

        double sorteio = random.nextDouble();
        if (sorteio < chanceCancelar) return "Cancelada";
        if (sorteio < chanceCancelar + 0.02) return "Pendente";   // poucas "travadas"
        return "Concluída";
    }

    private static Cliente sortearCliente(List<Cliente> clientes, Map<Long, Double> propensao, LocalDate dia) {

        List<Cliente> aptos = new ArrayList<>();
        List<Double> pesos = new ArrayList<>();

        for (Cliente cliente : clientes) {
            if (!cliente.getDataCadastro().isAfter(dia)) {
                aptos.add(cliente);
                pesos.add(propensao.get(cliente.getId()));
            }
        }

        if (aptos.isEmpty()) {
            throw new IllegalStateException("Nenhum cliente cadastrado até " + dia);
        }
        return Sorteio.comPeso(aptos, pesos);
    }

    private static Vendedor sortearVendedor(List<Vendedor> vendedores, Map<Long, Double> propensao, LocalDate dia) {

        List<Vendedor> aptos = new ArrayList<>();
        List<Double> pesos = new ArrayList<>();

        for (Vendedor vendedor : vendedores) {
            if (!vendedor.getDataAdmissao().isAfter(dia)) {
                aptos.add(vendedor);
                pesos.add(propensao.get(vendedor.getId()));
            }
        }

        if (aptos.isEmpty()) {
            throw new IllegalStateException("Nenhum vendedor admitido até " + dia);
        }
        return Sorteio.comPeso(aptos, pesos);
    }
}
