import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main (String[] args){

        List<Integer> nums = List.of(
                10, 5, 20, 30, 20, 40, 30
        );

        // 1 segundo maior número distinto

        int maior = nums.stream()
                .distinct() // tiro repetidos
                .sorted(Comparator.reverseOrder()) // coloco em ordem decrescente
                .skip(1) // pulo o primeiro no caso o maior
                .findFirst() // pego o proximo (ou seja o segundo maior)
                .orElse(0);

        System.out.println("Segundo maior: " + maior);

        // 2 Números que aparecem mais de uma vez

        List<Integer> repetidos = nums.stream()
                .filter(n -> Collections.frequency(nums, n) > 1)
                .distinct().toList();

        System.out.println("Números que se repetem na lista: " + repetidos);

        List<String> palavras = List.of(
                "Java",
                "Stream",
                "Programacao",
                "API",
                "Computador"
        );

        // 3 palavra com maior quantidade de caracteres.

        String maiorPalavra = palavras.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("");

        System.out.println("Palavra com maior qtd de caracteres: " + maiorPalavra);


        List<String> palavras2 = List.of(
                "java",
                "python",
                "java",
                "csharp",
                "python",
                "java"
        );

        // 4 frequência de palavras

        Map<String, Long> contagem = palavras2.stream()
                .collect(Collectors.groupingBy(
                        palavra -> palavra,
                        Collectors.counting()
                ));

        contagem.forEach((palavra, quantidade) ->
                System.out.println(palavra + " -> " + quantidade)
        );

        // 5 Primeira letra repetida
        String texto = "programacao";

        List<Character> caracteres = texto.chars()
                .mapToObj(c -> (char) c)
                .toList();

        String pLetra = caracteres.stream()
                .filter(n -> Collections.frequency(caracteres, n) > 1)
                .findFirst()
                .map(String::valueOf)
                        .orElse("");

        System.out.println("Primeira letra que se repete na palavra (PROGRAMAÇÃO): " + pLetra.toUpperCase());

        // 6 primeira letra que não se repete

        String texto2 = "swiss";

        List<Character> caract2 = texto2.chars()
                .mapToObj(c -> (char) c)
                .toList();

        String pLetra2 = caract2.stream()
                .filter(n -> Collections.frequency(caract2, n) <= 1)
                .findFirst()
                .map(String::valueOf)
                .orElse("");

        System.out.println("Primeira letra que NÃO se repete na palavra (SWISS): " + pLetra2.toUpperCase());


        // 7 Somar apenas números distintos

        List<Integer> nums3 = List.of(
                1, 2, 2, 3, 4, 4, 5
        );

        int soma = nums3.stream()
                .distinct()
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Soma dos números distintos da lista: " + soma);

        // 8 Produto dos números

        int produto = nums3.stream()
                .distinct()
                .reduce(1, (a, b) -> a * b);

        System.out.println("Produto dos números distintos da lista: " + produto);

        // 9 Encontrar o maior número sem usar max()
        //Resolva utilizando apenas reduce().

        int maiorN = nums3.stream()
                .reduce(Integer.MIN_VALUE, (a, b) -> Math.max(a, b));

        System.out.println("O maior da lista: " + maiorN);

        // 10 Transformar varias em uma unica lista

        List<List<Integer>> numeros = List.of(
                List.of(1, 2, 3),
                List.of(4, 5),
                List.of(6, 7, 8)
        );

        List<Integer> numsJuntos = numeros.stream()
                .flatMap(List::stream)
                .toList();

        System.out.println("União das listas: " + numsJuntos);

        // 11 Encontrar palavras que começam com a mesma letra

        List<String> palavrasJ = List.of(
                "Java",
                "Javascript",
                "Python",
                "PHP",
                "C++",
                "CSharp"
        );

        Map<String, List<String>> grupos = palavrasJ.stream()
                .collect(Collectors.groupingBy(w -> w.substring(0, 1)));

        List<String> palavrarCMletra = grupos.values().stream()
                .filter(grupo -> grupo.size() > 1)
                .flatMap(List::stream)
                .toList(); // bug

        System.out.println("Palavras que começam com a mesma letra: " + palavrarCMletra);



        //-------------------------------------Agora com classes-------------------------------------

        List<Produto> produtos = List.of(
                new Produto("PS5", 3500.00),
                new Produto("Nikito", 80.00),
                new Produto("Relógio", 150.00),
                new Produto("GTA IV ultimate", 900.00)
        );

        //
        List<Pedido> pedidos = List.of(
                new Pedido(1, List.of(produtos.get(0), produtos.get(1))),
                new Pedido(2, List.of(produtos.get(2), produtos.get(3))),
                new Pedido(3, List.of(produtos.get(0), produtos.get(2)))
        );


        // preço total de cada pedido

        Map<Integer, Double> totalCadaPedido = pedidos.stream()
                .collect(Collectors.toMap(
                        Pedido::getNumero, // chave
                        p -> p.getProdutos().stream()
                                .mapToDouble(Produto::getPreco)
                                .sum() // valor
                ));


        System.out.println(totalCadaPedido);


        // o pedido mais caro

        Pedido pedidoMaisCaro = pedidos.stream()
                .max(Comparator.comparingDouble(p -> p.getProdutos().stream()
                        .mapToDouble(Produto::getPreco)
                        .sum()))
                .orElse(null);


        System.out.println(pedidoMaisCaro);


        // o produto mais caro

        Produto produtoMaisCaro = produtos.stream()
                .max(Comparator.comparingDouble(p -> p.getPreco()))
                .orElse(null);


        System.out.println("Produto mais caro: " + produtoMaisCaro.getNome());

        //

        List<Produto> todosProdutos = pedidos.stream()
                .flatMap(p -> p.getProdutos().stream())
                .toList();


        double precoMedio = pedidos.stream()
                .flatMap(p -> p.getProdutos().stream())
                .mapToDouble(Produto::getPreco)
                .average()
                .orElse(0.0);

        List<Produto> produtosCaros = produtos.stream()
                .filter(p -> p.getPreco() > 100)
                .toList();


    }
}
