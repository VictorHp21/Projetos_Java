import java.util.*;

public class Main {
    public static void main(String[] args){


        // exs List

        // ex 1

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


        // ex 2

        System.out.println("******** ex 2 ********");

        List<Integer> valores = new ArrayList<>(
                List.of(1,2,3,5,6,3,2,4,8,9,8,4,1,3)
        );

        System.out.println("IndexOf valor 3: " + valores.indexOf(3));

        System.out.println("LastIndexOF valor 3: " + valores.lastIndexOf(3));

        List<Integer> sublistValores = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            sublistValores.add(valores.get(i));
        }

        sublistValores.forEach(System.out::println);


        // ex 03

        List<Integer> valores2 = List.of(1,2,3,4,5); // lista imutável

        List<Integer> valores3 = Arrays.asList(1,2,3,4,5,6);

       // valores2.add(6); // imutableCollections
      //  valores3.add(7); // UnsuportedOperationException


        // Arraylist

        System.out.println("-------------- ARRAYLIST ----------");

        // ex 1

        List<Integer> notas = new ArrayList<>();
        
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int valor = random.nextInt(10);
            notas.add(valor);
        }

        notas.forEach(System.out::println);

        Double media = 0.0;

        int total = 0;

        int maior = 0;

        for(Integer n: notas){

            if(n > maior){
                maior = n;
            }

            total+= n;
        }

        media = (double) (total / notas.size());

        int menor = 10;

        for(Integer n: notas) {
            if (n < menor) {
                menor = n;

            }

        }


        System.out.println("Média das notas: " + media);

        System.out.println("Maior nota: " + maior);

        System.out.println("Menor nota: " + menor);


        // ex 2


        List<Integer> nums2 = new ArrayList<>(
                List.of(1,2,3,4,5,6,7,8,9)
        );

        List<Integer> pares = new ArrayList<>();

        List<Integer> acimaDaMedia = new ArrayList<>();

        media = calculaMedia(nums2);

        for(Integer n: nums2){

            if(n % 2 == 0){
                pares.add(n);
            }

            if(n > media){
                acimaDaMedia.add(n);
            }

        }


        System.out.println("Lista dos pares: " );

        pares.forEach(System.out::println);

        System.out.println("Lista dos acima da media: ");

        acimaDaMedia.forEach(System.out::println);

        //LinkedList

        System.out.println("***** LINKEDLIST *****");

        LinkedList<String> nomes2 = new LinkedList<>();

        nomes2.add("Bruno");
        nomes2.add("Carlos");
        nomes2.add("Mariana");
        nomes2.add("Victor");

        nomes2.addFirst("Ana");

        nomes2.addLast("Walter");

        nomes2.removeFirst();

        nomes2.removeLast();



        nomes2.forEach(System.out::println);

        System.out.println("-------------");

        System.out.println(nomes2.getFirst());


        // ex 2

        LinkedList<String> historico = new LinkedList<>();

        int indiceAtual = -1;

        // Visitando páginas
        historico.add("Google");
        indiceAtual++;

        historico.add("YouTube");
        indiceAtual++;

        historico.add("GitHub");
        indiceAtual++;

        historico.add("Stack Overflow");
        indiceAtual++;

        System.out.println("Página atual: " + historico.get(indiceAtual));

        // VOLTAR
        if (indiceAtual > 0) {
            indiceAtual--;
        }

        System.out.println("Voltou para: " + historico.get(indiceAtual));

        // VOLTAR NOVAMENTE
        if (indiceAtual > 0) {
            indiceAtual--;
        }

        System.out.println("Voltou para: " + historico.get(indiceAtual));

        // AVANÇAR
        if (indiceAtual < historico.size() - 1) {
            indiceAtual++;
        }

        System.out.println("Avançou para: " + historico.get(indiceAtual));


        // ex 3

        ArrayList<Integer> arrayList = new ArrayList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();

        // Teste ArrayList
        long inicioArray = System.nanoTime();

        for (int i = 0; i < 100000; i++) {
            arrayList.add(0, i);
        }

        long fimArray = System.nanoTime();

        // Teste LinkedList
        long inicioLinked = System.nanoTime();

        for (int i = 0; i < 100000; i++) {
            linkedList.addFirst(i);
        }

        long fimLinked = System.nanoTime();

        // Calculando o tempo
        long tempoArray = fimArray - inicioArray;
        long tempoLinked = fimLinked - inicioLinked;

        System.out.println("ArrayList: " + tempoArray / 1_000_000.0 + " ms");

        System.out.println("LinkedList: " + tempoLinked / 1_000_000.0 + " ms");


    }

    public static Double calculaMedia(List<Integer> lista){
        Double total = 0.0;
        Double media = 0.0;

        for(Integer n: lista){

            total+= n;

        }

        media = total / lista.size();

        return media;
    }


    // LinkedList



}
