package model.dao.impl;

import model.dao.LoanDao;
import model.entities.Book;
import model.entities.Loan;
import model.entities.User;

import java.sql.Connection;
import java.util.List;

public class LoanDaoJDBC implements LoanDao {
    private Connection connection;

    public LoanDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Loan loan) {

    }

    @Override
    public void update(Loan loan) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void returnLoan(User user, Book book) {

    }

    @Override
    public Loan findById(Integer id) {
        return null;
    }

    @Override
    public Loan findByBookTitle(String title) {
        return null;
    }

    @Override
    public List<Loan> findAll() {
        return List.of();
    }

    @Override
    public List<Loan> findByUserId(Integer userId) {
        return List.of();
    }

    @Override
    public List<Loan> findByBookId(Integer bookId) {
        return List.of();
    }

    @Override
    public boolean isBookLoaned(Integer bookId) {
        return false;
    }
}
