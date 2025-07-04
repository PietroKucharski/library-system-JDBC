package model.dao;

import model.entities.Book;

import java.util.List;

public interface BookDao {
    void insert(Book author);

    void update(Book author);

    void deleteById(Integer id);

    Book findById(Integer id);

    Book findByTitle(String title);

    Book findByGenre(String genre);

    List<Book> findAll();
}
