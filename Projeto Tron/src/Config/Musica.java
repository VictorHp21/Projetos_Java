package Config;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Musica {

    private Clip clip;

    public Musica(String caminho) {

        try {
            URL arquivo = getClass().getResource(caminho);

            if (arquivo == null) {
                throw new IllegalArgumentException(
                        "Arquivo de música não encontrado: " + caminho
                );
            }

            AudioInputStream audio =
                    AudioSystem.getAudioInputStream(arquivo);

            clip = AudioSystem.getClip();
            clip.open(audio);

            audio.close();

        } catch (UnsupportedAudioFileException |
                 IOException |
                 LineUnavailableException e) {

            e.printStackTrace();
        }
    }


    public void tocar() {

        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }


    public void reiniciar() {

        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }


    public void parar() {

        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
        }
    }


    public void fechar() {

        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
}
