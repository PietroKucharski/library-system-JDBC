package model.dao.impl;

import database.DatabaseConnection;
import database.exceptions.DbException;
import model.dao.UserDao;
import model.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBC implements UserDao {
    private Connection connection;

    public UserDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(User user) {
        PreparedStatement preparedStatement = null;

        try {
            if (!isValidCPF(user.getCpf())) {
                throw new DbException("CPF inválido!");
            }

            preparedStatement = connection.prepareStatement("INSERT INTO users (name, email, cpf) VALUES(?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCpf());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    user.setId(id);
                }
                DatabaseConnection.closeResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    @Override
    public void update(User author) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public User findById(Integer id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM users");
            resultSet = preparedStatement.executeQuery();

            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                User user = instantiateUser(resultSet);
                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }

            int primeiroDigito = 11 - (soma % 11);
            if (primeiroDigito >= 10) {
                primeiroDigito = 0;
            }

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }

            int segundoDigito = 11 - (soma % 11);
            if (segundoDigito >= 10) {
                segundoDigito = 0;
            }

            return primeiroDigito == Character.getNumericValue(cpf.charAt(9)) &&
                    segundoDigito == Character.getNumericValue(cpf.charAt(10));
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    private User instantiateUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setCpf(resultSet.getString("cpf"));

        return user;
    }
}
