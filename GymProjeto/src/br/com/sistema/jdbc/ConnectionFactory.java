package br.com.sistema.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/bdgym?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo";
    private static volatile String lastErrorMessage = "";
    private static volatile boolean warningShown;

    public static Connection getConnection() {
        try {
            carregarDriver();
            Connection connection = DriverManager.getConnection(
                    config("gym.db.url", "GYM_DB_URL", DEFAULT_URL),
                    config("gym.db.user", "GYM_DB_USER", "root"),
                    config("gym.db.password", "GYM_DB_PASSWORD", "")
            );
            lastErrorMessage = "";
            return connection;
        } catch (Exception e) {
            lastErrorMessage = e.getClass().getSimpleName() + ": " + e.getMessage();
            if (!warningShown) {
                warningShown = true;
                System.err.println("[GymProjeto] Banco indisponível. O sistema continuará no modo demonstrativo em memória.");
                System.err.println("[GymProjeto] Motivo: " + lastErrorMessage);
            }
            return null;
        }
    }

    public static String getLastErrorMessage() {
        return lastErrorMessage;
    }

    public static boolean isDatabaseAvailable() {
        try (Connection connection = getConnection()) {
            return connection != null;
        } catch (Exception ex) {
            return false;
        }
    }

    private static String config(String property, String environment, String defaultValue) {
        String value = System.getProperty(property);
        if (value == null || value.trim().isEmpty()) value = System.getenv(environment);
        return value == null || value.trim().isEmpty() ? defaultValue : value.trim();
    }

    private static void carregarDriver() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException modernoAusente) {
            Class.forName("com.mysql.jdbc.Driver");
        }
    }
}
