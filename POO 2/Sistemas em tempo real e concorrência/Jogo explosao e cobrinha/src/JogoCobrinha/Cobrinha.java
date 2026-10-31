package JogoCobrinha;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Cobrinha {

    public static char direcao = 'D'; // A - ESQUERDA, S -BAIXO, W - CIMA E D -DIREITA
    private int TamanhoDaCobra; // lá ele
    public int QuantidadeComida;

    // vetores das posições x e y do corpo da cobra
    private final int[] eixoX = new int[JogoCobrinha.UNIDADES];
    private final int[] eixoY = new int[JogoCobrinha.UNIDADES];

    public Cobrinha(){
        TamanhoDaCobra = 6;
        QuantidadeComida = 0;
    }

    public void Desenhar(Graphics g){
        for(int i = 0; i < TamanhoDaCobra; i++){
            if(i == 0){ // cabeça
                g.setColor(Color.BLACK);
                g.fillRect(eixoX[0], eixoY[0], JogoCobrinha.TAMANHO_BLOCO, JogoCobrinha.TAMANHO_BLOCO);
            } else {
                g.setColor(new Color(45, 180, 0));
                g.fillRect(eixoX[i], eixoY[i], JogoCobrinha.TAMANHO_BLOCO, JogoCobrinha.TAMANHO_BLOCO);
            }
        }
    }

    public void andar() throws InterruptedException{
        // deslocar
        for (int i = TamanhoDaCobra; i > 0; i--){
            eixoX[i] = eixoX[i - 1];
            eixoY[i] = eixoY[i - 1];
        }

        // regiao critica
        JogoCobrinha.Mutex.acquire();

        // atualizar posicao com base nas teclas pressionadas

        switch (direcao){
            case 'A':
                eixoX[0] = eixoX[0] - JogoCobrinha.TAMANHO_BLOCO;
                break;

            case 'D':
                eixoX[0] = eixoX[0] - JogoCobrinha.TAMANHO_BLOCO;
                break;


            case 'W':
                eixoY[0] = eixoY[0] - JogoCobrinha.TAMANHO_BLOCO;
                break;


            case 'S':
                eixoY[0] = eixoY[0] - JogoCobrinha.TAMANHO_BLOCO;
                break;

            default:

                break;

        }

        // liberar regiao

        JogoCobrinha.Mutex.realese();

    }

    public void TocarEfeito() throws LineUnavailableException, Exception, UnsupportedAudioFileException{
        File arquivo = new File("C:/Users/victo/OneDrive/Desktop/PROJETOS/Projetos Java/POO 2/Sistemas em tempo real e concorrência/Jogo explosao e cobrinha/imagem e sons/somPotuacao.wav");

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(arquivo);

        // cria clip para reproduzir o arquivo
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);

        // reproduz
        clip.start();
    }


    public boolean alcancouComida() throws LineUnavailableException, IOException, UnsupportedAudioFileException,
            Exception{ // testar
        // verifica se pegou a camida
        if(eixoX[0] == Comida.posicao_x && eixoY[0] == Comida.posicao_y){
            TamanhoDaCobra++;
            QuantidadeComida++;

            // som
            TocarEfeito();

            return true;
        } else {
            return false;
        }
    }

    public boolean VerificarGameOver(){
        boolean perdeu = false;

        // cabeça bateu  no corpo
        for(int i = TamanhoDaCobra; i > 0; i--){
            if(eixoX[0] == eixoX[i] && eixoY[0] == eixoY[0]){
                perdeu = true;
                break;
            }
        }

        // cabeça tocou nas bordas
        if(eixoX[0] < 0 || eixoX > JogoCobrinha.LARGURA_TELA){
            perdeu = true;
        }

        if(eixoY[0] < 0 || eixoY > JogoCobrinha.LARGURA_TELA){
            perdeu = true;
        }

        return perdeu;
    }

}
