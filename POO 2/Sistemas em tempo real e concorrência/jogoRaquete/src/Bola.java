import java.awt.*;
import java.util.Random;

public class Bola {
    public double x, y;
    public int largura_bola, altura_bola;
    public double dx, dy; // deslocamento nos eixos x e y (calculado em angulos)
    public double velocidade = 0.7;


    public Bola(int x, int y){
        this.x = x;
        this.y = y;
        this.largura_bola = 4;
        this.altura_bola = 4;

        CalcularAnguloDeslocamento();
    }

    private void CalcularAnguloDeslocamento(){
        // gera um angulo aleatório para dar a sensação de um movimento mais realístico para a bola
        int angle = new Random().nextInt(120 - 45) + 45 + 1;
        dx = Math.cos(Math.toRadians(angle));
        dy = Math.sin(Math.toRadians(angle));
    }


    public void AtualizarPosicao(){
        // se a bola atingiu o limite do lado direito
        if(x + (dx * velocidade) + largura_bola >= Jogo.LARGURA){
            dx*= -1;
        } else if (x + (dx * velocidade) < 0)  // atingiu lado esquerdo
        {
            dx*= -1;
        }

        // se a bola ultrapassou o limite inferio da janela => a raquete não rebateu a bola!
        if(y >= Jogo.ALTURA)
        {
            System.out.println("Você perdeu!");
            x = 0;
            y = 0;
            return;
        }

        // definição do rectangle:(x_cantosuperio, y_cantosuperio, largura, altura)
        // delimitar asa regiões em que estão a bola e a raquete
        Rectangle regiaoBola = new Rectangle((int) (x + (dx*velocidade)), (int) (y+(dy*velocidade)), largura_bola, altura_bola);

        Rectangle regiaoRaqueteJogador = new Rectangle(Jogo.objetoRaqueteJogador.x, Jogo.objetoRaqueteJogador.y, Jogo.objetoRaqueteJogador.largura_raquete, Jogo.objetoRaqueteJogador.altura_raquete);

        // verificar colisão da bola com a raquete do jogador
        if(regiaoBola.intersects(regiaoRaqueteJogador)){
            CalcularAnguloDeslocamento();
            if(dy > 0){
                dy*= -1;
            }
        }

        // verificar se a bola ultrapaou o topo da janela do jogo
        if(y < 0){
            CalcularAnguloDeslocamento();
            if(dy < 0){
                dy*=-1;
            }
        }

        // atualizar a posicao da bola
        x+=dx*velocidade;
        y+=dy*velocidade;

    }

    public void Desenhar(Graphics g){
        g.setColor(Color.BLUE);
        // desenhar o retangulo que representa a bola
        g.fillRect((int)x, (int)y, largura_bola, altura_bola);
    }

}
