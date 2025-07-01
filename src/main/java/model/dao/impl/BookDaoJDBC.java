package model.dao.impl;

import database.DatabaseConnection;
import database.exceptions.DbException;
import model.dao.BookDao;
import model.entities.Author;
import model.entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoJDBC implements BookDao {
    private Connection connection;

    public BookDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Book book) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("INSERT INTO public.books (title, author_id, " +
                    "publication_date, genre, is_available) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getAuthor().getId());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublicationDate()));
            preparedStatement.setString(4, book.getGenre());
            if (book.getAvailable() != null) {
                preparedStatement.setBoolean(5, book.getAvailable());
            } else {
                preparedStatement.setNull(5, Types.BOOLEAN);
            }
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    book.setId(id);
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
    public void update(Book book) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Book findById(Integer id) {
        return null;
    }

    @Override
    public Book findByTitle(String title) {
        return null;
    }

    @Override
    public Book findByGenre(String genre) {
        return null;
    }

    @Override
    public List<Book> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Faço um JOIN nas tabelas de books e authors para trazer os dados da tabela authors pelo author_id
            preparedStatement = connection.prepareStatement(
                    "SELECT b.*, a.name AS author_name, a.nationality, a.birth_date, a.biography " +
                            "FROM public.books b " +
                            "JOIN public.authors a ON b.author_id = a.id;"
            );
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

    private Book instantiateBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setPublicationDate(rs.getDate("publication_date") != null
                ? rs.getDate("publication_date").toLocalDate()
                : null); // Verifico se há dentro do resultset
        book.setGenre(rs.getString("genre"));
        book.setAvailable(rs.getBoolean("is_available"));

        Author author = new Author();
        author.setId(rs.getInt("author_id"));
        author.setName(rs.getString("author_name"));
        author.setNationality(rs.getString("nationality"));
        author.setBirthDate(rs.getDate("birth_date") != null
                ? rs.getDate("birth_date").toLocalDate()
                : null);
        author.setBiography(rs.getString("biography"));

        book.setAuthor(author);

        return book;
    }
}
