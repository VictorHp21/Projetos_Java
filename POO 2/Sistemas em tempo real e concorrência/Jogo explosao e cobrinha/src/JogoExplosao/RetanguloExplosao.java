package JogoExplosao;

import java.awt.*;
import java.util.Random;

public class RetanguloExplosao extends Rectangle {

    public Color cor;
    public int velocidade = 0;
    public int rotation = 0;

    public RetanguloExplosao(int x, int y, int largura, int altura){
        super(x,y,largura,altura);
        // gerar cor aleatoria para o retangulo
        int R = new Random().nextInt(255);
        int G = new Random().nextInt(255);
        int B = new Random().nextInt(255);

        cor = new Color(R, G, B);

        velocidade = new Random().nextInt(4) + 2;
    }

    public void update(){
        x += velocidade;
        rotation ++;
         // se atingir o angulo maximo, zerar
        if(rotation >= 360){
            rotation = 0;
        }
    }

}
