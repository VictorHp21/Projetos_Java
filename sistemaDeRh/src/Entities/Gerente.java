package Entities;

import Enums.Cargo;
import Enums.Departamento;
import Excecoes.funcionarioException;

import java.time.LocalDate;

public class Gerente extends Funcionario{
    public Gerente(Long id, Integer idade, String cpf, Cargo cargo, String nome, Double salario, Departamento departamento, LocalDate dataContratacao, boolean statusDaContratacao) {
        super(id, idade, cpf, cargo, nome, salario, departamento, dataContratacao, statusDaContratacao);
    }

    @Override
    public String aumentarSalario(Double valor){
        // aumento do salário n pode ser maior que 50%



        if (valor > this.getSalario() * 0.5){
            throw new funcionarioException("O aumento para o gerente não pode ser mais de 50% do sálario");
        }

        this.setSalario(
                this.getSalario() + valor
        );

        return "Aumento de sálario (Gerente) efetuado com sucesso!\n" +
                "Novo sálario do gerente R$ " + getSalario();
    }
}
