import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main (String[] args){

        // Set

        // ex 1

        int[] nums = {1, 2, 3, 4, 5, 6, 4, 6, 3, 2};

        Set<Integer> numsNoRepeat = new HashSet<>();
        int duplicados = 0;

        for (int i = 0; i < nums.length; i++) {
            int valor = nums[i];


            if(!numsNoRepeat.add(valor)){
                duplicados++;
            }

        }

        System.out.println("Números sem repetição: " + numsNoRepeat);
        System.out.println("Quantidade de duplicados: " + duplicados);


        System.out.println("--------------------");

        System.out.println();


        // ex 2

        List<String> emails1 = new ArrayList<>();

        emails1.add("ana@gmail.com");
        emails1.add("bruno@hotmail.com");
        emails1.add("carlos@yahoo.com");
        emails1.add("mariana@gmail.com");
        emails1.add("victor@outlook.com");
        emails1.add("fernanda@gmail.com");
        emails1.add("pedro@hotmail.com");
        emails1.add("juliana@yahoo.com");
        emails1.add("ana@gmail.com");
        emails1.add("bruno@hotmail.com");

        List<String> emails2 = new ArrayList<>();

        emails2.add("ana@gmail.com");
        emails2.add("roberto@gmail.com");
        emails2.add("victor@outlook.com");
        emails2.add("camila@hotmail.com");
        emails2.add("pedro@hotmail.com");
        emails2.add("lucas@gmail.com");
        emails2.add("fernanda@gmail.com");
        emails2.add("gabriel@yahoo.com");
        emails2.add("bruno@hotmail.com");
        emails2.add("isabela@outlook.com");


        Set<String> conjunto1 = new HashSet<>(emails1);
        Set<String> conjunto2 = new HashSet<>(emails2);

        // interseção - emails presentes nas duas listas

        Set<String> intersecao = new HashSet<>(conjunto1);

        intersecao.retainAll(conjunto2);
        // retainAll() mantém somente os elementos que também existem no outro conjunto


        System.out.println("Intereseção: " + intersecao);


        // união

        Set<String> uniao = new HashSet<>(conjunto1);

        uniao.addAll(conjunto2);

        System.out.println("União: " + uniao);

        //  diferença (emails que estão apenas na primeira lista)

        Set<String> diferenca = new HashSet<>(conjunto1);

        diferenca.removeAll(conjunto2);

        System.out.println("Diferença: " + diferenca);


        // ex 3




    }
}
