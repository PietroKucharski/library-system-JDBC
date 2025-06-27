package model.dao.factory;

import database.DatabaseConnection;
import model.dao.AuthorDao;
import model.dao.BookDao;
import model.dao.LoanDao;
import model.dao.UserDao;
import model.dao.impl.AuthorDaoJDBC;
import model.dao.impl.BookDaoJDBC;
import model.dao.impl.LoanDaoJDBC;
import model.dao.impl.UserDaoJDBC;

public class DaoFactory {
    public static BookDao createBookDao() {
        return new BookDaoJDBC(DatabaseConnection.getConnection());
    }

    public static AuthorDao createAuthorDao() {
        return new AuthorDaoJDBC(DatabaseConnection.getConnection());
    }

    public static UserDao createUserDao() {
        return new UserDaoJDBC(DatabaseConnection.getConnection());
    }

    public static LoanDao createLoanDao() {
        return new LoanDaoJDBC(DatabaseConnection.getConnection());
    }
}
