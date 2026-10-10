import javax.swing.*;
import java.util.Timer;

public class Temporizador extends Timer {

    private Timer timer;
    private Relogio relogionatela;

    public Temporizador(JLabel labelInterface){
        timer = new Timer();
        relogionatela = new Relogio(labelInterface);
        timer.schedule(relogionatela, 0, 1000);
    }
}
