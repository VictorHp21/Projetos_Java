import java.util.*;

public class Main {
    public static void main (String[] args){
        int[] nums = {4,3,3,1,1,5,2};

        Set<Integer> numsNonrepeat = new TreeSet<>();

        for (int n : nums){
            numsNonrepeat.add(n);
        }

        System.out.println(numsNonrepeat);


    }
}
