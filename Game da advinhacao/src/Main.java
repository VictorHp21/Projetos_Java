import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Random random = new Random();
        int num  = random.nextInt(10) + 1;

        Scanner s = new Scanner(System.in);

        int tentativa = 0;
        int ntentaiva = 0;

        while (true){
            System.out.println("Digite um número de 1 a 10: ");
            tentativa = s.nextInt();

            if (tentativa > num) {
                System.out.println("Tente de novo, o numero gerado é menor");
                ntentaiva++;
            } else if (tentativa < num){
                System.out.println("Tente de novo, o numero gerado é maior");
                ntentaiva++;
            } else {
                ntentaiva++;
                System.out.println("Parabéns!!!");
                System.out.println("O número gerado foi: " + num);
                System.out.println("Você acertou em: " + ntentaiva + " tentativas!");
                break;
            }
        }








    }
}
