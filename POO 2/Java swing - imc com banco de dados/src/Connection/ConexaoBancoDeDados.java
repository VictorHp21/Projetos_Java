package Connection;

import Entities.Pessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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


    private void EncerrarConexao() throws SQLException {

        if (conexaobd != null) {
            conexaobd.close();
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

            psInsert.setString(1, ObjetoPessoa.getNomeCompleto());

            psInsert.setString(2, ObjetoPessoa.getEndereço());
            psInsert.setString(3, ObjetoPessoa.getTelefone());
            psInsert.setString(4, ObjetoPessoa.getCPF());
            psInsert.setString(5, ObjetoPessoa.getTipoSanguineo());
            psInsert.setString(6, ObjetoPessoa.getCurso());
            psInsert.setString(7, ObjetoPessoa.getContatoDeEmergencia());
            psInsert.setString(8, ObjetoPessoa.getTelefoneEmergencia());

            psInsert.setDouble(9, ObjetoPessoa.getAltura());
            psInsert.setDouble(10, ObjetoPessoa.getPeso());
            psInsert.setDouble(11, ObjetoPessoa.getImc());

            psInsert.execute();

            EncerrarConexao();

            return "Cadastro realizado com sucesso!";

        } else {
            return "Erro! Inserção não realizada!";
        }

    }

    public String RemoverPessoa(int id) throws SQLException{
        ConexaoBancoDeDados();

        if (conexaobd != null) {

            // deletar um registro do banco de dados

            PreparedStatement comandoupdate =
                    conexaobd.prepareStatement(
                            "DELETE FROM pessoa WHERE id = ?"
                    );

            comandoupdate.setInt(1, id);

            comandoupdate.execute();

            EncerrarConexao();

            return "Remoção realizada com sucesso!";

        } else {

            return "Erro! Alteração não realizada!";

        }
    }

    public List cadastrados() throws SQLException{
        ConexaoBancoDeDados();

        List<Pessoa> pessoas = new ArrayList<>();

        if(conexaobd != null){
            PreparedStatement comandoSelect =
                    conexaobd.prepareStatement(
                            "SELECT * FROM pessoa"
                    );

            ResultSet resultado = comandoSelect.executeQuery();

            while (resultado.next()){
                Pessoa pessoa = new Pessoa(
                        resultado.getString("nome_completo"),
                        resultado.getString("endereco"),
                        resultado.getString("telefone"),
                        resultado.getString("cpf"),
                        resultado.getString("tipo_sanguineo"),
                        resultado.getString("curso"),
                        resultado.getString("contato_emergencia"),
                        resultado.getString("telefone_emergencia"),
                        resultado.getDouble("altura"),
                        resultado.getDouble("peso")
                );

                pessoa.setId(resultado.getInt("id"));

                pessoas.add(pessoa);
            }

            resultado.close();
            comandoSelect.close();

        }

        return pessoas;
    }


}
