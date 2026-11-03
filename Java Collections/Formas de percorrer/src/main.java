import java.util.*;

public class main {
    public static void main(String[] args){

        List<String> nomes = List.of("Ana", "Breno", "Carla");

        // for-each
        for (String n: nomes){
            System.out.println(n);
        }

        // for-each com lambda
        nomes.forEach(System.out::println);

        // Iterator (permite remover com segurança)
        Iterator<String> it = new ArrayList<>(nomes).iterator(); // cria uma copia

        while(it.hasNext()){
            String n = it.next();
            if(n.startsWith("B")){
                it.remove(); // remove da copia
            }
        }

        // para remover da lista seria preciso uma lista mutável
        // dessa forma: List<String> nomes = new ArrayList<>(List.of("Ana", "Bruno", "Carla"));

        // Remover elementos dentro de um for-each lança ConcurrentModificationException.
        //Use Iterator.remove() ou removeIf().

        List<Integer> nums = new ArrayList<>(List.of(1,2,3,4,5));

        nums.removeIf( n -> n % 2 == 0);

        nums.forEach(System.out::println);

        // alguns metodos em arraylist:

        List<String> nomes2 = new ArrayList<>();
        nomes2.add("Ana");
        nomes2.add("Bruno");
        nomes2.add(1, "Carla"); // [Ana, Carla, Bruno]
        nomes2.set(0, "Ana Maria"); // [Ana Maria, Carla, Bruno]
        System.out.println(nomes2.get(2)); // Bruno
        System.out.println(nomes2.indexOf("Carla")); // 1
        nomes2.remove("Bruno"); // por objeto
        nomes2.remove(0); // por índice
        Collections.sort(nomes2);


        //LinkedList: lista duplamente encadeada: cada nó guarda o elemento e referências para o anterior e o
        //próximo

        LinkedList<String> fila = new LinkedList<>();
        fila.add("B");
        fila.addFirst("A"); // [A, B]
        fila.addLast("C"); // [A, B, C]
        fila.removeFirst(); // remove A
        System.out.println(fila.getFirst()); // B


        // obs: ArrayList vence LinkedList na grande maioria dos casos por usar memória
        //contígua (melhor cache). Escolha LinkedList só se fizer muitas inserções/remoções nas pontas ou via
        //iterator

    }
}
