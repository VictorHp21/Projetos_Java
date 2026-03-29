import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /*Ex 09: Faça um algoritmo que calcule e apresente o valor do volume de uma lata de óleo, dado
                 seu raio e sua altura.

                     // volume cilindro: v = pi * r^2 * h
                     */

        Scanner s = new Scanner(System.in);

        System.out.println("Digite o raio da lata: ");
        float r = s.nextFloat();

        System.out.println("Digite a altura da lata: ");
        float h = s.nextFloat();

        double pi = Math.PI;
        double volume = pi * (r*r) * h;

        System.out.println("O volume da lata é: " + volume);


    }
}

