import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args){

        List<String> nomes = new ArrayList<>(List.of(
                "Ana",
                "João",
                "Carlos",
                "Bia",
                "Roberto"
        ));

        Iterator<String> n = nomes.iterator();

        while (n.hasNext()){
            //nomes.removeIf(p -> p.length() < 4);
            String nome = n.next();

            if(nome.length() < 4){
                n.remove();
            } else{
                System.out.println(nome);
            }




        }





        // como removeif

        Iterator<String> it2 = nomes.iterator();

        System.out.println("Com removeIF");

        while (it2.hasNext()){
            String nome = it2.next();

            nomes.removeIf(p -> p.length() < 4);

            System.out.println(nome);
        }


        nomes.forEach(System.out::println);

        // com compare

        System.out.println("Compare (maior para menor nome: )");

        Comparator<String> comp = (a, b) -> Integer.compare(b.length(), a.length());

        nomes.sort(comp); // ordenei do menor para o maior nome

        nomes.forEach(System.out::println);

    }


}
