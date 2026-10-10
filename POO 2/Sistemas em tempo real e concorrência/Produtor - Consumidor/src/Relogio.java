import javax.swing.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

public class Relogio extends TimerTask {
    JLabel interfacegrafica;

    Relogio(JLabel label){
        interfacegrafica = label;
    }

    @Override
    public void run(){
        try { // formatar a hora em minutos segundos
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime localTime = LocalTime.now(); //pegar horario atual do sistema
            interfacegrafica.setText(formatoHora.format(localTime)); // mostrar na tela
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
