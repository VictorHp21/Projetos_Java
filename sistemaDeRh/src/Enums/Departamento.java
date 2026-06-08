package Enums;

public enum Departamento {
    VENDAS("Setor de vendas"),
    CAIXAS("Setor dos caixas"),
    ADMINISTRACAO("Gerência");

    private final String descricao;

    Departamento(String descricao){
        this.descricao = descricao;
    }

}
