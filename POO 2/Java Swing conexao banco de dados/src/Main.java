import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main (String[] args) throws SQLException {
        ConexaoBancoDeDados objBancoDados = new ConexaoBancoDeDados();

        // como fazer crud (create, read, update, delete)
        if(objBancoDados != null){
            Statement inserir = objBancoDados.conexaobd.createStatement();
            String query = "INSERT INTO pessoa(id, nome, sobrenome) VALUES (1, 'HORACIO', 'EXCELSIOR')";
            inserir.execute(query);
        }

        // outra opção de fazer o insert
        PreparedStatement psInsert = objBancoDados.conexaobd.prepareStatement("INSERT INTO pessoa(id, nome, sobrenome) VALUES (?,?,?)");
        psInsert.setString(1, "2");
        psInsert.setString(2, "Renata");
        psInsert.setString(3, "Rocha");
        psInsert.execute();

        // para consulta no bd
        Statement consulta = objBancoDados.conexaobd.createStatement();
        ResultSet resultadoConsulta = consulta.executeQuery("SELECT * FROM pessoa");

        while(resultadoConsulta.next()){
            System.out.println("ID: " + resultadoConsulta.getString("id") + "" + resultadoConsulta.getString("nome") + "" + resultadoConsulta.getString("sobrenome"));
        }

        // atualizar dados
        Statement comandoUpdate = objBancoDados.conexaobd.createStatement();
        comandoUpdate.execute("UPDATE pessoa SET nome = 'Andreia' WHERE id = 2");

        // deletar um dado
        Statement comandoDelete = objBancoDados.conexaobd.createStatement();
        comandoDelete.execute("DELETE FROM pessoa where id = 2");



    }
}
