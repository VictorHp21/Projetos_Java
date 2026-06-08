package Entities;

import Enums.Cargo;
import Enums.Departamento;
import Excecoes.funcionarioException;

import java.time.LocalDate;

public class Vendedor extends Funcionario{
    public Vendedor(Long id, Integer idade, String cpf, Cargo cargo, String nome, Double salario, Departamento departamento, LocalDate dataContratacao, boolean statusDaContratacao) {
        super(id, idade, cpf, cargo, nome, salario, departamento, dataContratacao, statusDaContratacao);
    }

    @Override
    public String aumentarSalario(Double valor){
        // aumento do salário n pode ser maior que 40%
        // no aumento hávera um bonus de 10% como se fosse uma bonificação por vendas feitas


        if (valor > this.getSalario() * 0.4){
            throw new funcionarioException("O aumento para o vendedor não pode ser mais de 40% do sálario");
        }

        this.setSalario(
                this.getSalario() + valor + (valor * 0.1)
        );

        return "Aumento de sálario (Vendedor) efetuado com sucesso!\n" +
                "Novo sálario do vendedor R$ " + getSalario();
    }


}
