import java.util.Scanner;

public class Main {
    public static void main (String[] args){

        /*Ex 03: Dado o tamanho do raio de uma circunferência, calcular a área e o perímetro da mesma.  */

        Scanner s = new Scanner(System.in);

        //pi
        double pi = Math.PI;

        System.out.println("Digite o raio: ");
        float r = s.nextFloat();

        // area
        System.out.println("Área da circunferência: " + pi * (r*r));

        // perímetro
        System.out.println("Perímetro da circunferência: " + 2 * (pi * r));


    }
}
