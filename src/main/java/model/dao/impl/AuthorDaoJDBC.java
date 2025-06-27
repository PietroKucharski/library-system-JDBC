package model.dao.impl;

import database.DatabaseConnection;
import database.exceptions.DbException;
import model.dao.AuthorDao;
import model.entities.Author;
import model.entities.Book;

import java.sql.*;
import java.util.List;

public class AuthorDaoJDBC implements AuthorDao {
    private Connection connection;

    public AuthorDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Author author) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO authors (name, nationality, birth_date, biography) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getNationality());
            preparedStatement.setDate(3, java.sql.Date.valueOf(author.getBirthDate()));
            preparedStatement.setString(4, author.getBiography());

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    author.setId(id);
                }
                DatabaseConnection.closeResultSet(resultSet);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    @Override
    public void update(Author author) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Author findById(Integer id) {
        return null;
    }

    @Override
    public List<Author> findAll() {
        return List.of();
    }

    @Override
    public List<Book> findAllBook(Author author) {
        return List.of();
    }
}
