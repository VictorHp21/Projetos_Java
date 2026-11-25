package com.victor.Gerador.Model;

import java.time.LocalDate;

public class Cliente {

    private Long id;
    private String nome;
    private String email;
    private String cidade;
    private String estado;
    private LocalDate dataCadastro;


    public Cliente(){}

    public Cliente(Long id, String nome, String email,  String cidade, String estado, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.email = email;

        this.cidade = cidade;
        this.estado = estado;
        this.dataCadastro = dataCadastro;
    }

    // getters e setters

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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }



    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", dataCadastro=" + dataCadastro +
                '}';
    }
}
