import java.util.Scanner;

public class Main {
    public static void main (String[] args){
        Scanner s = new Scanner(System.in);

        System.out.println("Put on String: ");
        String str = s.next();


    }

    public int myAtoi(String s){
       // s = s.trim(); // ignorar os espaços.

        int i = 0;
        int sign = 1;

        while (i < s.length() && s.charAt(i) == ' '){
            i++;

        }

        if (i < s.length()){
            if (s.charAt(i) == '-'){
                sign = -1;
                i++;
            } else if (s.charAt(i) == '+'){
                i++;
            }
        }

        long result = 0;

        while (i < s.length() && Character.isDigit(s.charAt(i))){
            int digit = s.charAt(i) - '0';

            result = result * 10 + digit;

            // verificar limite de 32 bits

            if (sign * result <= Integer.MIN_VALUE){
                return Integer.MIN_VALUE;
            }
            if (sign * result >= Integer.MAX_VALUE){
                return Integer.MAX_VALUE;
            }

        }

        return (int) (sign * result);


    }
}
