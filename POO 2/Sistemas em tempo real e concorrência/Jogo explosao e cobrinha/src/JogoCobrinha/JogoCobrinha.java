package JogoCobrinha;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
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

    private boolean somDerrotaTocado = false;


    public JogoCobrinha(){
        setPreferredSize(new Dimension(LARGURA_TELA, ALTURA_TELA));
        setBackground(Color.WHITE);
        setFocusable(true);

        addKeyListener(new InterrupcaoTeclado());
        objetoCobra = new Cobrinha();
        objetoComida = new Comida();
        objetoComida.CriarNovaPosicao();
        GameOver = false;
        Mutex = new Semaphore(1);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        try {
            desenharTela(g);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e){
            e.printStackTrace();
        }
    }


    public void desenharTela(Graphics g) throws LineUnavailableException, IOException, UnsupportedAudioFileException{

        if(GameOver == false){
            objetoComida.Desenhar(g);
            objetoCobra.Desenhar(g);

            // mostrar pontuação
            g.setColor(Color.red);
            g.setFont(new Font(NOME_FONTE, Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            String texto = "Pontos: " + objetoCobra.QuantidadeComida;
            g.drawString(texto, (LARGURA_TELA - metrics.stringWidth(texto)) / 2, g.getFont().getSize());
        } else{
            fimDeJogo(g);
        }




    }


    public void fimDeJogo(Graphics g) throws LineUnavailableException, IOException, UnsupportedAudioFileException{

        // mostrar msg fim de jogo
        g.setColor(Color.red);
        g.setFont(new Font(NOME_FONTE, Font.BOLD, 75));
        FontMetrics fontefinal = getFontMetrics(g.getFont());

        g.drawString("Fim do Jogo.", (LARGURA_TELA - fontefinal.stringWidth("Fim do Jogo")) / 2, ALTURA_TELA / 2);

        // executar som de derrota
        if (!somDerrotaTocado) {
            somDerrotaTocado = true;   // marca antes, para tocar só uma vez

            File arquivo = new File("C:\\Users\\victo\\OneDrive\\Desktop\\PROJETOS\\Projetos Java\\POO 2\\Sistemas em tempo real e concorrência\\Jogo explosao e cobrinha\\imagem e sons\\SomPerdeu.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(arquivo);

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
    }



    @Override
    public void run() {

        while (GameOver == false){
            try{
                objetoCobra.andar();
            } catch (InterruptedException e){
                e.printStackTrace();
            }


            try {
                if (objetoCobra.alcancouComida() == true){
                    objetoComida.CriarNovaPosicao();
                }
            } catch (Exception e){
                e.printStackTrace();
            }


            GameOver = objetoCobra.VerificarGameOver();
            repaint(); // chama internamente o metodo "paintComponent"

            try{
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }
}
