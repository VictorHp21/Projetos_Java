package application;

import java.util.Scanner;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Random gerador = new Random();

        int x = gerador.nextInt(10) + 1;

        System.out.println("=== Jogo de Adivinhação ===");

        int y = 0;



        while (y != x) {

            System.out.println("Tente adivinhar o número entre 1 e 10");
            y = sc.nextInt();

            if (y == x) {
                System.out.println("Parabéns! Você acertou!");
            } else {
                System.out.println("Que pena! Você errou.");
                System.out.println("O número gerado era: " + x);
                x = gerador.nextInt(10) + 1;
            }

        }



        sc.close();
    }
}
