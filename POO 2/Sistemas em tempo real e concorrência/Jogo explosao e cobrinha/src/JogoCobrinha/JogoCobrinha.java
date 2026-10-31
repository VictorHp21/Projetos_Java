package JogoCobrinha;

import javax.swing.*;
import java.util.concurrent.Semaphore;

public class JogoCobrinha extends JPanel implements Runnable {

    public static final int LARGURA_TELA = 1300;
    public static final int ALTURA_TELA = 750;
    public static final int TAMANHO_BLOCO = 50;
    public static final int UNIDADES = LARGURA_TELA * ALTURA_TELA / (TAMANHO_BLOCO * TAMANHO_BLOCO);
    public static final int INTERVALO = 200;
    public static final String NOME_FONTE = "Ink Free";
    public boolean GameOver = false;
    Cobrinha objetoCobra;
    Comida objetoComida;
    public static Semaphore Mutex;


    public Jogo(){}



    @Override
    public void run() {

    }
}
