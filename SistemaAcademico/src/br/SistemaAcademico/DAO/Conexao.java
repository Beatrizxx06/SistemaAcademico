package br.SistemaAcademico.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL =
            "jdbc:mysql://localhost:3306/sistema_academico";

    private static final String USUARIO = "root";

    private static final String SENHA = "Oklaroma2010@";

    public static Connection obterConexao() throws SQLException {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {

            throw new SQLException("Driver MySQL não encontrado.");
        }

        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}