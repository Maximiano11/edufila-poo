package br.senac.pr.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String url = "jdbc:postgresql://localhost:5432/cadastro_cmei";
    private static final String user = "jeversonsolomon";
    private static final String password = " ";

    public static Connection conectar() throws SQLException {
        Connection conexao = DriverManager.getConnection(url, user, password);
        return conexao;
    }
}
