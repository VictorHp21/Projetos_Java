package Principal;



import javax.swing.*;
import java.awt.*;

public class PainelMenu extends JPanel {

    private final ImageIcon fundo;

    private final JButton botaoPlay;
    private final JLabel insertCoin;

    private final Timer timerPiscar;

    public PainelMenu() {

        setLayout(null);


        fundo = new ImageIcon(
                getClass().getResource("/Imagens e sons/tronBakcground.gif")
        );


        botaoPlay = new JButton("PLAY");

        botaoPlay.setFont(
                new Font("Arial", Font.BOLD, 32)
        );

        botaoPlay.setForeground(Color.CYAN);
        botaoPlay.setBackground(Color.BLACK);

        botaoPlay.setFocusPainted(false);
        botaoPlay.setBorder(
                BorderFactory.createLineBorder(Color.CYAN, 3)
        );

        botaoPlay.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        add(botaoPlay);

        // Mensagem INSERT COIN
        insertCoin = new JLabel("INSERT COIN");

        insertCoin.setFont(
                new Font("Monospaced", Font.BOLD, 25)
        );

        insertCoin.setForeground(Color.CYAN);

        add(insertCoin);

        // Timer para fazer a mensagem piscar
        timerPiscar = new Timer(500, e -> {

            insertCoin.setVisible(
                    !insertCoin.isVisible()
            );

        });

        timerPiscar.start();
    }

    // Posicionamento dos componentes
    @Override
    public void doLayout() {

        int largura = getWidth();
        int altura = getHeight();

        botaoPlay.setBounds(
                largura / 2 - 110,
                altura / 2 - 35,
                220,
                70
        );

        insertCoin.setBounds(
                30,
                altura - 75,
                250,
                40
        );
    }


    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.drawImage(
                fundo.getImage(),
                0,
                0,
                getWidth(),
                getHeight(),
                this
        );
    }

    public JButton getBotaoPlay() {
        return botaoPlay;
    }

    public void pararAnimacao() {
        timerPiscar.stop();
    }
}
