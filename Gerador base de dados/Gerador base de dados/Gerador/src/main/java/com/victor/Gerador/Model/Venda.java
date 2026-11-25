package com.victor.Gerador.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Venda {
    private Long id;
    private Cliente cliente;
    private Vendedor vendedor;
    private LocalDateTime dataVenda;
    private BigDecimal valorTotal;
    private String formaPagamento;
    private String status;


    public Venda() {
    }


    public Venda(Long id, Cliente cliente, Vendedor vendedor,
                 LocalDateTime dataVenda, BigDecimal valorTotal,
                 String formaPagamento, String status) {

        this.id = id;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.status = status;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", cliente=" + cliente.getNome() +
                ", vendedor=" + vendedor.getNome() +
                ", dataVenda=" + dataVenda +
                ", valorTotal=" + valorTotal +
                ", formaPagamento='" + formaPagamento + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
