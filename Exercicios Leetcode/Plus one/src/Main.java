public class Main {
    public static void main (String[] args){

        int[] nums = {4,3,2,1};

        int[] result = plusOne(nums);

        for (int r: result){
            System.out.print(r + ", ");
        }

    }

    public static  int[] plusOne(int[] n){

        int totalValue = 0;

        for (int j : n) {
            totalValue = totalValue * 10 + j;
        }

        totalValue += 1;

        String str = String.valueOf(totalValue);
        int[] result = new int[str.length()];

        for (int i = 0; i < str.length(); i++){
            result[i] = Character.getNumericValue(str.charAt(i));
        }

        return result;

    }

}
