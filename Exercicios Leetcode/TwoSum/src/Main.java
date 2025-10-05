import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);





        System.out.println("Vetor size: ");
        int n = s.nextInt();

        int[] nums = new int[n];

        for (int i = 0; i < n; i++){
            System.out.println("Element " + i + ": ");
            nums[i] = s.nextInt();
        }

        System.out.println("Target: ");
        int target = s.nextInt();

        int[] result = twoSum(nums, target);

        System.out.println(result[0] + ", " + result[1]);






    }

    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++){
            for (int j = i + 1; j < nums.length; j++){
                if (nums[i] + nums[j] == target){
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {};
    }
}
