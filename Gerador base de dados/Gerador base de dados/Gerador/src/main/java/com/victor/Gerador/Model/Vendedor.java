package com.victor.Gerador.Model;

import java.time.LocalDate;

public class Vendedor {
    private Long id;
    private String nome;
    private LocalDate dataAdmissao;
    private String setor;

    public Vendedor() {
    }

    public Vendedor(Long id, String nome, LocalDate dataAdmissao, String setor) {
        this.id = id;
        this.nome = nome;
        this.dataAdmissao = dataAdmissao;
        this.setor = setor;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }



    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }



    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataAdmissao=" + dataAdmissao +

                '}';
    }
}
