package Model.entities;

import java.io.Serializable;
import java.util.Date;

public class Cliente implements Serializable {
    private Integer Id;
    private String Nome;
    private Date DataNascimento;
    private String endereco;
    private String telefone;

    public Cliente(Integer id, String nome, Date dataNascimento, String endereco, String telefone) {
        Id = id;
        Nome = nome;
        DataNascimento = dataNascimento;
        this.endereco = endereco;
        this.telefone = telefone;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
