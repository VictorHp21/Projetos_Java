package Model.entities;

public class ConversorMoedas implements ICoversor {
    private Double valor;
    private Double cotacao;

    public ConversorMoedas(Double valor, Double cotacao) {
        this.valor = valor;
        this.cotacao = cotacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getCotacao() {
        return cotacao;
    }

    public void setCotacao(Double cotacao) {
        this.cotacao = cotacao;
    }

    @Override
    public Double converter(){
        return valor * cotacao;
    }

    public String NomeMoeda(){
        if(cotacao == 0.1870){
            return "$";
        }else if(cotacao == 5.36){
            return "R$";
        }else if(cotacao == 0.1592){
            return "€";
        } else if(cotacao == 6.28){
            return "R$";
        }
        else{
            return "R$";
        }
    }
}
