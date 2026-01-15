package Model.entities;

import java.util.*;

public class Cinema {
    private Map<SessaoKey, Sessao> sessoes = new TreeMap<>(Comparator.comparing(SessaoKey::getHorario));

    private List<SessaoKey> listaIdsessoes = new ArrayList<>();

    public Cinema(){};


    public List<SessaoKey> getListaIdsessoes() {
        return listaIdsessoes;
    }

    public Map<SessaoKey, Sessao> getSessoes() {
        return sessoes;
    }

    public void adicionarSessao(SessaoKey chave, Sessao sessao) {
        sessoes.put(chave, sessao);
    }

    public Double arrecadacaoTotalSessoes(){
        double arrecadacaoTotalGeral = 0.0;

        for (Map.Entry<SessaoKey, Sessao> se: sessoes.entrySet()){
           arrecadacaoTotalGeral += se.getValue().getArrecadacaoTotalSessao();
        }

        return arrecadacaoTotalGeral;
    }

    public void Listarsessoes(){
        int i = 1;

        for (Map.Entry<SessaoKey, Sessao> se: sessoes.entrySet()){
            System.out.println("Sessão: " + i);
            System.out.println("Horário: " + se.getKey().getHorario());
            System.out.println("Filme: " + se.getKey().getFilme());
            System.out.println("Capacidade total da sessão: " + se.getValue().getCapacidade());
            System.out.println("Valor de ingresso(INTEIRA) da sessão: " + se.getValue().getPrecoBase());
            System.out.println("Número de ingressos diponíveis para venda: " + se.getValue().getIngressosDisponiveis());

            listaIdsessoes.add(se.getKey());
            i++;
        }
    }




}
