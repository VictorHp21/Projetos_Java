import java.util.Scanner;

public class Main {
    public static void main (String[] args){

        /*Ex 02: Dado o tamanho do lado de um quadrado, calcular a área e o perímetro do mesmo. */

        Scanner s = new Scanner(System.in);

        System.out.println("Lado do quadrado: ");
        int l = s.nextInt();

        System.out.println("Área: " + l*l);

        System.out.println("Perímetro: " + 4 * l);

    }
}
