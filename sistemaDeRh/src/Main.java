import Entities.*;
import Enums.Cargo;
import Enums.Departamento;
import Excecoes.SalarioInvalidoException;
import Excecoes.cpfInvalidoException;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;


// coisas a serem feitas para terminar: colocar depois do DO um try-cath(tratar exceções possíveis sem que o programa pare)
// colocar modo de editar um sálario e depois adicionalo no arquivo de texto na frente do funcionario o qual foi alterado o mesmo(sálario)
// testar tudo




public class Main {
    public static void main (String[] args) throws IOException {

        Scanner s = new Scanner(System.in);

        List<Empresa> empresas = new ArrayList<>();

        List<Funcionario> funcionarioList = new ArrayList<>();

        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // para busca de empresa

        Empresa empBuscaSistema = null;

        File pasta = new File("pasta_funcionários");

        if(!pasta.exists()){
            pasta.mkdir();
            System.out.println("Pasta dos funcionários criada com sucesso\n");
        } else {
            System.out.println("Pasta existe!!!");
        }



        File arquivoFuncionarios = new File("pasta_funcionários/funcionarios.txt");


        carregarFuncionarios(funcionarioList, arquivoFuncionarios);


        int op = 0;

        do{
            menu();
            op = s.nextInt();

            switch (op){
                case 1:

                    System.out.println("Digite o nome do funcionário: ");
                    String nome = s.nextLine();

                    System.out.println("Digite a idade do funcionário: ");
                    Integer idade = s.nextInt();

                    System.out.println("Digite o cpf (11 caracteres): ");
                    String cpf = s.nextLine();

                    if (!cpf.matches("\\d+")) {
                        throw new cpfInvalidoException("CPF deve conter apenas números.");
                    } else if (cpf.length() < 11) {
                        throw new cpfInvalidoException("CPF inválido, digite um cpf com 11 caracteres.");
                    } else if (buscarFuncionarioNoArquivoPorCpf(arquivoFuncionarios, cpf)) {
                        throw new cpfInvalidoException("CPF já cadastrado");
                    }

                    System.out.println("Digite o sálario: ");
                    Double salario = s.nextDouble();

                    if(salario <= 0 ){
                        throw new SalarioInvalidoException("Sálario não pode ser negativo ou igual a zero");
                    } else if (salario < 1612.00) {
                        throw new SalarioInvalidoException("Sálario não pode ser menor que o sálario minímo");
                    }

                    menuCargos();
                    int opCargo = s.nextInt();

                    Cargo cargo = null;


                    // switch para escolha do cargo

                    switch (opCargo){

                        case 1:
                             cargo = Cargo.VENDEDOR;

                            break;

                        case 2:
                             cargo = Cargo.CAIXA;

                            break;

                        case 3:
                            cargo = Cargo.GERENTE;

                            break;

                        default:
                            throw new RuntimeException("Escolha uma opção válida!");

                            break;

                    }

                    // switch para escolha do departamento

                    menuDepartamento();
                    int opDep = s.nextInt();

                    Departamento departamento = null;

                    switch (opDep){

                        case 1:
                            departamento = Departamento.VENDAS;

                            break;

                        case 2:
                            departamento = Departamento.CAIXAS;

                            break;

                        case 3:
                            departamento = Departamento.ADMINISTRACAO;

                            break;

                        default:
                            throw new RuntimeException("Escolha uma opção válida!");

                            break;

                    }

                    System.out.println("Digite a data da contratação (dia/mes/ano): ");
                    String dataEntrda = s.nextLine();

                    LocalDate dataContratacao = null;

                    try {
                         dataContratacao = LocalDate.parse(dataEntrda, formatoData);

                        System.out.println("Data registrada: " + dataContratacao.format(formatoData));


                    } catch (DateTimeParseException e){
                        System.out.println("Data inválida! use o formato: (dia/mes/ano)");
                    }

                    Empresa empresaEscolhida = null;

                    // colocar um metodo para buscar uma empresa por nome e depois cadastrar o funcionario neste empresa para so depois editar o status do mesmo nesta empresa

                    System.out.println("Este funcionário ainda esta na empresa " + empresaEscolhida + "? \n" +
                            "1 - Sim\n" +
                            "2 - Não");
                    int statusFunc = s.nextInt();

                    Boolean statusContratacao = null;

                    if(statusFunc == 1){
                        statusContratacao = true;
                    } else if (statusFunc == 2) {
                        statusContratacao = false;
                    }


                    // ADICIONAR LÓGICA PARA SALVAR FUNCIONÁRIO NA CLASSE CERTA(CLASSES FILHAS) A DEPENDER DO ENUM


                    
                    if(cargo == Cargo.VENDEDOR){
                        Vendedor f = new Vendedor((long) IdGenerator.getNextId(), idade, cpf, cargo, nome, salario,
                                departamento, dataContratacao, statusContratacao);

                        funcionarioList.add(f);

                        System.out.println("Vendedor cadastrado com sucesso");
                    } else if (cargo == Cargo.CAIXA) {
                        Caixa f = new Caixa((long) IdGenerator.getNextId(), idade, cpf, cargo, nome, salario,
                                departamento, dataContratacao, statusContratacao);

                        funcionarioList.add(f);

                        System.out.println("Caixa cadastrado com sucesso");
                    } else {
                        Gerente f = new Gerente((long) IdGenerator.getNextId(), idade, cpf, cargo, nome, salario,
                                departamento, dataContratacao, statusContratacao);

                        funcionarioList.add(f);

                        System.out.println("Gerente cadastrado com sucesso");
                    }





                    break;

                case 2:

                    System.out.println("Escolha uma opção abaixo: ");

                    System.out.println("1 - Funcionários salvos temporariamente");

                    System.out.println("2 - Salvos no arquivo de texto");

                    int opFuncionario = s.nextInt();

                    if(opFuncionario == 1){
                        listarFuncionários(funcionarioList);
                    } else if (opFuncionario == 2) {
                        leitorArquivo(arquivoFuncionarios);
                    }

                    break;

                case 3:

                   salvarFuncionariosNoArquivo(funcionarioList, arquivoFuncionarios);



                    break;

                case 4:

                    System.out.println("Escolha de qual das empresas abaixo deseja buscar um funcionário: ");

                    System.out.println("**** Empresas cadastradas ****\n");

                    System.out.println(listarEmpresasCadastradas(empresas));

                    System.out.println("Digite os  digitos da empresa escolhida");
                    String cnpjEmp = s.nextLine();



                    empBuscaSistema = buscarEmpresa(empresas, cnpjEmp);

                    System.out.println("Digite o nome do funcionário que deseja buscar na empresa: " + empBuscaSistema.getNome());
                    String nomeBusca = s.nextLine();

                    empBuscaSistema.buscarFuncionarioPorNome(nomeBusca);



                    break;


                case 5:

                    System.out.println("Escolha de qual das empresas abaixo deseja remover um funcionário: ");

                    System.out.println("**** Empresas cadastradas ****\n");

                    System.out.println(listarEmpresasCadastradas(empresas));

                    System.out.println("Digite os 4 primeiros digitos da empresa escolhida");
                     cnpjEmp = s.nextLine();

                    empBuscaSistema = buscarEmpresa(empresas, cnpjEmp);

                    System.out.println("Digite o nome do funcionário que deseja remover da empresa: " + empBuscaSistema.getNome());
                     nomeBusca = s.nextLine();

                     Funcionario funcARemover = null;

                   funcARemover = empBuscaSistema.buscarFuncionarioPorNome(nomeBusca);

                   empBuscaSistema.removerFuncionario(funcARemover);


                    break;


                case 6:

                    System.out.println("Escolha de qual das empresas abaixo deseja atualizar o salário de um funcionário: ");

                    System.out.println("**** Empresas cadastradas ****\n");

                    System.out.println(listarEmpresasCadastradas(empresas));

                    System.out.println("Digite os 4 primeiros digitos da empresa escolhida");
                    cnpjEmp = s.nextLine();

                    empBuscaSistema = buscarEmpresa(empresas, cnpjEmp);

                    System.out.println("*** Empresa: " + empBuscaSistema.getNome());

                    System.out.println("Digite o nome do funcionário que deseja atualizar o salário: ");
                    nomeBusca = s.nextLine();

                    Funcionario funcAtualizarSalario = null;

                    funcAtualizarSalario = empBuscaSistema.buscarFuncionarioPorNome(nomeBusca);

                    System.out.println("Salário do funcionário" + funcAtualizarSalario.getNome() + "\n" +
                            "R$" + funcAtualizarSalario.getSalario());

                    System.out.println("Digite seu novo salário: ");
                    Double novoSalario = s.nextDouble();

                    funcAtualizarSalario.setSalario(novoSalario);

                    System.out.println("Sálario atualizado com sucesso!!\nNovo salário deste: R$" + funcAtualizarSalario.getSalario());


                    break;


                case 7:

                    System.out.println("Escolha de qual das empresas abaixo deseja atualizar o cargo de um funcionário: ");

                    System.out.println("**** Empresas cadastradas ****\n");

                    System.out.println(listarEmpresasCadastradas(empresas));

                    System.out.println("Digite o cnpj da empresa escolhida");
                    cnpjEmp = s.nextLine();

                    empBuscaSistema = buscarEmpresa(empresas, cnpjEmp);

                    System.out.println("*** Empresa: " + empBuscaSistema.getNome());

                    System.out.println("Digite o nome do funcionário que deseja atualizar o cargo: ");
                    nomeBusca = s.nextLine();

                    Funcionario funcAtualizarCargo = null;

                    funcAtualizarCargo = empBuscaSistema.buscarFuncionarioPorNome(nomeBusca);

                    System.out.println("Cargo do funcionário" + funcAtualizarCargo.getCargo());

                    System.out.println("Escolha o novo cargo: ");
                    menuCargos();
                    int cargoNovoOp = s.nextInt();

                    Cargo novoCargo = null;

                    switch (cargoNovoOp){

                        case 1:
                            novoCargo = Cargo.VENDEDOR;

                            break;

                        case 2:
                            novoCargo = Cargo.CAIXA;

                            break;

                        case 3:
                            novoCargo = Cargo.GERENTE;

                            break;

                        default:
                            throw new RuntimeException("Escolha uma opção válida!");

                            break;

                    }



                    funcAtualizarCargo.setCargo(novoCargo);

                    System.out.println("Cargo atualizado com sucesso!!\nNovo cargo deste: " + funcAtualizarCargo.getCargo());


                    break;


                case 8:

                    System.out.println("Escolha de qual das empresas abaixo deseja mostrar a folha salarial: ");

                    System.out.println("**** Empresas cadastradas ****\n");

                    System.out.println(listarEmpresasCadastradas(empresas));

                    System.out.println("Digite o cnpj da empresa escolhida");
                    cnpjEmp = s.nextLine();

                    empBuscaSistema = buscarEmpresa(empresas, cnpjEmp);

                    System.out.println("*** Empresa: " + empBuscaSistema.getNome());

                    System.out.println(empBuscaSistema.gerarRelatorioFolhaSalarial());



                    break;

                case 9:

                    carregarFuncionarios(funcionarioList, arquivoFuncionarios);

                    System.out.println("Funcionários do arquivo de texto carregados no sistema com sucesso!");

                    break;


                case 10:

                    System.out.println("Relatórios: ");

                    System.out.println("Empresas cadastradas");

                    System.out.println(listarEmpresasCadastradas(empresas));

                    System.out.println();

                    break;


                case 11:

                    System.out.println("Digite o nome da empresa: ");
                    String nomeEmpresa = s.nextLine();

                    System.out.println("Digite o cnpj da empresa: ");
                    String cnpjEmpresa = s.nextLine();



                    if (!cnpjEmpresa.matches("\\d+")) {
                        throw new cpfInvalidoException("CPF deve conter apenas números.");
                    } else if (cnpjEmpresa.length() < 14) {
                        throw new cpfInvalidoException("CNPJ inválido, digite um CNPJ com 14 caracteres.");
                    } else if (empresaEstaCadastrada(empresas, cnpjEmpresa)) {
                        throw new RuntimeException("CNPJ já cadastrado");
                    }

                    Empresa novaEmpresa = new Empresa(nomeEmpresa, cnpjEmpresa);

                    empresas.add(novaEmpresa);

                    System.out.println("Empresa cadastrada com sucesso! ");


                    break;


                case 12:

                    System.out.println("Saindo...");

                    break;

                default:

                    System.out.println("Digite uma opção válida!!");

                    break;
            }

        } while (op != 12);

    }

    // MENUS*****

    public static void menu(){
        System.out.println("******** Escolha uma das opções abaixo ********");
        System.out.println("1 - Cadastrar Funcionário");
        System.out.println("2 - Ver funcionários cadastrados");
        System.out.println("3 - Salvar funcionários no arquivo de texto");
        System.out.println("4- Buscar funcionário");
        System.out.println("5- Remover funcionário");
        System.out.println("6 - Atualizar salário");
        System.out.println("7 - Atualizar cargo");
        System.out.println("8 - Mostrar folha salarial");
        System.out.println("9 - Carregar funcionários salvos");
        System.out.println("10 - Relatórios");
        System.out.println("11 - Cadastrar empresa"); // adicionar os outros metodo de crud de empresa e demais necessários
        System.out.println("12 - Sair\n");
    }

    public static StringBuilder listarEmpresasCadastradas(List<Empresa> empresas){

        StringBuilder retornoEmpresas = new StringBuilder();

        retornoEmpresas.append("\n");

        for (Empresa emp: empresas){
           retornoEmpresas.append(emp.toString()) ;
        }

        return retornoEmpresas;
    }

    public static Empresa buscarEmpresa(List<Empresa> empresas, String cnpjEmp){

        Empresa empBusca = null;


        for(Empresa emp: empresas){
            if(emp.getCnpj().equals(cnpjEmp)){
                empBusca = emp;
            }
        }

        return empBusca;
    }

    public static boolean empresaEstaCadastrada(List<Empresa> empresas, String cnpjEmp){

        Boolean empExiste = false;


        for(Empresa emp: empresas){
            if(emp.getCnpj().equals(cnpjEmp)){
                empExiste = true;
            }
        }

        return empExiste;

    }

    public static StringBuilder relatorios(List<Empresa> empresas){

        StringBuilder relatorios = new StringBuilder();

        relatorios.append("----- Relatório completo por empresa -----");

        int countEmpresa = 1;

        for(Empresa empRelatorio: empresas){

            relatorios.append("Empresa " + countEmpresa + "\n");

            relatorios.append(empRelatorio.toString());

            relatorios.append("\nFuncionários desta empresa: \n");
            relatorios.append(empRelatorio.listarFuncionarios());

            relatorios.append("\nFolha salarial: \n");
            relatorios.append(empRelatorio.gerarRelatorioFolhaSalarial());

            countEmpresa++;
        }

        return relatorios;
    }

    public static void menuCargos(){
        System.out.println("******** Escolha uma das opções abaixo ********");
        System.out.println("1 - Vendedor");
        System.out.println("2 - Caixa");
        System.out.println("3 - Gerente");
        System.out.println("4 - Desistir do cadastro do funcionário!!!\n");

    }

    public static void menuDepartamento(){
        System.out.println("******** Escolha uma das opções abaixo ********");
        System.out.println("1 - Setor de vendas");
        System.out.println("2 - Caixas");
        System.out.println("3 - Gerência");
        System.out.println("4 - Desistir do cadastro do funcionário!!!\n");

    }

    public static void salvarFuncionariosNoArquivo(List<Funcionario>funcionarioList, File arquivoFuncionarios){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoFuncionarios, true))){
            // uso do true acima para não apagar oq ja estiver escrito no arquivo de txt

            for(Funcionario func: funcionarioList){
                String nomeF = func.getNome();
                //Double salarioF = func.getSalario();

                String cpfFunc = func.getCpf();

                if(buscarFuncionarioNoArquivoPorCpf(arquivoFuncionarios, cpfFunc)){
                    System.out.println("Funcionário: " + nomeF + " já cadastrado");
                } else {
                    bw.write(func.getId() + ";" +
                            func.getNome() + ";" +
                            func.getIdade() + ";" +
                            func.getCpf() + ";" +
                            func.getCargo() + ";" +
                            func.getDepartamento() + ";" +
                            func.getSalario() + ";" +
                            func.getDataContratacao() + ";" +
                            func.isStatusDaContratacao());
                    bw.newLine();
                }

                System.out.println("Funcionários salvos no arquivo de texto com sucesso");

            }

        } catch (IOException e){
            System.out.println("Erro ao escrever no arquivo: \n");
            System.out.println(e.getMessage());
        }
    }


    public static void listarFuncionários(List<Funcionario>funcionarioList){

        System.out.println("---- Funcionários cadastrados -----");

        int ind = 1;

        for(Funcionario f: funcionarioList){

            System.out.println("\nFuncionário " + ind);

            System.out.println("Nome: " + f.getNome());

            System.out.println("Sálario: " + f.getSalario());

            ind++;

        }

    }

    public static boolean buscarFuncionarioNoArquivoPorCpf(File arquivo, String cpf) throws IOException {



        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {

            String linha;

            while ((linha = br.readLine()) != null) {

                if (linha.equalsIgnoreCase("CPF: " + cpf)) {
                    return true;
                }
            }


        } catch (IOException e){
            System.out.println("Erro ao ler arquivo de funcionários\n");
            System.out.println(e.getMessage());
        }

        return false;
    }



    public static void leitorArquivo(File arquivo){

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))){

            String linha;


            System.out.println("Funcionários salvos no arquivo de texto");

            while((linha = br.readLine()) != null){
                System.out.println(linha);
                System.out.println("\n");

            }



        } catch (IOException e){
            System.out.println("Erro ao ler arquivo de funcionários\n");
            System.out.println(e.getMessage());
        }

    }

    // metodo para gerar id

    public class IdGenerator {

        private static final AtomicInteger counter = new AtomicInteger(0);

        public static int getNextId() {
            return counter.incrementAndGet();
        }
    }


    // metodo para carregar funcionários ao iniciar o programa

    public static void carregarFuncionarios(
            List<Funcionario> funcionarios,
            File arquivo) {

        if (!arquivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {

            String linha;

            while ((linha = br.readLine()) != null) {

                String[] dados = linha.split(";");

                Long id = Long.parseLong(dados[0]);
                String nome = dados[1];
                Integer idade = Integer.parseInt(dados[2]);
                String cpf = dados[3];
                Cargo cargo = Cargo.valueOf(dados[4]);
                Departamento departamento =
                        Departamento.valueOf(dados[5]);
                Double salario = Double.parseDouble(dados[6]);
                LocalDate dataContratacao =
                        LocalDate.parse(dados[7]);
                Boolean status =
                        Boolean.parseBoolean(dados[8]);

                Funcionario funcionario;

                if (cargo == Cargo.VENDEDOR) {

                    funcionario = new Vendedor(
                            id,
                            idade,
                            cpf,
                            cargo,
                            nome,
                            salario,
                            departamento,
                            dataContratacao,
                            status
                    );

                } else if (cargo == Cargo.CAIXA) {

                    funcionario = new Caixa(
                            id,
                            idade,
                            cpf,
                            cargo,
                            nome,
                            salario,
                            departamento,
                            dataContratacao,
                            status
                    );

                } else {

                    funcionario = new Gerente(
                            id,
                            idade,
                            cpf,
                            cargo,
                            nome,
                            salario,
                            departamento,
                            dataContratacao,
                            status
                    );
                }

                funcionarios.add(funcionario);
            }

        } catch (IOException e) {
            System.out.println("Erro ao carregar funcionários");
            System.out.println(e.getMessage());
        }
    }
}
