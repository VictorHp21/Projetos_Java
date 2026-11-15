package Entities;

import javax.swing.*;
import java.awt.*;

public class JogoTron extends JPanel {
    private Moto motoAzul;

    public JogoTron() {

        setPreferredSize(new Dimension(480, 480));
        setBackground(Color.BLACK);

        motoAzul = new Moto(128, 240, "azul");

        addKeyListener(new InterrupcaoTeclado(motoAzul));

        setFocusable(true);

        Timer timer = new Timer(100, e -> {

            motoAzul.andar();

            repaint();
        });

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        motoAzul.Desenhar(g);
    }
}
