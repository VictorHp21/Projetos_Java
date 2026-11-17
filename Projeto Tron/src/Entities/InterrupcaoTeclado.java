package Entities;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InterrupcaoTeclado extends KeyAdapter {

    private final JogoTron jogo;

    public InterrupcaoTeclado(JogoTron jogo) {
        this.jogo = jogo;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){

            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                jogo.pedirDirecao('A');
                break;

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                jogo.pedirDirecao('D');
                break;

            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                jogo.pedirDirecao('W');
                break;

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                jogo.pedirDirecao('S');
                break;

            case KeyEvent.VK_ENTER:
                jogo.reiniciarSeAcabou();
                break;



        }
    }
}
