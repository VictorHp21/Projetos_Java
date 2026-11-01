package JogoCobrinha;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Comida {
    public static int posicao_x;
    public static int posicao_y;
    Random random;

    public Comida(){
        random = new Random();
    }

    public void CriarNovaPosicao(){
        posicao_x = random.nextInt(JogoCobrinha.LARGURA_TELA / JogoCobrinha.TAMANHO_BLOCO) * JogoCobrinha.TAMANHO_BLOCO;
        posicao_y = random.nextInt(JogoCobrinha.ALTURA_TELA / JogoCobrinha.TAMANHO_BLOCO) * JogoCobrinha.TAMANHO_BLOCO;
    }

    public void Desenhar(Graphics g){
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\victo\\OneDrive\\Desktop\\PROJETOS\\Projetos Java\\POO 2\\Sistemas em tempo real e concorrência\\Jogo explosao e cobrinha\\imagem e sons\\Comida.png");
        Image image = imageIcon.getImage();
        g.drawImage(image, posicao_x, posicao_y, null);
    }
}
