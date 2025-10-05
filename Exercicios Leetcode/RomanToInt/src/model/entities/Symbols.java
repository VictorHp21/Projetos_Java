package model.entities;

import java.util.Objects;

public class Symbols {
    private String symbol;
    private int valor;

    public Symbols(String symbol, int valor) {
        this.symbol = symbol;
        this.valor = valor;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Symbols symbols = (Symbols) o;
        return valor == symbols.valor && Objects.equals(symbol, symbols.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, valor);
    }

    @Override
    public String toString() {
        return "Symbols{" +
                "symbol='" + symbol + '\'' +
                ", valor=" + valor +
                '}';
    }
}
