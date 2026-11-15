package Principal;

import Entities.JogoTron;

import javax.swing.*;

public class Main {
    public static void main(String[] args){

        JFrame janela = new JFrame("Tron");

        JogoTron jogo = new JogoTron();

        janela.add(jogo);

        janela.pack();

        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        janela.setLocationRelativeTo(null);

        janela.setVisible(true);

        jogo.requestFocusInWindow();

    }
}
