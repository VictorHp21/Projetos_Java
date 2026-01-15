package Model.entities;

import Model.enums.TipoIngresso;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Sessao {
    private int capacidade;
    private Double precoBase;

    private int inteiras;
    private int meias;

    private int totalIngressosVendidos;

    public Sessao(){}

    public Sessao(int capacidade, Double precoBase) {
        this.capacidade = capacidade;
        this.precoBase = precoBase;
        this.totalIngressosVendidos = 0;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public Double getPrecoBase() {
        return precoBase;
    }

    public int getInteiras() {
        return inteiras;
    }

    public int getMeias() {
        return meias;
    }

    public int getTotalIngressosVendidos() {
        return totalIngressosVendidos;
    }

    // metodos


    public void venderIngresso(TipoIngresso tipo){
        if (totalIngressosVendidos >= capacidade){
            System.out.println("❌ Sessão lotada!");
        }

        if(tipo.getFatorDesconto() == 0.5){
            meias++;
        } else {
            inteiras++;
        }

        totalIngressosVendidos++;
        double precoVenda = precoBase * tipo.getFatorDesconto();
        System.out.println("✅ Ingresso Vendido!\nValor da venda: " + precoVenda);

    }

    public int getIngressosDisponiveis(){
        return capacidade - totalIngressosVendidos;
    }



    public Double getArrecadacaoTotalSessao(){
        double totalArrecadado = 0.0;

        Double valorMeias = (precoBase * meias / 2);
        Double valorInteiras = (precoBase * inteiras);
        totalArrecadado = valorMeias + valorInteiras;

        return totalArrecadado;
    }


    @Override
    public String toString() {
        return "Sessao{" +
                "capacidade=" + capacidade +
                ", precoBase=" + precoBase +
                ", inteiras=" + inteiras +
                ", meias=" + meias +
                ", totalIngressosVendidos=" + totalIngressosVendidos +
                '}';
    }
}
