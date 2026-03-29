import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /*Ex 01:Dado o tamanho da base e da altura de um retângulo, calcular a sua área e o seu
            perímetro.
        * */

        // Area base x altura

        // perímetro 2 * (base x altura)

        Scanner s = new Scanner(System.in);

        System.out.println("Digite a base do retângulo: ");
        int base = s.nextInt();

        System.out.println("Digite a altura: ");
        int altura = s.nextInt();

        System.out.println("A área do retângulo é: " + base * altura);

        System.out.println("O perímetro do retângulo é: " +  2 * (base + altura));



    }
}
