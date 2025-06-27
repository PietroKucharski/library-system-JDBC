package model.dao.impl;

import model.dao.AuthorDao;
import model.entities.Author;
import model.entities.Book;

import java.sql.Connection;
import java.util.List;

public class AuthorDaoJDBC implements AuthorDao {
    private Connection connection;

    public AuthorDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Author author) {

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
