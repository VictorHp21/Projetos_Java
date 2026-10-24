import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Jogo extends Canvas implements KeyListener, Runnable {

    // Configurações da tela do jogo
    public static int LARGURA = 160;
    public static int ALTURA = 120;
    public static int ESCALA = 3;
    public BufferedImage TelaDoJogo = new BufferedImage(LARGURA, ALTURA, BufferedImage.TYPE_INT_RGB);

    public static  RaqueteJogador objetoRaqueteJogador;
    public static Bola objetoBola;

    public Jogo()
    {
        // configurar tamanho tela
        this.setPreferredSize(new Dimension(LARGURA*ESCALA, ALTURA*ESCALA));

        //iniciar os objetos raquete e bola
        objetoRaqueteJogador = new RaqueteJogador(100, ALTURA - 5);
        objetoBola = new Bola(100, ALTURA/2 - 1);
        // adc manipuladir de eventos do teclado (leitura das teckas)
        this.addKeyListener(new InterrupcaoTeclado(objetoRaqueteJogador));
    }

    public void AtualizarPosicoesObjetos() throws InterruptedException{
        // calcula as novas posições da raquete e bola
        objetoRaqueteJogador.atualizarPosicao();
        objetoBola.AtualizarPosicao();
    }

    public void DesenharJogoNaTela() {
        // criar o frontbuffer
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        // desenhar os objetos do jogo no backbuffer
        Graphics g = TelaDoJogo.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0 , 0, LARGURA, ALTURA);
        objetoRaqueteJogador.Desenhar(g);
        objetoBola.Desenhar(g);

        // transferir a imagem do nackbuffer para o frontbuffer
        g = bs.getDrawGraphics();
        g.drawImage(TelaDoJogo, 0 ,0, LARGURA*ESCALA, ALTURA*ESCALA, null);
        bs.show(); // mostrar o frontbuffer


    }


    @Override
    public void run(){
        while (true){
            // atualizar as posições de todo o objeto do jogo
            try {
                AtualizarPosicoesObjetos();
            } catch (InterruptedException e1){
                e1.printStackTrace();
            }

            // desenhar novamente o jogo
            DesenharJogoNaTela();
            try {
                Thread.sleep(15); // aguardar 15 milisegundos e continuar a execução
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
