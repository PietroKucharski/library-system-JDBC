package model.dao;

import model.entities.Book;
import model.entities.Loan;
import model.entities.User;

import java.util.List;

public interface LoanDao {
    void insert(Loan loan);

    void update(Loan loan);

    void deleteById(Integer id);

    void returnLoan(User user, Book book);

    Loan findById(Integer id);

    Loan findByBookTitle(String title);

    List<Loan> findAll();

    List<Loan> findByUserId(Integer userId);

    List<Loan> findByBookId(Integer bookId);

    boolean isBookLoaned(Integer bookId);
}
