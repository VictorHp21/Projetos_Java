package com.victor.Gerador.Config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL =
            System.getenv("DB_URL");

    private static final String USUARIO =
            System.getenv("DB_USER");

    private static final String SENHA =
            System.getenv("DB_PASSWORD");

    public static Connection conectar() throws SQLException {

        if (URL == null || USUARIO == null || SENHA == null) {
            throw new IllegalStateException(
                    "Variáveis de ambiente do banco não configuradas!"
            );
        }

        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
