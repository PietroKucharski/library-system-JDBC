package application;

import model.dao.AuthorDao;
import model.dao.BookDao;
import model.dao.LoanDao;
import model.dao.UserDao;
import model.dao.factory.DaoFactory;
import model.entities.Author;
import model.entities.Book;
import model.entities.Loan;
import model.entities.User;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AuthorDao authorDao = DaoFactory.createAuthorDao();
        BookDao bookDao = DaoFactory.createBookDao();
        UserDao userDao = DaoFactory.createUserDao();
        LoanDao loanDao = DaoFactory.createLoanDao();

        /* Testing Author Insert Method */
        /*Author author = new Author(
                "Cecília Meireles",
                "Brasileira",
                LocalDate.of(1901, 11, 7),
                "Cecília foi uma importante poetisa, professora e jornalista brasileira, conhecida por obras " +
                        "como 'Romanceiro da Inconfidência' e por sua escrita lírica e intimista.");

        authorDao.insert(author);
        System.out.println("Author Inserted! New id = " + author.getId());*/

        /* Testing Author findAll() Method */
        /*List<Author> list = authorDao.findAll();
        for (Author author1 : list) {
            System.out.println(author1);
        }*/

        /* Testing Author findById() Method */
        /*System.out.println(authorDao.findById(3));*/

        /* Testing Author findAllBooks() Method */
        /*List<Book> books = authorDao.findAllBooks("Clarice Lispector");
        for (Book book : books) {
            System.out.println(book);
        }*/

        /*---------------*/

        /*Author author = new Author();
        author.setId(1);

        Book book = new Book("O Senhor dos Anéis", author, LocalDate.of(1954, 7, 29), "Fantasia", true);

        bookDao.insert(book);
        System.out.println("Book Inserted! New id = " + book.getId());*/

        /*Testing Book findAll() Method */
        /*List<Book> list = bookDao.findAll();
        for (Book book : list) {
            System.out.println(book);
        }*/

        /*Testing Book findById() Method*/
        /*System.out.println(bookDao.findById(1));*/

        /*Testing Book findByTitle() Method*/
        /*System.out.println(bookDao.findByTitle("O Senhor dos Anéis"));*/

        /*Teting Book findByGenre() Method*/
        /*System.out.println(bookDao.findByGenre("Fantasia"));*/

        /*Teting Book update() Method*/
        /*Book book = bookDao.findById(1);
        book.setTitle("Novo Título Atualizado");
        book.setGenre("Ficção Científica");
        book.setAvailable(false);
        bookDao.update(book);*/

        /*User user = new User();
        user.setName("Pietro Kucharski");
        user.setEmail("pietro@email.com");
        user.setCpf("52998224725");

        userDao.insert(user);
        System.out.println("Usuário inserido com ID: " + user.getId());*/

        /*List<User> list = userDao.findAll();
        for (User users : list) {
            System.out.println(users);
        }*/

        /*Testing User findById() Method*/
        /*System.out.println(userDao.findById(1));*/

        /*Teting User update() Method*/
        /*User user = userDao.findById(1);
        user.setName("Vittor");
        user.setEmail("vittorlanau@gmail.com");
        user.setCpf("13238759990");
        userDao.update(user);*/

        /*User user = userDao.findById(1);
        Book book = bookDao.findById(1);

        Loan loan = new Loan(book, user);
        loanDao.insert(loan);*/

        /*List<Loan> list = loanDao.findAll();
        for (Loan loans : list) {
            System.out.println(loans);
        }*/

        /*System.out.println(loanDao.findById(2));*/

        /*System.out.println(loanDao.findByBookTitle("Novo Título Atualizado"));*/
    }
}
