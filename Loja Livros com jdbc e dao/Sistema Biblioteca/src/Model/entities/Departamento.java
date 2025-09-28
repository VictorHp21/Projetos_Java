package Model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Departamento implements Serializable {
    private Integer Id;
    private String Nome;

    public Departamento() {}

    public Departamento(Integer id, String nome) {
        Id = id;
        Nome = nome;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Departamento that = (Departamento) o;
        return Objects.equals(Nome, that.Nome);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Nome);
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "Id=" + Id +
                ", Nome='" + Nome + '\'' +
                '}';
    }
}
