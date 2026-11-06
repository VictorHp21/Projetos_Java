import java.util.*;

public class Main {
    public static void main(String[] args){


        // Queue (fila) ordem FIFO (First In, First Out).

        Queue<String> fila = new LinkedList<>();
        fila.offer("Ana"); fila.offer("Bruno"); fila.offer("Carla");
        System.out.println(fila.peek()); // Ana (não remove)
        System.out.println(fila.poll()); // Ana (remove)
        System.out.println(fila); // [Bruno, Carla]

        //PriorityQueue Fila em que o elemento retirado é sempre o de maior prioridade (por padrão, o menor segundo a ordem
        //natural).

        PriorityQueue<Integer> pq = new PriorityQueue<>(); // min-heap
        pq.addAll(List.of(30, 10, 20, 5));
        System.out.println(pq.poll()); // 5
        System.out.println(pq.poll()); // 10
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        maxHeap.addAll(List.of(30, 10, 20, 5));
        System.out.println(maxHeap.poll()); // 30

       // Com objetos: tarefas por prioridade
        record Tarefa(String nome, int prioridade) {}
        PriorityQueue<Tarefa> tarefas =
                new PriorityQueue<>(Comparator.comparingInt(Tarefa::prioridade));

        // Deque e ArrayDeque

        // permite inserir e remover nas duas pontas. Pode funcionar
        //como fila (FIFO) ou como pilha (LIFO). A classe ArrayDeque usa um array circular redimensionável, não
        //aceita null e é geralmente a melhor opção para filas e pilhas.

        Deque<Integer> pilha = new ArrayDeque<>();
        pilha.push(1); pilha.push(2); pilha.push(3);
        System.out.println(pilha.pop()); // 3 (LIFO)
        System.out.println(pilha.peek()); // 2

        Deque<String> fila1 = new ArrayDeque<>();
        fila1.offerLast("A"); fila1.offerLast("B");
        System.out.println(fila1.pollFirst()); // A (FIFO)


        // exemplo: verificar parênteses balanceados

        static boolean balanceado(String s) {
            Deque<Character> p = new ArrayDeque<>();
            for (char c : s.toCharArray()) {
                if (c == '(' || c == '[' || c == '{') p.push(c);
                else if (c == ')' || c == ']' || c == '}') {
                    if (p.isEmpty()) return false;
                    char a = p.pop();
                    if ((c == ')' && a != '(') || (c == ']' && a != '[') || (c == '}' && a != '{'))
                        return false;
                }
            }
            return p.isEmpty();
        }


        // equals() e hascode()

        public class Aluno {
            private final String matricula;
            private final String nome;
            public Aluno(String matricula, String nome) {
                this.matricula = matricula;
                this.nome = nome;
            }
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Aluno outro = (Aluno) o;
                return Objects.equals(matricula, outro.matricula); // identidade = matrícula
            }
            @Override
            public int hashCode() {
                return Objects.hash(matricula);
            }
        }
        Set<Aluno> turma = new HashSet<>();
        turma.add(new Aluno("123", "Ana"));
        turma.add(new Aluno("123", "Ana Maria"));
        System.out.println(turma.size()); // 1 (considerados o mesmo aluno)


        // Comparable

        public class Produto implements Comparable<Produto> {
            private String nome;
            private double preco;
            // construtor, getters...
            @Override
            public int compareTo(Produto outro) {
                return Double.compare(this.preco, outro.preco); // ordem crescente de preço
            }
        }
        List<Produto> lista = new ArrayList<>();
        Collections.sort(lista); // usa compareTo


        //Comparator

        lista.sort(Comparator.comparing(Produto::getNome)); // por nome
        lista.sort(Comparator.comparingDouble(Produto::getPreco).reversed()); // preço decrescente
        lista.sort(Comparator.comparing(Produto::getNome)
                .thenComparingDouble(Produto::getPreco)); // nome e, em empate, preço
        List<String> nomes = new ArrayList<>(List.of("bia", "Ana", "carla"));
        nomes.sort(String.CASE_INSENSITIVE_ORDER);
        nomes.sort(Comparator.reverseOrder());
        nomes.sort(Comparator.nullsFirst(Comparator.naturalOrder()));

        // colections e arrays

        List<Integer> n = new ArrayList<>(List.of(4, 8, 1, 8, 3));
        Collections.sort(n); // [1, 3, 4, 8, 8]
        System.out.println(Collections.max(n)); // 8
        System.out.println(Collections.frequency(n, 8)); // 2
        Collections.reverse(n); // [8, 8, 4, 3, 1]
        List<Integer> fixa = Arrays.asList(1, 2, 3);
        fixa.set(0, 10); // OK
// fixa.add(4); // UnsupportedOperationException
        List<Integer> imut = List.of(1, 2, 3);
// imut.add(4); // UnsupportedOperationException


        // Qual coleção usar:
        //Lista com acesso rápido por índice ArrayList
        //Muitas inserções/remoções no início e fim ArrayDeque (ou LinkedList)
        //Elementos únicos, rápido HashSet
        //Elementos únicos, mantendo ordem de inserção LinkedHashSet
        //Elementos únicos e ordenados TreeSet
        //Dicionário chave/valor, rápido HashMap
        //Dicionário com ordem de inserção / cache LRU LinkedHashMap
        //Dicionário ordenado pelas chaves TreeMap
        //Fila FIFO ArrayDeque / LinkedList (Queue)
        //Pilha LIFO ArrayDeque (Deque)
        //Sempre processar o de maior prioridade PriorityQueue

    }
}
