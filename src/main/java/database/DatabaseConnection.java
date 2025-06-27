package database;

import database.exceptions.DbException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection = null;

    public static Connection getConnection() { // Faz a conexão com o banco de dados
        if (connection == null) {
            try {
                Properties props = loadProperties(); // Chama metodo para pegar as propriedades no arquivo db.properties
                String url = props.getProperty("url");
                connection = DriverManager.getConnection(url);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }

    private static Properties loadProperties() { // Metodo para pegar as informações de conexão com banco de dados
        try (FileInputStream fs = new FileInputStream("db.properties")) { // Le o arquivo db.properties
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static void closeConnection() { // Fecha a conexão com banco de dados
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
