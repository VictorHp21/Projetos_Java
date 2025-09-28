package Model.entities;

public class Gerente extends Fucionario{
    private char[] senha;

    private Departamento departamento;

    public Gerente() {}

    public Gerente(Integer id, String nome, String endereco, String telefone, String cpf, Double salario, String cargo, Departamento departamento) {
        super(id, nome, endereco, telefone, cpf, salario, cargo, departamento);
    }

    public char[] getSenha() {
        return senha;
    }

    public void setSenha(char[] senha) {
        this.senha = senha;
    }

    @Override
    public Departamento getDepartamento() {
        return departamento;
    }

    @Override
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public void adcFuncionario(){

    }

    public void removerFuncionario(){

    }

}
