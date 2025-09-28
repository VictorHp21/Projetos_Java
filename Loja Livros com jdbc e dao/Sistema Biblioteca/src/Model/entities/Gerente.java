package Model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Gerente implements Serializable {
    private Integer Id;
    private String nome;
    private String senha;
    private  Double salario;

    private Departamento departamento;

    public Gerente(Integer id, String nome, String senha, Double salario, Departamento departamento) {
        Id = id;
        this.nome = nome;
        this.senha = senha;
        this.salario = salario;
        this.departamento = departamento;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
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
        Gerente gerente = (Gerente) o;
        return Objects.equals(Id, gerente.Id) && Objects.equals(nome, gerente.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, nome);
    }

    @Override
    public String toString() {
        return "Gerente{" +
                "Id=" + Id +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", salario=" + salario +
                ", departamento=" + departamento +
                '}';
    }
}