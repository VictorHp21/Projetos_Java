import java.util.Scanner;

public class Main {
    public static void main (String[] args){

        /*Ex 05: Ler um número inteiro e exibir o seu sucessor.   */

        Scanner s = new Scanner(System.in);

        System.out.println("Digite o valor: ");
        int n = s.nextInt();
        int sucessor = ++n; // pre incremento

        System.out.println("Seu sucessor é: " + sucessor);





    }
}
