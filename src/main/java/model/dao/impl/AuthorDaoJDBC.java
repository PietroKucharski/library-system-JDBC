package model.dao.impl;

import database.DatabaseConnection;
import database.exceptions.DbException;
import model.dao.AuthorDao;
import model.entities.Author;
import model.entities.Book;

import java.sql.*;
import java.util.ArrayList;
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
                    "INSERT INTO authors (name, nationality, birth_date, biography) VALUES (?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getNationality());
            preparedStatement.setDate(3, java.sql.Date.valueOf(author.getBirthDate()));
            preparedStatement.setString(4, author.getBiography());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
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
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE public.authors SET name=?, nationality=?, " +
                    "birth_date=?, biography=? WHERE id = ?;");
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getNationality());
            preparedStatement.setDate(3, java.sql.Date.valueOf(author.getBirthDate()));
            preparedStatement.setString(4, author.getBiography());
            preparedStatement.setInt(5, author.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM public.authors WHERE id=?;");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Author findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM authors WHERE id = ?;");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return instantiateAuthor(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    @Override
    public List<Author> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM public.authors;");
            resultSet = preparedStatement.executeQuery();

            List<Author> authors = new ArrayList<>();

            while (resultSet.next()) {
                Author author = instantiateAuthor(resultSet);
                authors.add(author);
            }
            return authors;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Book> findAllBooks(String name) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT b.* FROM books b JOIN authors a ON b.author_id = a.id WHERE a.name = ?;");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                Book book = instantiateBook(resultSet);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(preparedStatement);
            DatabaseConnection.closeResultSet(resultSet);
        }
    }

    private Author instantiateAuthor(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getInt("id"));
        author.setName(resultSet.getString("name"));
        author.setNationality(resultSet.getString("nationality"));
        author.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
        author.setBiography(resultSet.getString("biography"));
        return author;
    }

    private Book instantiateBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));

        Author author = instantiateAuthor(resultSet);
        book.setAuthor(author);

        Date birthDate = resultSet.getDate("publication_date");
        if (birthDate != null) {
            book.setPublicationDate(birthDate.toLocalDate());
        }

        book.setGenre(resultSet.getString("genre"));
        book.setAvailable(resultSet.getBoolean("is_available"));

        return book;
    }
}
