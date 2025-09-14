package application;

import Model.entities.ConversorMoedas;
import Model.entities.ConversorPeso;
import Model.entities.ConversorTemperatura;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);



        int op = 0;

        loop:
        while (true) {
            menu();
            System.out.println("\nDigite a opção desejada: ");
            op = sc.nextInt();

            switch (op) {
                case 1:
                    System.out.println("\nConversorMoedas de Moedas");
                    menuMoedas();
                    System.out.println("\nDigite a opção desejada: ");
                    int op2 = sc.nextInt();

                    double valor = 0, cotacao = 0;

                    switch (op2){
                        case 1:
                            System.out.println("\nDigite o valor em Real: ");
                             valor = sc.nextDouble();
                             cotacao = 0.1870;

                            break;

                        case 2:
                            System.out.println("\nDigite o valor em Dolar: ");
                            valor = sc.nextDouble();
                            cotacao = 5.36;

                            break;

                        case 3:
                            System.out.println("\nDigite o valor em Real: ");
                            valor = sc.nextDouble();
                            cotacao = 0.1592;

                            break;

                        case 4:
                            System.out.println("\nDigite o valor em Euro: ");
                            valor = sc.nextDouble();
                            cotacao = 6.28;

                            break;

                        case 5:
                            break;

                        default:
                            System.out.println("Opção inválida!");
                            break;


                    }

                    ConversorMoedas conversor = new ConversorMoedas(valor, cotacao);
                    System.out.println("\nO valor convertido é: " +  conversor.NomeMoeda() + " " + conversor.converter());

                    break;

                case 2:
                    System.out.println("Qual conversão deseja realizar?");
                    System.out.println("1 - Celsius para Fahrenheit");
                    System.out.println("2 - Fahrenheit para Celsius");
                    int op3 = sc.nextInt();

                    double temp = 0.0;

                    if (op3 == 1){
                        System.out.println("\nDigite o valor em Celsius: ");
                        temp = sc.nextDouble();

                        ConversorTemperatura conversorTemp = new ConversorTemperatura(temp);
                        System.out.println("\nO valor convertido é: " + conversorTemp.converterParaFahrenheit() + " Fahrenheit\n");

                    } else if (op3 == 2){
                        System.out.println("\nDigite o valor em Fahrenheit: ");
                        temp = sc.nextDouble();

                        ConversorTemperatura conversorTemp = new ConversorTemperatura(temp);
                        System.out.println("\nO valor convertido é: " + conversorTemp.converterParaCelsius() + " Graus Celsius\n");
                    } else {
                        System.out.println("Opção inválida!");
                        break;
                    }




                    break;

                case 3:

                    System.out.println("Qual conversão deseja realizar?");
                    System.out.println("1 - Quilograma para Grama");
                    System.out.println("2 - Grama para Quilograma");
                    System.out.println("3 - Grama para Tonelada");
                    System.out.println("4 - Voltar");
                    int op4 = sc.nextInt();

                    System.out.println("Digite o valor para converter: ");
                    double peso = sc.nextDouble();

                    ConversorPeso conversorPeso = new ConversorPeso(peso);


                    if (op4 == 1){
                        System.out.println("\nO valor convertido é: " + conversorPeso.converterParaGramas());

                    } else if (op4 == 2) {
                        System.out.println("\nO valor convertido é: " + conversorPeso.converterParaQuilogramas());

                    } else if (op4 == 3) {
                        System.out.println("\nO valor convertido é: " + conversorPeso.converterParaToneladas());

                    } else {
                        System.out.println("Opção inválida!");
                        break;
                    }


                    break;



                case 4:
                    System.out.println("\nSaindo...");
                    sc.close();
                    break loop;

                default:
                    System.out.println("Opção inválida!");
                    break;





            }

        }

        System.out.println("\nFim do programa");



    }

    public static void menu(){
        System.out.println("=== Menu ===");
        System.out.println("1 - Conversor de Moedas");
        System.out.println("2 - Conversor de Temperatura");
        System.out.println("3 - Conversor de Peso");
        System.out.println("4 - Sair");
    }

    public static void menuMoedas(){
        System.out.println("Qual conversão deseja realizar?");
        System.out.println("1 - Real para Dolar");
        System.out.println("2 - Dolar para Real");
        System.out.println("3 - Real para Euro");
        System.out.println("4 - Euro para Real");
        System.out.println("5 - Voltar");

    }
}
