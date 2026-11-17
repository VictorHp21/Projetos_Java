package Entities;

import Config.Config;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Moto {

    public char direcao = 'D';

    private int x;
    private int y;

    private final int velocidade = Config.CELULA; // fica mais fácil de configurar --> velocidade acompanha o tamanho da celula

    //point representa par de coordenadas x e y
    private final List<Point> rastro = new ArrayList<>();

    private Image motoCima;
    private Image motoBaixo;
    private Image motoEsquerda;
    private Image motoDireita;

    private final Color corluz;

    private static final int TAMANHO_MAX_RASTRO = 20;
    private boolean viva = true;
    private char direcaoPedida;


    public Moto(int x, int y, String cor, char direcaoInicial){
        this.x = x;
        this.y = y;
        this.direcao = direcaoInicial;
        this.direcaoPedida = direcaoInicial;

        // adc posição inicial da moto ao rastro
        rastro.add(new Point(x,y));

        if(cor.equalsIgnoreCase("azul")){
            corluz = Color.CYAN;

            motoCima = carregarImagem("moto_azul_cima.png");
            motoBaixo = carregarImagem("moto_azul_baixo.png");
            motoEsquerda = carregarImagem("moto_azul_esquerda.png");
            motoDireita = carregarImagem("moto_azul_direita.png");
        } else{
            corluz = Color.ORANGE;

            motoCima = carregarImagem("moto_laranja_cima.png");
            motoBaixo = carregarImagem("moto_laranja_baixo.png");
            motoEsquerda = carregarImagem("moto_laranja_esquerda.png");
            motoDireita = carregarImagem("moto_laranja_direita.png");
        }

    }

    private Image carregarImagem(String nome){
        return new ImageIcon(
                getClass().getResource("/Imagens e sons/" + nome)
        ).getImage();
    }

    public void Desenhar(Graphics g) {

        if(!viva) return;

        Graphics2D g2 = (Graphics2D) g.create();

        // Desenhar o rastro
        g2.setStroke(new BasicStroke(
                5,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND
        ));

        for (int i = 1; i < rastro.size(); i++) {

            Point anterior = rastro.get(i - 1);
            Point atual = rastro.get(i);

            // Efeito de brilho externo
            g2.setColor(new Color(
                    corluz.getRed(),
                    corluz.getGreen(),
                    corluz.getBlue(),
                    70
            ));

            // espessura linha rastro
            g2.setStroke(new BasicStroke(
                    Config.ESPESSURA_BRILHO, // aqui
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND
            ));

            // conecta os pontos
            g2.drawLine(
                    anterior.x,
                    anterior.y,
                    atual.x,
                    atual.y
            );

            // Raio principal
            g2.setColor(corluz);

            g2.setStroke(new BasicStroke(
                    Config.ESPESSURA_RAIO,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND
            ));

            g2.drawLine(
                    anterior.x,
                    anterior.y,
                    atual.x,
                    atual.y
            );
        }

        // Selecionar imagem conforme direção
        Image imagemAtual = switch (direcao) {
            case 'W' -> motoCima;
            case 'S' -> motoBaixo;
            case 'A' -> motoEsquerda;
            case 'D' -> motoDireita;
            default -> motoDireita;
        };

        // Desenhar moto centralizada na posição

        int metade = Config.TAMANHO_MOTO / 2; // aqui tbm

        g2.drawImage(
                imagemAtual,
                x - metade,
                y - metade,
                Config.TAMANHO_MOTO,
                Config.TAMANHO_MOTO,
                null
        );

        g2.dispose();
    }

    public void andar() {

        if(!viva) return;

        direcao = direcaoPedida;

        switch (direcao) {

            case 'A':
                x -= velocidade;
                break;

            case 'D':
                x += velocidade;
                break;

            case 'W':
                y -= velocidade;
                break;

            case 'S':
                y += velocidade;
                break;
        }

        // Registrar nova posição para formar o rastro
        rastro.add(new Point(x, y));

        while (rastro.size() > TAMANHO_MAX_RASTRO){
            rastro.remove(0);
        }
    }

    public void mudarDirecao(char novaDirecao) {

        // Impedir que a moto volte diretamente sobre o próprio caminho
        if ((direcao == 'A' && novaDirecao == 'D') ||
                (direcao == 'D' && novaDirecao == 'A') ||
                (direcao == 'W' && novaDirecao == 'S') ||
                (direcao == 'S' && novaDirecao == 'W')) {

            return;
        }

        direcaoPedida = novaDirecao;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Point> getRastro() {
        return rastro;
    }


    public boolean estaViva(){
        return viva;
    }

    public void matar(){
        viva = false;
    }

    public boolean bateuNoRastroDe(Moto outra){
        if(!outra.estaViva()){
            return false; // rastro de uma moto ja morta n mata
        }

        Point cabeca = new Point(x,y);
        return  outra.getRastro().contains(cabeca);
    }

    public boolean bateuNoProprioRastro(){
        Point cabeca = rastro.get(rastro.size() - 1);

        return rastro.subList(0, rastro.size() - 1).contains(cabeca);
    }

    public char getDirecao(){
        return direcao;
    }

    public Color getCorluz() {
        return corluz;
    }

    public boolean mesmaCor(Moto outra){
        return corluz.equals(outra.corluz);
    }
}
