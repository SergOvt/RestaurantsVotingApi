package ru.voting.api.restaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.repository.UserRepository;

import java.util.List;

import static ru.voting.api.restaurants.util.ValidationUtil.*;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User get(int id) {
        return checkNotFound(repository.get(id), "id=" + id);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        user.setId(null);
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        Assert.notNull(user, "user must not be null");
        return checkNotFound(repository.save(user), user.getId());
    }

    @Override
    public void delete(int id) {
        checkNotFound(repository.delete(id), "email=" + id);
    }

}
