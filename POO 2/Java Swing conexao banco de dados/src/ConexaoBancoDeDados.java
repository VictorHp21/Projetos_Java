
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDeDados {

    public Connection conexaobd;
    private final String url_bancodedados = "jdbc:mysql://localhost:3306/bdexemplojavas";
    private final String usuario = "victor";
    private final String senha = "admin";

    public ConexaoBancoDeDados(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexaobd = DriverManager.getConnection(url_bancodedados, usuario, senha);
            System.out.println("Conexão realizado com sucesso!");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erro ao acesso o banco de dados");
        }
    }

}
