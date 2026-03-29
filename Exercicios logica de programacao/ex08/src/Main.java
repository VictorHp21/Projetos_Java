import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /*Ex 08: Dado que a fórmula para conversão de Fahrenheit para Celsius é C = 5/9 (F – 32), leu um
                 valor de temperatura em Fahrenheit e exibi-lo em Celsius    */

        Scanner s = new Scanner(System.in);

        System.out.println("Digite o valor da temperatura em Fahrenheit: ");
        float f = s.nextInt();

        float c =  (f - 32) * 5 / 9 ;

        System.out.println("Temp em Celsius: " + c);


    }
}
