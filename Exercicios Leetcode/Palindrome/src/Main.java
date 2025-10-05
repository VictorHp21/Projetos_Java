import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("Vetor size: ");
        int n = s.nextInt();

        int[] num = new int[n];

        for (int i = 0; i < n; i++) {
            num[i] = s.nextInt();
        }

        for (int i = 0; i < n; i++) {
            if (isPalindrome(num)) {
                System.out.println("Yes, it a palindrome");
                break;
            } else {
                System.out.println("isnt a palindrome");
                break;
            }
        }

    }

    public static boolean isPalindrome(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != nums[nums.length - 1 - i]) {
                return false;
            }
        }
        return true;
    }
}
