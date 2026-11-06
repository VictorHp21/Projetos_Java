import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Main {
    public static void main(String[] args){

        List<String> nomes = new ArrayList<>(
                List.of("Ana", "Breno", "Bruno", "Ster", "Jhonatam", "Rebecca")
        );

        // na segunda posição

        ListIterator<String> it = nomes.listIterator();



        while (it.hasNext()) {

            String nome = it.next();


            if (it.previousIndex() == 0) {
                it.set("Pedro");

                System.out.println("Após substituir:");
                System.out.println(nomes);
            }


            if (it.nextIndex() == 1) {
                it.add("Diana");

                System.out.println("Após adicionar:");
                System.out.println(nomes);
            }


            if (!it.hasNext()) {
                it.remove();

                System.out.println("Após remover:");
                System.out.println(nomes);
            }
        }



        

    }
}
