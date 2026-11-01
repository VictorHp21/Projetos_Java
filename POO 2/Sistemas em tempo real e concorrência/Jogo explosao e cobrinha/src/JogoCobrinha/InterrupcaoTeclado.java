package JogoCobrinha;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InterrupcaoTeclado extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e){

        System.out.println("tecla: " + e.getKeyCode());

        // regiao critica
        try {
            JogoCobrinha.Mutex.acquire();
        } catch (InterruptedException e1){
            e1.printStackTrace();
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if (Cobrinha.direcao != 'D') {
                    Cobrinha.direcao = 'A';
                }
                break;

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if (Cobrinha.direcao != 'A') {
                    Cobrinha.direcao = 'D';
                }
                break;

            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if (Cobrinha.direcao != 'S') {
                    Cobrinha.direcao = 'W';
                }
                break;

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (Cobrinha.direcao != 'W') {
                    Cobrinha.direcao = 'S';
                }
                break;

            default:
                break;
        }

        // liberar regiao critica

        JogoCobrinha.Mutex.release();
    }
}
