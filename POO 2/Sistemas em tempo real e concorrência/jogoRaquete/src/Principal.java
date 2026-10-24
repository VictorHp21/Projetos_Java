import javax.swing.*;

public class Principal extends JFrame {
    public static void main(String[] args){

        // criar instancia jogo
        Jogo game = new Jogo();

        // criar janelo jogo
        JFrame janelaprincipal = new JFrame("Ping Pong");

        // adicionar jogo na tela
        janelaprincipal.add(game);

        // permitir alterar dimensoies na tela de acordo com o jogo
        janelaprincipal.pack();

        // limpar as configurações de posicionamento da tela
        janelaprincipal.setLocationRelativeTo(null);

        // encerrar o programa ao clicar no x da janela
        janelaprincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // n deixar usuário alterar o tamanho da janela
        janelaprincipal.setResizable(false);

        // mostrar a tela

        janelaprincipal.setVisible(true);

        // iniciar a thread do jogo
        new Thread(game).start();


    }
}
