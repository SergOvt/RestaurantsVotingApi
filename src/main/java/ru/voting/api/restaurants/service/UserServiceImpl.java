package ru.voting.api.restaurants.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.repository.UserRepository;

import java.util.List;

import static ru.voting.api.restaurants.util.ValidationUtil.*;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User get(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.get(email), "email=" + email);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public User add(User user) {
        Assert.notNull(user, "user must not be null");
        return checkNotFound(repository.add(user), user.getId());
    }

    @Override
    public User update(User user) {
        Assert.notNull(user, "user must not be null");
        return checkNotFound(repository.update(user), user.getId());
    }

    @Override
    public void delete(String email) {
        Assert.notNull(email, "email must not be null");
        checkNotFound(repository.delete(email), "email=" + email);
    }

    @Override
    public boolean setVote(int restaurantId, int userId) {
        return repository.setVote(restaurantId, userId);
    }
}
