package Model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Livro implements Serializable {
    private Integer Id;
    private String Nome;
    private String Autor;
    private Date DataPublicacao;
    private String Genero;
    private double Preco;
    private int Quantidade;

    public Livro(String nome, String autor, Date dataPublicacao, String genero, double preco, int quantidade) {
        Nome = nome;
        Autor = autor;
        DataPublicacao = dataPublicacao;
        Genero = genero;
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

    public Date getDataPublicacao() {
        return DataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        DataPublicacao = dataPublicacao;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
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
                ", DataPublicacao=" + DataPublicacao +
                ", Genero='" + Genero + '\'' +
                ", Preco=" + Preco +
                ", Quantidade=" + Quantidade +
                '}';
    }
}
