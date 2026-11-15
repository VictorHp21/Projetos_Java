package Entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GeradorExplosao {
    private final List<ParticulaExplosao> particulas = new ArrayList<>();
    private final EfeitoSonoro som = new EfeitoSonoro("/Imagens e sons/explosao.wav");

    // criar uma explosão no ponto x e y com a cor da moto que bateu
    public void explodir(int x, int y, Color cor){
        for (int i = 0; i < 60; i++) {
            particulas.add(new ParticulaExplosao(x, y, cor));
        }

        // criar particulas brancas para brilho no centro
        for (int i = 0; i < 15; i++) {
            particulas.add(new ParticulaExplosao(x, y, Color.WHITE));
        }

        som.tocar();
    }

    public void update() {
        Iterator<ParticulaExplosao> it = particulas.iterator();
        while (it.hasNext()) {
            ParticulaExplosao p = it.next();
            p.update();
            if (p.morreu()) it.remove();
        } // usando iterator para remover particulas
    }

    public void render(Graphics g) {
        for (ParticulaExplosao p : particulas) {
            p.render(g);
        }
    }
}
