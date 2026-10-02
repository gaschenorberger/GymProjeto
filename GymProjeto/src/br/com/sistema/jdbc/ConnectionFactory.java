package br.com.sistema.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    private static final String URL = "jdbc:mysql://localhost:3306/bdgym?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            // Retorna null caso o MySQL não esteja rodando na máquina de testes,
            // permitindo que o DAO utilize dados persistentes em memória no modo demonstrativo.
            return null;
        }
    }
}
