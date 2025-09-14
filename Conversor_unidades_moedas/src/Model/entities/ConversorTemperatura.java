package Model.entities;

public class ConversorTemperatura  {
    private double valor;

    public ConversorTemperatura(double valor) {
        this.valor = valor;
    }

   public double getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }


    public double converterParaCelsius(){
        return (valor - 32) * 5 / 9;
    }

    public double converterParaFahrenheit(){
        return (valor * 9 / 5) + 32;
    }
}
