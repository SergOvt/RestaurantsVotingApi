package ru.voting.api.restaurants.service;

import ru.voting.api.restaurants.model.User;

import java.util.List;

public interface UserService {

    User get(int id);

    List<User> getAll();

    User create(User user);

    User update(User user);

    void delete(int id);

}
