import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("Enter a word: ");
        String word = s.next();

        char[] lett = word.toCharArray();

        boolean result = isPalindrome(lett);

        if (!result){
            System.out.println("This word ins´t a palindrome");
        } else {
            System.out.println("Yes, is a palindrome");
        }





    }

    public static boolean isPalindrome(char[] l) {

        char[] invers = new char[l.length];

           for (int i = 0; i < l.length; i++){
               invers[i] = l[l.length - 1 - i];
           }

           for (int i = 0; i < l.length; i++){
               if(l[i] != invers[i]){
                   return false;
               }
           }

           return true;


    }
}
