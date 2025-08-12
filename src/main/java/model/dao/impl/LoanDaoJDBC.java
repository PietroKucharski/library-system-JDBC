package model.dao.impl;

import database.DatabaseConnection;
import database.exceptions.DbException;
import model.dao.LoanDao;
import model.entities.Author;
import model.entities.Book;
import model.entities.Loan;
import model.entities.User;

import java.sql.*;
import java.util.ArrayList;
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
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT l.id, l.user_id, l.book_id, l.date_loan," +
                    "l.expected_return_date, l.return_date, u.name AS user_name, u.email, b.title AS book_title, a.name AS author_name, " +
                    "a.birth_date FROM loans l JOIN users u ON l.user_id = u.id JOIN books b ON l.book_id = b.id JOIN authors a " +
                    "ON b.author_id = a.id WHERE l.id = ?;");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return instantiateLoan(resultSet);
            }

            return null;
        } catch (SQLException e) {
            throw new  DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
            DatabaseConnection.closeStatement(preparedStatement);
        }
    }

    @Override
    public Loan findByBookTitle(String title) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT l.id, l.user_id, l.book_id, l.date_loan," +
                    "l.expected_return_date, l.return_date, u.name AS user_name, u.email, b.title AS book_title, a.name AS author_name, " +
                    "a.birth_date FROM loans l JOIN users u ON l.user_id = u.id JOIN books b ON l.book_id = b.id JOIN authors a " +
                    "ON b.author_id = a.id WHERE b.title = ?;");
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return instantiateLoan(resultSet);
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
    public List<Loan> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT l.id, l.date_loan, l.expected_return_date, " +
                    "l.return_date, u.name AS user_name, u.email, b.title AS book_title, a.name AS author_name, " +
                    "a.birth_date FROM loans l JOIN users u\n" +
                    "ON l.user_id = u.id JOIN books b ON l.book_id = b.id JOIN authors a ON b.author_id = a.id;");

            resultSet = preparedStatement.executeQuery();

            List<Loan> loans = new ArrayList<>();

            while (resultSet.next()) {
                Loan loan = instantiateLoan(resultSet);
                loans.add(loan);
            }

            return loans;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
            DatabaseConnection.closeStatement(preparedStatement);
        }
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

    private Loan instantiateLoan(ResultSet resultSet) throws SQLException {
        Loan loan = new Loan();
        loan.setId(resultSet.getInt("id"));
        loan.setDateLoan(resultSet.getDate("date_loan") != null
                ? resultSet.getDate("date_loan").toLocalDate() : null);
        loan.setExpectedReturnDate(resultSet.getDate("expected_return_date") != null
                ? resultSet.getDate("expected_return_date").toLocalDate() : null);
        loan.setReturnDate(resultSet.getDate("return_date") != null
                ? resultSet.getDate("return_date").toLocalDate() : null);

        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("user_name"));
        user.setEmail(resultSet.getString("email"));

        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("book_title"));

        Author author = new Author();
        author.setId(resultSet.getInt("id"));
        author.setName(resultSet.getString("author_name"));
        author.setBirthDate(resultSet.getDate("birth_date") != null
                ? resultSet.getDate("birth_date").toLocalDate()
                : null);


        book.setAuthor(author);
        loan.setUser(user);
        loan.setBook(book);

        return loan;
    }
}
