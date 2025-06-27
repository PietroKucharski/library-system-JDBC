package model.dao;

import model.entities.User;

import java.util.List;

public interface UserDao {
    void insert(User author);

    void update(User author);

    void deleteById(Integer id);

    User findById(Integer id);

    List<User> findAll();
}
