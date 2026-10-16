import java.util.List;

public class Pedido {
    private int numero;
    private List<Produto> produtos;


    public Pedido(int numero, List<Produto> produtos) {
        this.numero = numero;
        this.produtos = produtos;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "numero=" + numero +
                ", produtos=" + produtos +
                '}';
    }
}
