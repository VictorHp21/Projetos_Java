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

    private Fucionario fucionario;

    private List<ItemVenda> itens;

    public Vendas() {
        this.itens = new ArrayList<>();
    }

    public Vendas(Integer id, Date data_venda, Double valor, Cliente cliente, Fucionario fucionario, List<ItemVenda> itens) {
        Id = id;
        this.data_venda = data_venda;
        this.valor = valor;
        this.cliente = cliente;
        this.fucionario = fucionario;
        this.itens = new ArrayList<>();
    }


}
