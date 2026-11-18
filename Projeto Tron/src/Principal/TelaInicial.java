
package Principal;

import Config.Musica;
import Entities.JogoTron;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicial extends JFrame implements ActionListener {

    private final PainelMenu painelMenu;
    private final Musica musica;

    private JogoTron jogo;

    public TelaInicial() {

        setTitle("TRON ARCADE");

        setSize(1000, 700);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);


        painelMenu = new PainelMenu();

        setContentPane(painelMenu);


        musica = new Musica("/Imagens e sons/tronAudio.wav");

        musica.tocar();


        painelMenu.getBotaoPlay().addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == painelMenu.getBotaoPlay()) {

            iniciarJogo();

        }
    }

    private void iniciarJogo() {

        painelMenu.pararAnimacao();

        jogo = new JogoTron();

        jogo.setAoReiniciar(() -> musica.reiniciar());

        setContentPane(jogo);


        pack();

        setResizable(false);

        setLocationRelativeTo(null);

        revalidate();
        repaint();

        jogo.iniciar();

        jogo.requestFocusInWindow();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(TelaInicial::new);

    }
}
