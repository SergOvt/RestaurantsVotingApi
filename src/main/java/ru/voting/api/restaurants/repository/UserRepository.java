package ru.voting.api.restaurants.repository;

import ru.voting.api.restaurants.model.User;

import java.util.List;

public interface UserRepository {

    User get(int id);

    List<User> getAll();

    User save(User user);

    boolean delete(int id);

}
