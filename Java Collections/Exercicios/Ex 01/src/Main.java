import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args){

        List<Integer> nums = new ArrayList<>();

        for(int i = 1; i < 21; i++){
            nums.add(i);
        }

        Iterator<Integer> it = new ArrayList<>(nums).iterator();

        System.out.println("Imprimindo lista (com iterator)");

        while (it.hasNext()){
            System.out.print(it.next() + ", ");
        }

    }
}
