
import java.awt.*;
import java.util.concurrent.Semaphore;

public class RaqueteJogador {

    public boolean direita, esquerda;
    public int x, y;
    public int largura_raquete, altura_raquete;
    public Semaphore Mutex;

    public RaqueteJogador(int x, int y){
        this.x = x;
        this.y = y;
        this.largura_raquete = 40;
        this.altura_raquete = 5;
        Mutex = new Semaphore(1);
    }

    public void atualizarPosicao() throws InterruptedException{
        // regiao critica
        Mutex.acquire();

        if(direita) // tecla "->" pressionada?
        {
            x++;
        } else if (esquerda) {
            x--;
        }

        Mutex.release();


        if(x + largura_raquete > Jogo.LARGURA){ // se a posiçõa da raquete ultrapassou o limite da tela(canto direito)
            x = jogo.LARGURA - largura_raquete; // posicionar o canto da bola no final do lado direito
        } else if (x < 0) { // ultrapassou o canto esquerdo
            x = 0; // posicionar o canto da bola no início do lado esquerdo
        }

        public void Desenhar(Graphics g){
            // setar cor da raquete
            g.setColor(Color.PINK);

            // Desenhar o retangulo que representa a raquete
            g.fillRect (x, y, largura_raquete, altura_raquete);
        }


    }


}
