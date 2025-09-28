package Model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Cliente implements Serializable {
    private Integer Id;
    private String Nome;

    public Cliente(String nome, Integer id) {
        Nome = nome;
        Id = id;
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
        Cliente cliente = (Cliente) o;
        return Objects.equals(Id, cliente.Id) && Objects.equals(Nome, cliente.Nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, Nome);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "Id=" + Id +
                ", Nome='" + Nome + '\'' +
                '}';
    }
}
