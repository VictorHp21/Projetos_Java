import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main (String[] args) throws SQLException {
        ConexaoBancoDeDados conexao = new ConexaoBancoDeDados();

        // como fazer crud (create, read, update, delete)
        if(conexao != null){
            Statement inserir = conexao.conexaobd.createStatement();
            String query = "INSERT INTO pessoa(id, nome, sobrenome) VALUES (1, 'HORACIO', 'EXCELSIOR')";
            inserir.execute(query);
        }
    }
}
