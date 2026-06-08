package Entities;

import Excecoes.funcionarioException;

import java.util.List;

public class Empresa {
    private String nome;
    private String cnpj;
    private List<Funcionario> funcionarios;

    public Empresa(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
    }

    public void adicionarFuncionario(Funcionario funcionario){
        this.funcionarios.add(funcionario);
    }

    public void removerFuncionario(Funcionario funcionario){
        for(Funcionario f: funcionarios){
            if(f.equals(funcionario)){
                funcionarios.remove(f);
            } else {
                throw new funcionarioException("Funcionário não encontrado nesta empresa");
            }
        }
    }

    public Funcionario buscarFuncionarioPorNome(String nome){

        Funcionario funcionarioEncontrado = null;

        for(Funcionario f: funcionarios){
            if (f.getNome().equalsIgnoreCase(nome)){
                funcionarioEncontrado = f;
            } else {
                throw new funcionarioException("Funcionário " + nome + " não encontrado nesta empresa");
            }
        }
        return funcionarioEncontrado;
    }

    public Funcionario buscarFuncionarioPorCpf(String cpf){

        Funcionario funcionarioEncontrado = null;

        for(Funcionario f: funcionarios){
            if (f.getNome().equalsIgnoreCase(cpf)){
                funcionarioEncontrado = f;
            } else {
                throw new funcionarioException("Funcionário com o cpf: " + cpf + " não encontrado nesta empresa");
            }
        }
        return funcionarioEncontrado;
    }



    public StringBuilder listarFuncionarios(){
        StringBuilder funcionariosRelatorio = new StringBuilder("");

        int numFunc = 0;

        for (Funcionario f: funcionarios){
            numFunc++;
            funcionariosRelatorio.append("\n===== FUNCIONÁRIO " + numFunc +  " =====");
            funcionariosRelatorio.append(f.toString());
        }

        return funcionariosRelatorio;
    }


    public Double calcularFolhaDePagamento(){
        Double folhaDePagamento = 0.0;

        for (Funcionario f: funcionarios){
            folhaDePagamento += f.getSalario();
        }

        return folhaDePagamento;
    }

    public StringBuilder gerarRelatorioFolhaSalarial() {

        StringBuilder relatorio = new StringBuilder();

        relatorio.append("\n===== FOLHA SALARIAL =====\n");
        relatorio.append("Empresa: ").append(nome).append("\n");
        relatorio.append("CNPJ: ").append(cnpj).append("\n\n");

        double totalFolha = 0.0;

        for (Funcionario f : funcionarios) {
            relatorio.append("Funcionário: ")
                    .append(f.getNome())
                    .append(" | Salário: R$ ")
                    .append(String.format("%.2f", f.getSalario()))
                    .append("\n");

            totalFolha += f.getSalario();
        }

        relatorio.append("\n-------------------------\n");
        relatorio.append("Total da folha salarial: R$ ")
                .append(String.format("%.2f", totalFolha));

        return relatorio;
    }

    // Maior sálario

    public StringBuilder funcionarioMaiorSalario(){

        if(funcionarios.isEmpty()){
            return new StringBuilder("Nenhum funcionário cadastrado.");
        }

        Double maior = funcionarios.get(0).getSalario();
        Funcionario funcionarioMaiorSalario = funcionarios.get(0);

        StringBuilder relatorioMaiorSalarioFuncionario = new StringBuilder("");

        for (Funcionario f: funcionarios){


            if(f.getSalario() > maior){
                maior = f.getSalario();
                funcionarioMaiorSalario = f;
            }
        }

        relatorioMaiorSalarioFuncionario.append("\nFuncionário com maior sálario: \n" +
                "Nome: " +
                funcionarioMaiorSalario.getNome() +
                "Salário: R$ " + funcionarioMaiorSalario.getSalario()
              );

        return relatorioMaiorSalarioFuncionario;
    }


    //Menor sálario

    public StringBuilder funcionarioMenorSalario(){

        if(funcionarios.isEmpty()){
            return new StringBuilder("Nenhum funcionário cadastrado.");
        }

        Double menor = funcionarios.get(0).getSalario();

        Funcionario funcionarioMenorSalario = funcionarios.get(0);

        StringBuilder relatorioMenorSalarioFuncionario = new StringBuilder("");

        for (Funcionario f: funcionarios){


            if(f.getSalario() < menor){
                menor = f.getSalario();
                funcionarioMenorSalario = f;
            }
        }

        relatorioMenorSalarioFuncionario.append("\nFuncionário com Menor sálario: \n" +
                "Nome: " +
                funcionarioMenorSalario.getNome() +
                "Salário: R$ " + funcionarioMenorSalario.getSalario()
        );

        return relatorioMenorSalarioFuncionario;
    }

    // media salarial

    public Double mediaSalaria(){
        Double media, soma = 0.0;
        int numFunc = 0;

        for (Funcionario f: funcionarios){
            soma += f.getSalario();
            numFunc++;
        }

        media = soma/numFunc;

        return media;
    }


    // getters e setters e toString


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", quantidadeFuncionarios=" + (funcionarios != null ? funcionarios.size() : 0) +
                '}';
    }
}



