package Model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Fucionario implements Serializable {
    private Integer Id;
    private String Nome;
    private Date DataNascimento;
    private Double salario;


    private Departamento departamento;

    public Fucionario() {}


    public Fucionario(Integer id, String nome, Date dataNascimento, Double salario, Departamento departamento) {
        Id = id;
        Nome = nome;
        DataNascimento = dataNascimento;
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
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Date getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        DataNascimento = dataNascimento;
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
        Fucionario that = (Fucionario) o;
        return Objects.equals(Nome, that.Nome);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Nome);
    }

    @Override
    public String toString() {
        return "Fucionario{" +
                "Id=" + Id +
                ", Nome='" + Nome + '\'' +
                ", DataNascimento=" + DataNascimento +
                ", salario=" + salario +
                ", departamento=" + departamento +
                '}';
    }
}
