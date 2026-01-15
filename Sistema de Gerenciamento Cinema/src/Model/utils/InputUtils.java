package Model.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUtils {

    public static String lerTextoValido(Scanner sc, String mensagem) {
        String texto;
        while (true) {
            System.out.println(mensagem);
            texto = sc.nextLine().trim();

            if (texto.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                return texto;
            } else {
                System.out.println("⚠️ Entrada inválida! Digite apenas letras e espaços.");
            }
        }
    }


    public static int lerInteiroPositivo(Scanner sc, String mensagem) {
        int valor = 0;
        while (true) {
            try {
                System.out.println(mensagem);
                valor = sc.nextInt();
                sc.nextLine();

                if (valor > 0) {
                    return valor;
                } else {
                    System.out.println("⚠️ O valor deve ser maior que 0!");
                }
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Digite um número inteiro válido!");
                sc.nextLine();
            }
        }
    }


    public static double lerDoublePositivo(Scanner sc, String mensagem) {
        double valor = 0;
        while (true) {
            try {
                System.out.println(mensagem);
                valor = sc.nextDouble();
                sc.nextLine();

                if (valor > 0) {
                    return valor;
                } else {
                    System.out.println("⚠️ O valor deve ser positivo!");
                }
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Digite um número válido (use ponto para casas decimais)!");
                sc.nextLine();
            }
        }
    }

    public static LocalDateTime lerDataHora(Scanner sc, String mensagem, DateTimeFormatter dtf) {
        while (true) {
            try {
                System.out.println(mensagem);
                String dataTexto = sc.nextLine().trim();
                return LocalDateTime.parse(dataTexto, dtf);
            } catch (Exception e) {
                System.out.println("⚠️ Data/hora inválida! Use o formato dd/MM/yyyy HH:mm.");
            }
        }
    }
}
