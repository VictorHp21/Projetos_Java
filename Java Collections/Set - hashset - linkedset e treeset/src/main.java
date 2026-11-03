import java.util.*;

public class main {
    public static void main(String [] args){
        // set não permite elementos duplicados. Ao chamar
        //add() com um elemento já presente, o método retorna false e a coleção não muda. Sets não têm
        //acesso por índice. A noção de 'igual' vem de equals() (e hashCode() nos sets baseados em hash).

        //HashSet -- baseado em uma tabela hash (usa um hashmap interno)

        Set<String> cores = new HashSet<>();
        System.out.println(cores.add("azul")); // true
        System.out.println(cores.add("verde")); // true
        System.out.println(cores.add("azul")); // false (duplicado)
        System.out.println(cores.contains("verde")); // true
        System.out.println(cores.size()); // 2
        cores.remove("verde");


        //LinkedHashSet -- É um HashSet que mantém uma lista encadeada
        // preserva ordem de inserção

        List<Integer> lista = List.of(5, 3, 5, 1, 3, 9);
        Set<Integer> semRepetir = new LinkedHashSet<>(lista);
        System.out.println(semRepetir); // [5, 3, 1, 9]


        //TreeSet -- Mantém os elementos sempre ordenados usando uma árvore rubro-negra (árvore binária balanceada)

        TreeSet<Integer> nums = new TreeSet<>(List.of(40, 10, 30, 20, 50));
        System.out.println(nums); // [10, 20, 30, 40, 50]
        System.out.println(nums.first()); // 10
        System.out.println(nums.floor(35)); // 30
        System.out.println(nums.ceiling(35)); // 40
        System.out.println(nums.headSet(30)); // [10, 20]
        System.out.println(nums.descendingSet()); // [50, 40, 30, 20, 10]
// Ordem personalizada: Strings por tamanho (cuidado: tamanho igual = "duplicado")
        TreeSet<String> porTam = new TreeSet<>(Comparator.comparing(String::length);

        // obs: No TreeSet/TreeMap, quem decide se dois elementos são iguais é o compareTo/compare
        //(resultado 0), e não o equals. Mantenha ambos consistentes.

        // operações de conjuntos

        Set<Integer> a = new TreeSet<>(List.of(1, 2, 3, 4));
        Set<Integer> b = new TreeSet<>(List.of(3, 4, 5, 6));
        Set<Integer> uniao = new TreeSet<>(a); uniao.addAll(b); // [1,2,3,4,5,6]
        Set<Integer> inter = new TreeSet<>(a); inter.retainAll(b); // [3,4]
        Set<Integer> dif = new TreeSet<>(a); dif.removeAll(b); // [1,2]
        boolean sub = a.containsAll(inter); // true (subconjunto)


        // qual set escolher:
        //Só eliminar duplicados e verificar pertencimento rápido -- HashSet
        //Eliminar duplicados mantendo a ordem de inserção -- LinkedHashSet
        //Elementos sempre ordenados, ou buscas por faixa (menor/maior/vizinho) -- TreeSet

    }
}
