package JogoExplosao;

import java.awt.*;
import java.util.Random;

public class ParticulaExplosao extends Rectangle {

    public Color cor;
    public int velocidade = 0;
    public int rotacao = 0;
    public double dx, dy;
    public double x_atual, y_atual;
    public int Contador_tempoParticula = 0;

    public ParticulaExplosao(int x, int y, int largura, int altura, Color cor){
        super(x, y, largura, altura);
        this.cor = cor;
        x_atual = x;
        y_atual = y;
        // deslocamento em angulo de cada particula
        dx = new Random().nextGaussian();
        dy = new Random().nextGaussian();
        velocidade = 8;
    }

    public void update(){
        // atualiza a posicão
        x_atual += dx * velocidade;
        y_atual += dy * velocidade;

        Contador_tempoParticula++;
    }

    public void render(Graphics g){
        g.setColor(cor);

        g.fillRect((int) x_atual, (int) y_atual, width, height);
    }

}
