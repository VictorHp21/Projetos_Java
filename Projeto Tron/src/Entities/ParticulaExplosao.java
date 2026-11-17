package Entities;

import Config.Config;

import java.awt.*;
import java.util.Random;

public class ParticulaExplosao {
    private static final Random RANDOM = new Random();

    private double x, y;
    private double vx, vy; // velocidade x e y
    private int vida = 0;
    private final int vidaMaxima;
    private final int tamanho;
    private final Color cor;

    public ParticulaExplosao(double x, double y, Color cor){
        this.x = x;
        this.y = y;
        this.cor = cor;

        // direção, angulo e velocida da explosao aleatoria

        // aqui

        double angulo = RANDOM.nextDouble() * Math.PI * 2;
        double velocidade = (1 + RANDOM.nextDouble() * 5) * Config.ESCALA;
        vx = Math.cos(angulo) * velocidade;
        vy = Math.sin(angulo) * velocidade;

        vidaMaxima = 25 + RANDOM.nextInt(25);
        tamanho = (int) ((3 + RANDOM.nextInt(4)) * Config.ESCALA);

    }

    public void update(){
        x += vx;
        y += vy;
        vx *= 0.95; // no atrito particula desacelera
        vy *= 0.95;
        vida++;
    }

    public boolean morreu(){
        return vida >= vidaMaxima;
    }

    public void render(Graphics g){
        // transparência diminui conforme a particula envelhece
        float alpha = 1f - (float) vida / vidaMaxima;
        g.setColor(new Color(cor.getRed(), cor.getGreen(), cor.getBlue(),
                (int)(255 * alpha)));
        g.fillRect((int) x, (int) y, tamanho, tamanho);
    }
}
