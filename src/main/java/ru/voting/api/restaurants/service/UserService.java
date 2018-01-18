package ru.voting.api.restaurants.service;

import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.to.UserTo;

import java.time.LocalTime;
import java.util.List;

public interface UserService {

    User get(int id);

    List<User> getAll();

    User create(User user);

    User update(User user, int id);

    User update(UserTo userTo, int id);

    void delete(int id);

    void vote(User user, int restaurantId);

    void setEndVotingTime (LocalTime endVotingTime);
}
