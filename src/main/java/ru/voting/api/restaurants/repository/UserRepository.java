package ru.voting.api.restaurants.repository;

import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.model.Vote;

import java.util.List;

public interface UserRepository {

    User get(int id);

    User getByEmail(String email);

    List<User> getAll();

    User save(User user);

    boolean delete(int id);

    void setVote(Vote vote);

    List<Vote> getTodayVotes();

}
