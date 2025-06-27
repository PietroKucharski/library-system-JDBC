package application;

import model.dao.AuthorDao;
import model.dao.factory.DaoFactory;
import model.entities.Author;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        AuthorDao authorDao = DaoFactory.createAuthorDao();

        Author author = new Author(
                "Jorge Amado",
                "Brasileiro",
                LocalDate.of(1912, 8, 10),
                "Jorge Amado foi um dos mais famosos e traduzidos escritores brasileiros, conhecido por obras " +
                        "como 'Gabriela, Cravo e Canela'.");

        authorDao.insert(author);
        System.out.println("Author Inserted! New id = " + author.getId());
    }
}
