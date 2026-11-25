package com.victor.Gerador.Model;

import java.math.BigDecimal;

public class ItemVenda {
    private Long id;
    private Venda venda;
    private Produto produto;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subtotal;


    public ItemVenda() {
    }

    public ItemVenda(Long id, Venda venda, Produto produto,
                     Integer quantidade, BigDecimal precoUnitario,
                     BigDecimal subtotal) {

        this.id = id;
        this.venda = venda;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "ItemVenda{" +
                "id=" + id +
                ", venda=" + venda.getId() +
                ", produto=" + produto.getNome() +
                ", quantidade=" + quantidade +
                ", precoUnitario=" + precoUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
}
