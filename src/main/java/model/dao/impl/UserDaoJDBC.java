package model.dao.impl;

import model.dao.UserDao;
import model.entities.User;

import java.sql.Connection;
import java.util.List;

public class UserDaoJDBC implements UserDao {
    private Connection connection;

    public UserDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(User author) {

    }

    @Override
    public void update(User author) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public User findById(Integer id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }
}
