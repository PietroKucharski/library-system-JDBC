package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection;

    public static void connect() {
        try {
            String url = "jdbc:postgresql://localhost:5432/library_system";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "123456");
            Connection conn = DriverManager.getConnection(url, props);

            System.out.println("Conexão com banco realizada com sucesso");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
