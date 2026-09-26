package Connection;

import Entities.Pessoa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexaoBancoDeDados {
    public Connection conexaobd;
    private final String url_bancodedados = "jdbc:mysql://tarefas-mysql-vectorhenrique21-5e4f.a.aivencloud" +
            ".com:16433/pessoasPOO2?sslMode=REQUIRED";
    private final String usuario = "admin";
    private final String senha = "123";

    public void ConexaoBancoDeDados(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexaobd = DriverManager.getConnection(url_bancodedados, usuario, senha);
            System.out.println("Conexão realizado com sucesso!");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erro ao acesso o banco de dados");
        }
    }

    public String InserirDados(Pessoa ObjetoPessoa) throws SQLException{
        ConexaoBancoDeDados();

        if(conexaobd != null){

            PreparedStatement psInsert =
                    conexaobd.prepareStatement(
                             "INSERT INTO pessoa(" +
                                    "nome_completo, endereco, telefone, cpf, tipo_sanguineo, curso, " +
                                    "contato_emergencia, telefone_emergencia, altura, peso, imc) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                    );



        }

    }


}
