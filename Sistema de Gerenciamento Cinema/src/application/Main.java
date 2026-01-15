package application;

import Model.entities.Cinema;
import Model.entities.Filme;
import Model.entities.Sessao;
import Model.entities.SessaoKey;
import Model.enums.TipoIngresso;
import Model.utils.InputUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);

        DateTimeFormatter dtm =  DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


        //  Sessões de teste
        Filme f1 = new Filme("Duna: Parte 2", "Ficção Científica", 165);
        Filme f2 = new Filme("Divertida Mente 2", "Animação", 100);
        Filme f3 = new Filme("Deadpool & Wolverine", "Ação", 128);


        Cinema cinema = new Cinema();

        cinema.adicionarSessao( new SessaoKey(f1, LocalDateTime.of(2026, 1, 14, 14, 0)),
                new Sessao(100, 32.00));

        cinema.adicionarSessao( new SessaoKey(f2, LocalDateTime.of(2026, 1, 14, 16, 30)),
                new Sessao(120, 28.50));

        cinema.adicionarSessao(new SessaoKey(f3, LocalDateTime.of(2026, 1, 14, 19, 0)),
                new Sessao(150, 35.00));



        int op = 0;

        while (op != 5) {
            menu();
            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Digite um número válido (1 a 5).");
                continue;
            }



            switch (op){
                case 1:

                    while (true){

                        try {
                            String nomeFilme = InputUtils.lerTextoValido(sc,"Digite o nome do filme: ");

                            String genero = InputUtils.lerTextoValido(sc, "Gênero: ");

                            int duracao = InputUtils.lerInteiroPositivo(sc, "Duração (min): ");

                            Filme filme = new Filme(nomeFilme, genero, duracao);

                            System.out.println("***** Sessão *****");
                            int capacidade = InputUtils.lerInteiroPositivo(sc, "Capacidade: ");

                            double preco = InputUtils.lerDoublePositivo(sc, "Preço do ingresso: ");

                            System.out.println("Horário da sessão e dia da sessão (dd/MM/yyyy HH:mm): ");
                            LocalDateTime dataHorario = InputUtils.lerDataHora(sc, "Horário da sessão e dia da sessão " +
                                    "(dd/MM/yyyy HH:mm): ", dtm);


                            Sessao sessao = new Sessao(capacidade, preco);

                            SessaoKey sessaoKey = new SessaoKey(filme, dataHorario);

                            cinema.adicionarSessao(sessaoKey, sessao);

                        } catch (InputMismatchException e) {
                            System.out.println("⚠️ Erro valor inválido, tente novamente!");
                            sc.nextLine();
                            continue;
                        } catch (Exception e) {
                            System.out.println("⛔ Erro: " + e.getMessage());
                            continue;
                        }

                        System.out.print("Deseja cadastrar outra sessão? (s/n): ");
                        String continuar = sc.nextLine().trim().toLowerCase();

                        if (!continuar.equals("s")) {
                            break;
                        }
                    }

                    break;
                case 2:

                    cinema.Listarsessoes();

                    break;

                case 3:

                    System.out.println("***** Vender Ingresso *****");
                    System.out.println("Escolha a sessão: ");
                    Sessao sessaoSelecionada = null;

                    cinema.Listarsessoes();

                    while (true){
                        int escolha = InputUtils.lerInteiroPositivo(sc, "Digite o número da sessão: ");

                        if(escolha > cinema.getListaIdsessoes().size()){
                            System.out.println("Opção inválida! escolha novamente: ");
                            continue;
                        }

                        SessaoKey chaveEscolhida = cinema.getListaIdsessoes().get(escolha - 1);
                        sessaoSelecionada = cinema.getSessoes().get(chaveEscolhida);

                        System.out.println("Você escolheu a sessão: " + chaveEscolhida.getFilme() + "\n" + "Horário: " + chaveEscolhida.getHorario());
                        break;
                    }

                    TipoIngresso tipoIngresso = null;

                    while (true){
                        System.out.print("Tipo de ingresso (1 - Inteira, 2 - Meia): ");
                        int tipo = sc.nextInt();
                        sc.nextLine();


                        if(tipo == 1){
                            tipoIngresso = TipoIngresso.INTEIRA;
                        } else if (tipo == 2) {
                            tipoIngresso = TipoIngresso.MEIA;
                        } else {
                            System.out.println("Opção inválida! tente novamente: ");
                            continue;
                        }
                        break;
                    }



                    sessaoSelecionada.venderIngresso(tipoIngresso);



                    break;

                case 4:

                    System.out.println("A arrecadação geral de todas as sessões foi de:");
                    System.out.println("R$ " + cinema.arrecadacaoTotalSessoes());

                    break;

                case 5:

                    System.out.println("Saindo...");

                    break;

                default:

                    System.out.println("⛔ Opção inválida");

                    break;


            }

        }


    }

    public static void menu(){
        System.out.println("=== Sistema de Cinema ===");
        System.out.println("1. Cadastrar filme e sessão");
        System.out.println("2. Listar sessões");
        System.out.println("3. Vender ingresso");
        System.out.println("4. Mostrar arrecadação total");
        System.out.println("5. Sair");
    }

}
