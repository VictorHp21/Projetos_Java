package Model.entities;

// classe intermediária

public class ItemVenda {
    private Integer id;
    private Integer quantidade;

    private Livro livro;
    private Vendas vendas;

    public ItemVenda() {}

    public ItemVenda(Integer id, Integer quantidade, Livro livro, Vendas vendas) {
        this.id = id;
        this.quantidade = quantidade;
        this.livro = livro;
        this.vendas = vendas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }
}
