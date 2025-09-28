package application;

import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = DB.getConnection();
            System.out.println("✅ Conectado!");

        } catch (DbException e) {
           e.printStackTrace();
        } finally {
            DB.closeConnection();
            System.out.println("\n⚠️ Conexão fechada!");
        }

    }
}
