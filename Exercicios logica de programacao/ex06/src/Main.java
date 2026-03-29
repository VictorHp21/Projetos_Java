import java.util.Scanner;

public class Main {
    public static void main (String[] args){

        /*Ex 06: Ler dois números inteiros e exibir o quociente e o resto da divisão inteira entre eles.   */

        Scanner s = new Scanner(System.in);

        System.out.println("Digite o valor 1: ");
        int n1 = s.nextInt();

        System.out.println("Digite o valor 2: ");
        int n2 = s.nextInt();

        System.out.println("Quociente: " + n1/n2 + "\nResto da divisão: " + n1%n2);






    }
}
