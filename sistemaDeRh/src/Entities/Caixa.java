package Entities;

import Enums.Cargo;
import Enums.Departamento;
import Excecoes.funcionarioException;

import java.time.LocalDate;

public class Caixa extends Funcionario{
    public Caixa(Long id, Integer idade, String cpf, Cargo cargo, String nome, Double salario, Departamento departamento, LocalDate dataContratacao, boolean statusDaContratacao) {
        super(id, idade, cpf, cargo, nome, salario, departamento, dataContratacao, statusDaContratacao);
    }

    @Override
    public String aumentarSalario(Double valor){
        // aumento do salário n pode ser maior que 10%
        // no aumento hávera um bonus de 5% como se fosse uma quebra de caixa


        if (valor > this.getSalario() * 0.1){
            throw new funcionarioException("O aumento para o caixa não pode ser mais de 10% do sálario");
        }

        this.setSalario(
                this.getSalario() + valor + (valor * 0.05)
        );

        return "Aumento de sálario (Caixa) efetuado com sucesso!\n" +
                "Novo sálario do caixa R$ " + getSalario();
    }
}
