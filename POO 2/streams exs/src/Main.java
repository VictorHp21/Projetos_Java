import Entities.Pessoa;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Main {
    public static void main (String[] args){


        List<Integer> nums = List.of(1, 3, 4, 5, 8, 21, 31 ,8 ,2, 10, 15, 20);

        int maior = nums.stream()
                .max(Integer::compareTo)
                .orElse(0);

        //System.out.println(maior);

        int menor = nums.stream()
                .min(Integer::compareTo)
                .orElse(0);

        //System.out.println(menor);

        Set<Integer> paresOrdenados = nums.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toCollection(TreeSet::new));


        //System.out.println(paresOrdenados);

        Set<Integer> imparesOrdenados = nums.stream()
                .filter(n -> n %2 != 0)
                .collect(Collectors.toCollection(TreeSet::new));


       // System.out.println("Impares: " + imparesOrdenados);

        List<Integer> maiores10 = nums.stream()
                .filter(n -> n > 10)
                .toList();

       // System.out.println(maiores10);


        long numPares = nums.stream()
                .filter(n -> n % 2 == 0)
                .distinct() // pra não contar os elementos que se repetem
                .count();

        //System.out.println(numPares);


       // nums.stream()
                //.forEach(n -> System.out.println(n));



        Set<Integer> numsOrdemCrescente = nums.stream()
                .collect(Collectors.toCollection(TreeSet::new));

       // System.out.println(numsOrdemCrescente);

        List<Integer> decrescente = nums.stream()
                .sorted(Comparator.reverseOrder())
                .toList();

       // System.out.println(decrescente);




      //  ****************** NÍVEL 2 ********************

        List<Integer> nums2 = List.of(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        );

        //11

        List<Integer> dobrarNums = nums2.stream()
                .map(n -> n * 2)
                .toList();

       // System.out.println(dobrarNums);


        //12


        List<Integer> elevar4nums = nums2.stream()
                .map(n -> (int) Math.pow(n, 2))
                .toList();

        //System.out.println(elevar4nums);

        // 13

        List<Integer> dobrParesNums = nums2.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * 2)
                .toList();

        //System.out.println(dobrParesNums);

        //14

        List<String> numsToString = nums2.stream()
                .map(String::valueOf)
                        .toList();

        //System.out.println(numsToString);

        // 15

        List<Integer> numsMaiorQ5 = nums2.stream()
                .filter(n -> n > 5)
                .toList();

        //System.out.println(numsMaiorQ5);

        // 16

        int soma = nums2.stream()
                .mapToInt( n -> n )
                .sum();

        //System.out.println(soma);

        // 17

        Double media = nums2.stream()
                .mapToInt(n -> n)
                .average()
                .orElse(0);

        //System.out.println(media);

        // 18

        int numM7 = nums2.stream()
                .filter(n -> n > 7)
                .findFirst()
                .orElse(0);

        //System.out.println(numM7);

        // 19

        Boolean M20 = nums2.stream()
                .anyMatch(n -> n > 20);

        if(!M20){
            System.out.println("Não nenhum numéro nesta lista maior que 20");
        } else {
            System.out.println("Há um ou mais numeros nesta lista maiores que 20");
        }


        // 20

        Boolean positivos = nums2.stream()
                .allMatch(n -> n > 0);

        if (positivos){
            System.out.println("Números da lista são valores posiivos");
        }

        //  ****************** NÍVEL 3 ********************

        // Com objetos agora

        List<Pessoa> pessoas = List.of(
                new Pessoa("João", 20, "BH"),
                new Pessoa("Maria", 17, "SP"),
                new Pessoa("Carlos", 30, "BH"),
                new Pessoa("Ana", 25, "RJ"),
                new Pessoa("Pedro", 16, "SP"),
                new Pessoa("Juliana", 40, "BH")
        );


        // 21

        List<String> pessoasMais18 = pessoas.stream()
                .filter(p -> p.getIdade() > 18)
                .map(p -> p.getNome())
                .toList();

       // System.out.println("Pessoas com mais de 18 anos: " + pessoasMais18);


        // 22

        List<String> pessoasBH = pessoas.stream()
                .filter(p -> p.getCidade().equals("BH"))
                .map(p -> p.getNome())
                .toList();

        //System.out.println("Pessoas de BH: " + pessoasBH);


        // 23 ordenar da mais jovem para a mais velha
        Set<Pessoa> pessoasOrdemCrescente = pessoas.stream()
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>( Comparator.comparingInt(Pessoa::getIdade)
                ) ));

        System.out.println("\\\\");

        for (Pessoa p: pessoasOrdemCrescente){
            //System.out.println(p.getNome() + " " + p.getIdade() + " Anos");
        }

        // 24 Ordenar por nome

        Set<Pessoa> pessoasOrdemAlfabetica = pessoas.stream()
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>( Comparator.comparing(Pessoa::getNome)
                        ) ));

        System.out.println("Ordem alfabetica: ");

       // pessoasOrdemAlfabetica.forEach(p -> System.out.println(p.getNome() + " "));


        // 25 Pessoa mais velha

        Pessoa pessoaMaisVelha = pessoas.stream()
                .max(Comparator.comparing(p -> p.getIdade()))
                .get();

        System.out.println("Pessoa mais velha: " + pessoaMaisVelha.getNome() + " " + pessoaMaisVelha.getIdade() + " Anos");


        // 26 Pessoa mais nova

        Pessoa pessoaMaisNova = pessoas.stream()
                .min(Comparator.comparing(p -> p.getIdade()))
                .get();

        //System.out.println("Pessoa mais nova: " + pessoaMaisNova.getNome() + " " + pessoaMaisNova.getIdade() + " Anos");


        // 27 média idades

        Double mediaId = pessoas.stream()
                .mapToInt(p -> p.getIdade())
                .average()
                .getAsDouble();

        System.out.println("Média das idades: " + mediaId);

        // 28 alguém com mais de 35

        Boolean pessoa35 = pessoas.stream()
                .anyMatch(p -> p.getIdade() == 35);

        if(!pessoa35){
            System.out.println("Nenhuma pessoa na lista com 35 anos");
        } else {
            System.out.println("Há uma pessoa com 35 anos na lista");
        }

        // 29

        Boolean maior18 = pessoas.stream()
                .allMatch(p -> p.getIdade() >= 18);

        if(!maior18){
            System.out.println("Nem todos na lista são maiores de idade");
        }

        System.out.println("-------------------------groupingBy e partitioningBy------------------------------");

        // 30 Agrupar pessoas por cidade

        Map<String, List<Pessoa>> resultado = pessoas.stream()
                .collect(Collectors.groupingBy(Pessoa::getCidade));

        resultado.forEach((cidade, listpessoas) ->
        {
            System.out.print(cidade + " -> ");

            listpessoas.forEach(p -> System.out.print(p.getNome() + ", "));

            System.out.println();
        });


        System.out.println("***** Collectors com counting *****");

        // 31 Contar pessoas por cidade


        Map<String, Long> qtdcidade = pessoas.stream()
                .collect(Collectors.groupingBy(
                        Pessoa::getCidade,
                        Collectors.counting()));

        qtdcidade.forEach(
                (cidade, quantidade) -> System.out.println(cidade + " -> " + quantidade + " pessoas")
    );


        System.out.println("****** PartitionBy ******");


        // 32 Maiores e menores de idade

        Map<Boolean, List<Pessoa>> resultadoIdade = pessoas.stream()
                .collect(Collectors.partitioningBy(p -> p.getIdade() >= 18));

        System.out.println("False: menores de idade\nTrue: Maiores de idade");

        resultadoIdade.forEach(
                (fv, listpessoa) -> {
                    System.out.println(fv + " {");

                    listpessoa.forEach(p -> System.out.print(p.getNome() + ", "));

                    System.out.println(" }");
                }
        );


        // 33 Agrupar por faixa etária

        Map<String, List<Pessoa>> pessoasPorFaixa = pessoas.stream()
                .collect(Collectors.groupingBy(p -> {
                    if (p.getIdade() < 18) {
                        return "Menores de idade";
                    } else if (p.getIdade() < 60) {
                        return "Adultos";
                    } else {
                        return "Idosos";
                    }
                }));


        // 34 Pessoa mais velha por cidade

        Map<String, Optional<Pessoa>> maisVelhaPorCidade = pessoas.stream()
                .collect(Collectors.groupingBy(
                        Pessoa::getCidade,
                        Collectors.maxBy(Comparator.comparing(Pessoa::getIdade))
                ));

    }
}
