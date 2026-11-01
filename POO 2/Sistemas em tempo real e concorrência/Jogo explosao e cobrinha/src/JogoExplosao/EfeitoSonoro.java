package JogoExplosao;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class EfeitoSonoro {
    private Clip clip;

    public EfeitoSonoro(String caminho) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(caminho));
            clip = AudioSystem.getClip();
            clip.open(audio);
        } catch (Exception e) {
            e.printStackTrace();   // se falhar, o jogo continua sem som
        }
    }

    public void tocar() {
        if (clip == null) return;
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);   // volta ao início
        clip.start();
    }
}