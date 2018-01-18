package ru.voting.api.restaurants.repository;

import ru.voting.api.restaurants.model.User;

import java.time.LocalTime;
import java.util.List;

public interface UserRepository {

    User get(int id);

    User getByEmail(String email);

    List<User> getAll();

    User save(User user);

    boolean delete(int id);

    boolean vote(User user, int restaurantId, LocalTime endVotingTime);

}
