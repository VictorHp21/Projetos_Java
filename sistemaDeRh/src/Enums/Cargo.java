package Enums;

public enum Cargo {
    VENDEDOR("Vendedor"),
    CAIXA("Caixa"),
    GERENTE("Gerente");

    private final String descricao;

    Cargo(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
