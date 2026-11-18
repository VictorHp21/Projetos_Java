package Entities;

public class Pontuacao {
    public static final int PONTOS_POR_MORTE   = 100;
    public static final int PONTOS_POR_SEGUNDO = 10;
    public static final int BONUS_VITORIA      = 500;

    //  tempo o aviso ("MORTE DUPLA") fica na tela
    private static final long DURACAO_EVENTO_MS = 2500;

    private int pontosMortes = 0;
    private int motosDestruidas = 0;
    private int bonusVitoria = 0;


    private int recorde = 0;
    private boolean novoRecorde = false;

    private String evento = null;
    private long eventoEm = 0;


    public void reiniciar() {
        pontosMortes = 0;
        motosDestruidas = 0;
        bonusVitoria = 0;
        novoRecorde = false;
        evento = null;
    }


    public void registrarMortes(int quantidade) {
        if (quantidade <= 0) return;

        int multiplicador = quantidade;
        int ganho = quantidade * PONTOS_POR_MORTE * multiplicador;

        pontosMortes += ganho;
        motosDestruidas += quantidade;

        if (quantidade == 1) {
            anunciar("+" + ganho);
        } else if (quantidade == 2) {
            anunciar("MORTE DUPLA x2  +" + ganho);
        } else {
            anunciar("MORTE TRIPLA x3  +" + ganho);
        }
    }

    public void registrarVitoria() {
        bonusVitoria = BONUS_VITORIA;
    }

    // chamado quando a partida termina; atualiza o recorde
    public void finalizar(int segundos) {
        int total = getTotal(segundos);
        novoRecorde = total > recorde;
        if (novoRecorde) recorde = total;
    }

    public int getPontosTempo(int segundos) {
        return segundos * PONTOS_POR_SEGUNDO;
    }

    public int getTotal(int segundos) {
        return pontosMortes + getPontosTempo(segundos) + bonusVitoria;
    }


    public int getRecordeExibido(int segundos) {
        return Math.max(recorde, getTotal(segundos));
    }

    public int getPontosMortes()    { return pontosMortes; }
    public int getMotosDestruidas() { return motosDestruidas; }
    public int getBonusVitoria()    { return bonusVitoria; }
    public boolean isNovoRecorde()  { return novoRecorde; }

    private void anunciar(String texto) {
        evento = texto;
        eventoEm = System.currentTimeMillis();
    }


    public String getEventoAtivo() {
        if (evento == null) return null;
        if (System.currentTimeMillis() - eventoEm > DURACAO_EVENTO_MS) return null;
        return evento;
    }


    public float getOpacidadeEvento() {
        long idade = System.currentTimeMillis() - eventoEm;
        float restante = 1f - (float) idade / DURACAO_EVENTO_MS;
        return Math.max(0f, Math.min(1f, restante * 2f));
    }
}
