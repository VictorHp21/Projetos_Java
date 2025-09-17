package Model.entities;

import java.util.Objects;

public class Fucionario {
    private Integer Id;
    private String Nome;
    private String Endereco;
    private String Telefone;
    private String Cpf;
    private Double salario;
    private String cargo;

    private Departamento departamento;

    public Fucionario() {}

    public Fucionario(Integer id, String nome, String endereco, String telefone, String cpf, Double salario, String cargo, Departamento departamento) {
        Id = id;
        Nome = nome;
        Endereco = endereco;
        Telefone = telefone;
        Cpf = cpf;
        this.salario = salario;
        this.cargo = cargo;
        this.departamento = departamento;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getCpf() {
        return Cpf;
    }

    public void setCpf(String cpf) {
        Cpf = cpf;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fucionario that = (Fucionario) o;
        return Objects.equals(Nome, that.Nome);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Nome);
    }
}
