package ru.voting.api.restaurants.service;

import ru.voting.api.restaurants.model.User;

import java.util.List;

public interface UserService {

    User get(String email);

    List<User> getAll();

    User add(User user);

    User update(User user);

    void delete(String email);

}
