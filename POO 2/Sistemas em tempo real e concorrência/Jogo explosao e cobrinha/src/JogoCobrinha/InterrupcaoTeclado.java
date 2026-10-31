package JogoCobrinha;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InterrupcaoTeclado extends KeyAdapter {
    public void KeyPressed(KeyEvent e){
        // regiao critica
        try {
            JogoCobrinha.Mutex.acquire();
        } catch (InterruptedException e1){
            e1.printStackTrace();
        }

        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT: // esquerda
                if(Cobrinha.direcao != 'D'){
                    Cobrinha.direcao = 'A';

                }

                break;


            case KeyEvent.VK_RIGHT: // direita
                if (Cobrinha.direcao != 'E'){
                    Cobrinha.direcao = 'D';
                }

                break;


            case KeyEvent.VK_UP: // para cima
                if(Cobrinha.direcao != 'A'){
                    Cobrinha.direcao = 'W';
                }

                break;


            case KeyEvent.VK_DOWN: // para cima
                if(Cobrinha.direcao != 'W'){
                    Cobrinha.direcao = 'A';
                }

                break;


            default:

                break;
        }

        // liberar regiao critica

        JogoCobrinha.Mutex.release();
    }
}
