import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /*Ex 10: Converter um inteiro informado menor que 32 para sua representação em binário
         */

        Scanner s = new Scanner(System.in);

        System.out.println("Digite um número inteiro: ");
        int n = s.nextInt();

        if (n > 32){
            System.out.println("Digite um número menor ou igual a 32");
        }

        int quociente = 1;
        StringBuilder resto = new StringBuilder();
        while (quociente != 0){
            resto.append(n % 2);
            quociente = n / 2;
            n = n/2;
        }

        resto.reverse();

        System.out.println(resto);

    }
}

