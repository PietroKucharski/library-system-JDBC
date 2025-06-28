package application;

import model.dao.AuthorDao;
import model.dao.factory.DaoFactory;
import model.entities.Author;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AuthorDao authorDao = DaoFactory.createAuthorDao();

        /* Testing Author Insert Method */
        /*Author author = new Author(
                "Clarice Lispector",
                "Brasileira",
                LocalDate.of(1920, 12, 10),
                "Clarice foi uma escritora e jornalista conhecida por seu estilo introspectivo e obras como " +
                        "'A Hora da Estrela'.");

        authorDao.insert(author);
        System.out.println("Author Inserted! New id = " + author.getId());*/

        /* Testing Author findAll() Method */
        /*List<Author> list = authorDao.findAll();
        for (Author author1 : list) {
            System.out.println(author1);
        }*/

        /* Testing Author findById() Method */
        System.out.println(authorDao.findById(3));
    }
}
