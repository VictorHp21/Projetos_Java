package com.victor.Gerador.Model;

import java.time.LocalDate;

public class Vendedor {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataAdmissao;
    private boolean ativo;

    public Vendedor() {
    }


    public Vendedor(Long id, String nome, String email,
                    String telefone, LocalDate dataAdmissao,
                    boolean ativo) {

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataAdmissao = dataAdmissao;
        this.ativo = ativo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", dataAdmissao=" + dataAdmissao +
                ", ativo=" + ativo +
                '}';
    }
}
