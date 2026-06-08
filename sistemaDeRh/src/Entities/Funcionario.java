package Entities;

import Enums.Cargo;
import Enums.Departamento;
import Excecoes.funcionarioException;

import java.time.LocalDate;
import java.time.Period;

public class Funcionario {
    protected Long id;
    protected Integer idade;
    protected String cpf;
    protected Enum<Cargo> cargo;
    protected String nome;
    protected Double salario;
    protected Enum<Departamento> departamento;
    protected LocalDate dataContratacao;
    protected boolean statusDaContratacao;

    public Funcionario(Long id, Integer idade, String cpf, Cargo cargo, String nome, Double salario,
                       Departamento departamento,
                       LocalDate dataContratacao, boolean statusDaContratacao) {
        this.idade = idade;
        this.cpf = cpf;
        this.cargo = cargo;
        this.nome = nome;
        this.salario = salario;
        this.departamento = departamento;
        this.dataContratacao = dataContratacao;
        this.statusDaContratacao = statusDaContratacao;
    }

    public Long getId() {
        return id;
    }


    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setCargo(Enum<Cargo> cargo) {
        this.cargo = cargo;
    }

    public Enum<Cargo> getCargo() {
        return cargo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Enum getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Enum departamento) {
        this.departamento = departamento;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public boolean isStatusDaContratacao() {
        return statusDaContratacao;
    }

    public void setStatusDaContratacao(boolean statusDaContratacao) {
        this.statusDaContratacao = statusDaContratacao;
    }


    // métodos

    public String aumentarSalario(Double valor){
        // aumento do salário n pode ser maior que 30%
        if (valor > salario * 0.3){
            throw new funcionarioException("O aumento não pode ser mais de 30% do sálario");
        }

        this.salario += valor;

        return "Aumento de sálario efetuado com sucesso!";
    }

    public String calcularTempoEmpresa(){
        LocalDate hoje = LocalDate.now();

        Period tempoDeEmpresa = Period.between(this.dataContratacao, hoje);

        return "***** Tempo de empresa *****\n" +
                "Dia: " + tempoDeEmpresa.getDays() + "" + tempoDeEmpresa.getMonths() + " meses e " + tempoDeEmpresa.getYears() + "anos";
    }


    // metodo toString

    @Override
    public String toString() {
        return
                "\nID: " + id +
                "\nNome: " + nome +
                "\nIdade: " + idade +
                "\nCPF: " + cpf +
                "\nCargo: " + cargo +
                "\nDepartamento: " + departamento +
                "\nSalário: R$ " + String.format("%.2f", salario) +
                "\nData de contratação: " + dataContratacao +
                "\nStatus: " + (statusDaContratacao ? "Ativo" : "Desligado") +
                "\nTempo de empresa: " + calcularTempoEmpresa() +
                "\n========================\n";
    }


}
