package Entities;

import javax.swing.*;
import java.awt.*;

public class JogoTron extends JPanel {
    private Moto motoAzul;

    private Image fundo;

    public JogoTron() {

        fundo = new ImageIcon(
                getClass().getResource("/Imagens e sons/fundo.png")
        ).getImage();

        setPreferredSize(new Dimension(480, 480));
        //setBackground(Color.BLACK);

        motoAzul = new Moto(128, 240, "azul");

        addKeyListener(new InterrupcaoTeclado(motoAzul));

        // painel recebe o foco do teclado
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


        // desenhar a imagem de fundo
        g.drawImage(
                fundo,
                0,0,
                getWidth(),
                getHeight(),
                this
        );

        motoAzul.Desenhar(g);
    }
}
