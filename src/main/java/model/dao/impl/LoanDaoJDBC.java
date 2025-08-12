package model.dao.impl;

import database.DatabaseConnection;
import database.exceptions.DbException;
import model.dao.LoanDao;
import model.entities.Book;
import model.entities.Loan;
import model.entities.User;

import java.sql.*;
import java.util.List;

public class LoanDaoJDBC implements LoanDao {
    private Connection connection;

    public LoanDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Loan loan) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("INSERT INTO public.loans (book_id, user_id, date_loan, " +
                    "expected_return_date) VALUES(?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, loan.getBook().getId());
            preparedStatement.setInt(2, loan.getUser().getId());
            preparedStatement.setDate(3, java.sql.Date.valueOf(loan.getDateLoan()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(loan.getExpectedReturnDate()));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    loan.setId(id);
                }
                DatabaseConnection.closeResultSet(resultSet);
            } else {
                throw new DbException("Error while inserting loan");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(preparedStatement);
        }
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
