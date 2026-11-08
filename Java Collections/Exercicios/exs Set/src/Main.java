import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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

        System.out.println("----------------------------");

        List<String> palavras = new ArrayList<>(
                List.of("carro", "vento", "carro", "prédio")
        );

        if(!temDuplicados(palavras)){
            System.out.println("Não tem itens duplicados na lista");
        } else {
            System.out.println("Há pelo menos um ou mais itens duplicados na lista");

            System.out.println("Primeiro elemento duplicado da lista: " + primeiroElementoQueSeRepete(palavras));
        }



        // HashSet

        //ex 01

        String frase = "Não importa o que você tem, mas sim o que você faz com aquilo que tem";

        Set<String> palavrasUnicas = new HashSet<>(
                Arrays.asList(
                        frase.toLowerCase()
                                .replaceAll("[^\\p{L}\\s]", "")
                                .trim()
                                .split("\\s+")
                )
        );

        System.out.println(palavrasUnicas);

        System.out.println("Quantidade palavras únicas: " + palavrasUnicas.size());



        // ex 2

        System.out.println("------------");

        Set<Aluno> alunos = new HashSet<>();

        alunos.add(new Aluno("Victor", 101));
        alunos.add(new Aluno("Maria", 102));
        alunos.add(new Aluno("João", 103));


        alunos.add(new Aluno("Pedro", 101));
        alunos.add(new Aluno("Hector", 102));
        // não adiciona graças ao hashcode e equals para matricula
        // assim n aceitando uma matricula duplicada

        // teste com nome (criando hashcode e equals na classe com nome agora)

        alunos.add(new Aluno("Victor", 110));

        System.out.println(alunos);

        // cria duplicatas pois: ao comparar  dois atributos, nenhum desses objetos é considerado igual a outro.

        // no contexto de classe o set vai considerar duplicados com base no hashcode e equals

    }

    public static boolean temDuplicados(List<String> lista){

        Set<String> setList = new HashSet<>();
        String p = "";

        boolean duplicados = false;

        for (int i = 0; i < lista.size(); i++) {

            p = lista.get(i);

            if(!setList.add(p)){
                duplicados = true;
                break;
            }
        }

        return duplicados;
    }

    public static String primeiroElementoQueSeRepete(List<String> lista){

        Set<String> setList = new HashSet<>();
        String p = "";
        String primeiroDuplicado = "";


        for (int i = 0; i < lista.size(); i++) {

            p = lista.get(i);

            if(!setList.add(p)){
                 primeiroDuplicado = p;
                break;
            }
        }

        return primeiroDuplicado;

    }

    }

