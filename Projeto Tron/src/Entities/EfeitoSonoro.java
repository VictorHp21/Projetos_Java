package Entities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class EfeitoSonoro {

    private Clip clip;

    public EfeitoSonoro(String recurso){
        try {
            URL url = getClass().getResource(recurso);

            if(url == null){
                System.out.println("Som não encontrado: " + recurso + " jogo continua sem som");
                return;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(url);

            clip = AudioSystem.getClip();
            clip.open(audio);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tocar(){
        if(clip == null) return;
        if(clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

}
