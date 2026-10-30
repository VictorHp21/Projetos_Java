package JogoExplosao;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.concurrent.Semaphore;

public class JogoExplosao extends Canvas implements Runnable {


    public static final int Largura = 640, Altura = 480;
    public static int Contador_Tempo = 100;
    public static int pontuacao = 0;
    public GeradorExplosao objGerador;
    public static int mouse_x, mouse_y;
    public  static boolean MouseClicado = false;
    public boolean JogoAcabou = false;
    public static Semaphore Mutex;

    public JogoExplosao(){
        Dimension dimension = new Dimension(Largura, Altura);
        this.setPreferredSize(dimension);
        this.addMouseListener(new InterrupcaoMouse());
        objGerador = new GeradorExplosao();
        Mutex = new Semaphore(1);
    }


    public void update() throws InterruptedException{
        if(!JogoAcabou){
            objGerador.update();

            if(Contador_Tempo <= 0){
                JogoAcabou = true;
            }
        }
    }

    public void render(){
        // criar front buffer
        BufferStrategy bs = this.getBufferStrategy();


        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        // desenhar cenário do jogo no back buffer
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Largura, Altura);

        if(JogoAcabou == false){
            // mostrar pontuação na tela
            g.setColor(Color.MAGENTA);
            g.setFont(new Font("Arial", Font.BOLD,23));
            g.drawString("Pontos: " + pontuacao, 10, 40);

            // desenhar retangulo que mostra o tempo que falta
            g.setColor(Color.GREEN);
            g.fillRect(Largura/2 - 100 - 70, 20, Contador_Tempo * 3, 20);

            // desehar borda azul ao redor do retangulo que representa o tempo

            g.setColor(Color.BLUE);
            g.drawRect(Largura / 2 - 100-70, 20, 300, 20);

            // colocar objetos na tela
            objGerador.render(g);


        } else {
            g.setColor(Color.MAGENTA);
            g.setFont(new Font("Arial", Font.BOLD,35));
            g.drawString("GAME OVER!!!", Largura /2 - 100, Altura / 2);
            // mostrar front buffer
        }

        bs.show();

    }


    @Override
    public void run() {

        while (true){

            try {
                // atualizar posicao dos objetos
                update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // desenhar objetos na tela

            render();

            try {
                Thread.sleep(16); // espera 16 ms
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

        } 


    }
}
