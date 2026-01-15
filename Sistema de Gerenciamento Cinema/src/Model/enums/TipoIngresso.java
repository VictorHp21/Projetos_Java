package Model.enums;

public enum TipoIngresso {
    INTEIRA(1.0),
    MEIA(0.5);

    private final Double fatorDesconto;


    TipoIngresso(Double fatorDesconto) {
        this.fatorDesconto = fatorDesconto;
    }

    public Double getFatorDesconto() {
        return fatorDesconto;
    }
}
