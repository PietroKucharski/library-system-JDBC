package model.dao.impl;

import model.dao.BookDao;
import model.entities.Book;

import java.sql.Connection;

public class BookDaoJDBC implements BookDao {
    private Connection connection;

    public BookDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Book author) {

    }

    @Override
    public void update(Book author) {

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
}
