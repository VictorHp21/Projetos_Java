import java.util.Scanner;

public class Main {
    public static void main (String[] args){

        Scanner s = new Scanner(System.in);

        System.out.println("Vetor size: ");
        int n = s.nextInt();

        int[] nums = new int[n];

        for (int i = 0; i < n; i++){
            System.out.println("Num " + i + ": ");
            nums[i] = s.nextInt();
        }

        System.out.println("Target: ");
        int target = s.nextInt();

        int position = positionTarget(nums, target);

        System.out.println("Position: " + position);

    }

    public static int positionTarget(int[] nums, int target){

        int position = 0;



        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                 return i;
            }

        }





        return position = nums.length;
    }
}
