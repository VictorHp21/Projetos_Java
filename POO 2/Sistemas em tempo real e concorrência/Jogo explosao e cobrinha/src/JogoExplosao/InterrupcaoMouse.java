package JogoExplosao;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InterrupcaoMouse implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // regiao critica
        try {
            JogoExplosao.Mutex.acquire();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        JogoExplosao.MouseClicado = true;
        JogoExplosao.mouse_x = e.getX();
        JogoExplosao.mouse_y = e.getY();

        // liberar regiao
        JogoExplosao.Mutex.release();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
