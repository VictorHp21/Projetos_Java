package Entities;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InterrupcaoTeclado extends KeyAdapter {

    private final Moto moto;

    public InterrupcaoTeclado(Moto moto){
        this.moto = moto;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){

            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                moto.mudarDirecao('A');
                break;

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                moto.mudarDirecao('D');
                break;

            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                moto.mudarDirecao('W');
                break;

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                moto.mudarDirecao('S');
                break;



        }
    }
}
