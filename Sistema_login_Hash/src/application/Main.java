package application;

import Model.entities.Usuario;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Cadastro ===");
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();


        Usuario user = Usuario.cadastrar(email, senha);
        System.out.println("Usuário cadastrado com sucesso!");
        System.out.println("Hash armazenado (exemplo): " + user.getSenhaHashHex());

        System.out.println("\n=== Autenticação ===");
        System.out.print("Email: ");
        String emailLogin = sc.nextLine();
        System.out.print("Senha: ");
        String senhaLogin = sc.nextLine();


        if (!user.getEmail().equals(emailLogin)) {
            System.out.println("Usuário não encontrado.");
        } else {
            boolean ok = user.autenticar(senhaLogin);
            if (ok) {
                System.out.println("Autenticado com sucesso!");
            } else {
                System.out.println("Senha incorreta.");
            }
        }

        sc.close();
    }
}

