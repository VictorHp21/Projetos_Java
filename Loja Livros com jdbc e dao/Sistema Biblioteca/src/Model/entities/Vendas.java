package Model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Vendas implements Serializable {
    private Integer Id;
    private Date data_venda;
    private Double valor;

    private Cliente cliente;

    private Funcionario funcionario;

    private List<ItemVenda> itens;

    public Vendas() {
        this.itens = new ArrayList<>();
    }

    public Vendas(Integer id, List<ItemVenda> itens, Funcionario funcionario, Cliente cliente, Double valor, Date data_venda) {
        Id = id;
        this.itens = itens;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.valor = valor;
        this.data_venda = data_venda;
    }
}
