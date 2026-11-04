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

    }
}
