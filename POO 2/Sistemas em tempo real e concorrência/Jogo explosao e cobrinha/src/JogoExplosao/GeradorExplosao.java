package JogoExplosao;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GeradorExplosao {
    public int Tempo_GeradorRetangulos = 0;
    public ArrayList<RetanguloExplosao> vetor_retangulos = new ArrayList<RetanguloExplosao>();
    public ArrayList<ParticulaExplosao> vetor_particulas = new ArrayList<ParticulaExplosao>();


    public void update() throws InterruptedException{
        Tempo_GeradorRetangulos ++;

        if(Tempo_GeradorRetangulos % 60 == 0){
            RetanguloExplosao objRetangulo = new RetanguloExplosao(0, new Random().nextInt(480 - 40), 40, 40);
            vetor_retangulos.add(objRetangulo);
        }

        for(int i = 0; i < vetor_retangulos.size(); i++){
            RetanguloExplosao current = vetor_retangulos.get(i);
            vetor_retangulos.get(i).update();

            // se retangulo atingir o final da tela
            if(current.x > JogoExplosao.Largura){
                vetor_retangulos.remove(current);

                JogoExplosao.Contador_Tempo--;
            }

            // região critica
            JogoExplosao.Mutex.acquire();

            if(JogoExplosao.MouseClicado){
                // verificar se os pontos (mouse x e y) estão dentro do retangulo
                if(JogoExplosao.mouse_y >= current.x && JogoExplosao.mouse_x < current.x + current.width){
                    if(JogoExplosao.mouse_y >= current.y && JogoExplosao.mouse_y < current.y + current.height){
                        // atingiu o retangulo, então remover
                        vetor_retangulos.remove(current);
                        // jogador pontuou!
                        JogoExplosao.pontuacao++;
                        JogoExplosao.MouseClicado = false;

                        // gerar 50 particulas com a mesma cor do retangulo para simular uma explosão

                        for(int n = 0; n < 50; n++){
                            ParticulaExplosao objParticula = new ParticulaExplosao(current.x, current.y, 8, 8 , current.cor);

                            vetor_particulas.add(objParticula);
                        }

                    }
                }
            }

            // liberar regiao critica
            JogoExplosao.Mutex.release();

        }

        // atualizar a posição de cada particula

        for(int i = 0; i < vetor_particulas.size(); i++){
            vetor_particulas.get(i).update();
            ParticulaExplosao objParticula = vetor_particulas.get(i);

            // apagar da tela particulas que atingiram o time de 30 segundos

            if(objParticula.Contador_tempoParticula >= 30){
                vetor_particulas.remove(objParticula);
            }


        }




    }

    public void render(Graphics g){

        // desenhar na tela cada retangulo

        for(int i = 0; i < vetor_retangulos.size(); i++){
            RetanguloExplosao objRetangulo = vetor_retangulos.get(i);

            // adicionar rotação para cada retangulo
            Graphics2D g2 = (Graphics2D) g;

            g2.rotate(Math.toRadians(objRetangulo.rotation), objRetangulo.x + objRetangulo.width/2, objRetangulo.y + objRetangulo.height/2);

            g2.setColor(objRetangulo.cor);

            g2.fillRect(objRetangulo.x, objRetangulo.y, objRetangulo.width, objRetangulo.height);

            g2.rotate(-Math.toRadians(objRetangulo.rotation), objRetangulo.x + objRetangulo.width/2, objRetangulo.y + objRetangulo.height/2);


        }

        for(int i = 0; i < vetor_particulas.size(); i++){
            vetor_particulas.get(i).render(g);
        }

    }
}
