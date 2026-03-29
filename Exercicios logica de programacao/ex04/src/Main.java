import java.util.Scanner;

public class Main {
    public static void main (String[] args){

        /*Ex 04: Dado os três lados de um triângulo determinar o perímetro do mesmo.  */

        Scanner s = new Scanner(System.in);

        System.out.println("Digite o valor do lado 1");
        int l1 = s.nextInt();

        System.out.println("Digite o valor do lado 2");
        int l2 = s.nextInt();

        System.out.println("Digite o valor do lado 3");
        int l3 = s.nextInt();

        System.out.println("Perímetro do triângulo: " + (l1+l2+l3));



    }
}
