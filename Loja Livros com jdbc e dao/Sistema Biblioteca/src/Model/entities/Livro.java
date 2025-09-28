package Model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Livro implements Serializable {
    private Integer Id;
    private String Nome;
    private String Autor;
    private String Categoria;
    private double Preco;
    private int Quantidade;

    public Livro(Integer id, String nome, String autor, String categoria, double preco, int quantidade) {
        Id = id;
        Nome = nome;
        Autor = autor;
        Categoria = categoria;
        Preco = preco;
        Quantidade = quantidade;
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

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String autor) {
        Autor = autor;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public double getPreco() {
        return Preco;
    }

    public void setPreco(double preco) {
        Preco = preco;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        Quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(Id, livro.Id) && Objects.equals(Nome, livro.Nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, Nome);
    }

    @Override
    public String toString() {
        return "Livro{" +
                "Id=" + Id +
                ", Nome='" + Nome + '\'' +
                ", Autor='" + Autor + '\'' +
                ", Categoria='" + Categoria + '\'' +
                ", Preco=" + Preco +
                ", Quantidade=" + Quantidade +
                '}';
    }
}
