package Model.entities;

public class ConversorPeso {
    private double valor;

    public ConversorPeso(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String converterParaGramas(){
        double gramas = valor * 1000;
        return String.format("Gramas: %2.f", gramas);
    }

    public String converterParaQuilogramas(){
        double quilogramas = valor / 1000;
        return String.format("Quilogramas: %.2f", quilogramas);
    }


    public String converterParaToneladas(){
        double toneladas = valor / 1000000;
        return String.format("Toneladas: %.2f", toneladas);
    }
}
