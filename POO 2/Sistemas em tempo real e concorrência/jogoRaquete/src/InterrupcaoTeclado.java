import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InterrupcaoTeclado extends KeyAdapter {

    RaqueteJogador objetoRaqueteJogador;

    InterrupcaoTeclado(RaqueteJogador obj){
        objetoRaqueteJogador = obj;
    }

    //@Override
    public void KeyPressed(KeyEvent e){
        try {
            // acesso região critica
            this.objetoRaqueteJogador.Mutex.acquire();
            // verificar se as teclas "-->" E "<--" foram pressionadas
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){

                objetoRaqueteJogador.direita = true;

            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                objetoRaqueteJogador.esquerda = true;

            }
        } catch (InterruptedException e1){
            e1.printStackTrace();
        }

        // liberar região critica
        objetoRaqueteJogador.Mutex.release();
    }


    //@Override
    public void KeyReleased(KeyEvent e){
        try {
            // acesso a regiao critica
            objetoRaqueteJogador.Mutex.acquire();
            // verificar se as teclas foram liberadas
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                objetoRaqueteJogador.direita = false;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                objetoRaqueteJogador.esquerda = false;
            }

        } catch (InterruptedException e1){
            e1.printStackTrace();
        }

        // liberar região critica
        objetoRaqueteJogador.Mutex.release();
    }


    public void KeyTyped(KeyEvent arg0){
        
    }

}
