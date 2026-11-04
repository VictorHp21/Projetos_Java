import java.util.*;

public class Main {
    public static void main (String[] args){

        //HashMap



        Map<String, Integer> idades = new HashMap<>();
        idades.put("Ana", 20);
        idades.put("Bruno", 25);
        idades.put("Ana", 21); // substitui: Ana -> 21
        System.out.println(idades.get("Ana")); // 21
        System.out.println(idades.get("Zé")); // null
        System.out.println(idades.getOrDefault("Zé", 0)); // 0
        idades.putIfAbsent("Carla", 30);


        //for each em um map

        for (Map.Entry<String, Integer> e : idades.entrySet()) {
            System.out.println(e.getKey() + " = " + e.getValue());
        }

        System.out.println("teste");

        idades.forEach((nome, idade) -> System.out.println(nome + ": " + idade));



        // Padrão clássico: contar frequências
        String texto = "banana";
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : texto.toCharArray()) {
            freq.merge(c, 1, Integer::sum); // {a=3, b=1, n=2}
        }
     // Agrupar: nome do curso -> lista de alunos
        Map<String, List<String>> porCurso = new HashMap<>();
        porCurso.computeIfAbsent("ADS", k -> new ArrayList<>()).add("Ana");
        porCurso.computeIfAbsent("ADS", k -> new ArrayList<>()).add("Bruno");


        //LinkedHashMap -- Hashmap com uma lista duplamente encadeada

        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("c", 3); m.put("a", 1); m.put("b", 2);
        System.out.println(m); // {c=3, a=1, b=2} (ordem de inserção)
        class LruCache<K, V> extends LinkedHashMap<K, V> {
            private final int capacidade;
            LruCache(int capacidade) {
                super(16, 0.75f, true); // true = ordem de acesso
                this.capacidade = capacidade;
            }
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > capacidade; // remove o menos usado
            }
        }


        //TreeMap -- mantém as estradas ordenadas pela chave (devem ser comparable)

        TreeMap<Integer, String> notas = new TreeMap<>();
        notas.put(0, "Reprovado");
        notas.put(5, "Recuperação");
        notas.put(9, "Excelente");
        notas.put(7, "Aprovado");

        double nota = 6.4;
        System.out.println(notas.floorEntry((int) nota).getValue()); // Recuperação
        System.out.println(notas.firstKey()); // 0
        System.out.println(notas.headMap(7)); // {0=..., 5=...}


        notas.forEach((n, situacao) -> System.out.println(n + " situação: " + situacao));


        // Qual map escolher: Busca por chave o mais rápida possível, ordem não importa HashMap
        //Iterar na ordem de inserção (ou criar cache LRU) LinkedHashMap
        //Chaves ordenadas ou consultas por faixa/vizinho TreeMap
        //Acesso por várias threads ConcurrentHashMap




    }
}
