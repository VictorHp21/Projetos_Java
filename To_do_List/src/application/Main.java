package application;

import Model.entities.Tarefa;

import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        File file = new File("C:\\Users\\victo\\OneDrive\\Desktop\\PROJETOS\\Projetos Java\\To_do_List\\tarefas.txt");

        Scanner scanner = new Scanner(System.in);

        String line = "LISTA DE TAREFAS";
        boolean cabecalho = false;

        if(file.exists()){
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String l;
                while ((l = br.readLine()) != null) {
                    if (l.trim().equals(line)) {
                        cabecalho = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!cabecalho){
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                bw.write(line);
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Tarefa> tarefas = new ArrayList<>();

        loop:
        while (true) {
            menu();
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("\n==== Adicionar tarefa ====");
                    System.out.println("Nome: ");
                    String nome = scanner.next();

                    scanner.nextLine();

                    System.out.println("Descrição: ");
                    String descricao = scanner.nextLine();;



                    System.out.println("Data a ser feita: ");
                    String dataString = scanner.next();

                    LocalDate localDate = LocalDate.parse(dataString, formatter);
                    Date data = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());


                    System.out.println("Prioridade: ");
                    String prioridade = scanner.next();

                    System.out.println("Qual o status da tarefa? ");
                    System.out.println("1. ⛔Pendente");
                    System.out.println("2. ⚠️Em andamento");
                    System.out.println("3. ✅Concluída");
                    int statusOpcao = scanner.nextInt();

                    String status = "";

                    if (statusOpcao == 1) {
                        status = "⛔Pendente";
                    } else if (statusOpcao == 2) {
                        status = "⚠️Em andamento";
                    } else if (statusOpcao == 3) {
                        status = "✅Concluída";
                    }

                    Tarefa tarefa = new Tarefa(nome, descricao, data, prioridade, status);
                    tarefas.add(tarefa);

                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                        bw.write(nome + ";" + descricao + ";" + formatter.format(localDate) + ";" + prioridade + ";" + status);
                        bw.newLine();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    break;


                case 2:

                    System.out.println("\n==== Lista de tarefas ====");

                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String lines;
                        while ((lines = br.readLine()) != null) {
                            System.out.println(lines);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;


                case 3:

                    scanner.nextLine();

                    System.out.println("\n==== Remover tarefa ====");
                    // colocar um list de tarefas para o usuario ver a partir do arquivo de texto
                    System.out.println("Digite o nome da tarefa que deseja remover? ");
                    String nomeTarefa = scanner.nextLine();


                    if (nomeTarefa.isBlank()) {
                        System.out.println("Tarefa não encontrada");
                        break;
                    }
                    boolean removido = tarefas.removeIf(t -> t.getNome().equals(nomeTarefa));

                    if (!removido) {
                        System.out.println("Tarefa não encontrada");
                        break;
                    }

                    List<String> linhas = new ArrayList<>();

                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String newLine;
                        while ((newLine = br.readLine()) != null) {

                            if (!newLine.startsWith(nomeTarefa + ";")) {
                                linhas.add(newLine);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // regravar o arquivo sem a tarefa removida


                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

                        for (String l : linhas) {
                            bw.write(l);
                            bw.newLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    break;

                case 4:
                    break loop;

            }




        }
    }

    public static void menu() {
        System.out.println("\n***** Menu *****");
        System.out.println("1. Adicionar tarefa");
        System.out.println("2. Listar tarefas");
        System.out.println("3. Remover tarefa");
        System.out.println("4. Sair");
        System.out.print("Escolha uma opção: ");
    }


}



