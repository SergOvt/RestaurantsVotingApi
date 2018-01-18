package ru.voting.api.restaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.voting.api.restaurants.AuthorizedUser;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.repository.UserRepository;
import ru.voting.api.restaurants.to.UserTo;

import java.time.LocalTime;
import java.util.List;

import static ru.voting.api.restaurants.util.ValidationUtil.*;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private LocalTime endVotingTime = LocalTime.of(11,0);
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User get(int id) {
        return checkNotFound(repository.get(id), id);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        checkNew(user);
        return repository.save(user);
    }

    @Override
    @Transactional
    public User update(User user, int id) {
        Assert.notNull(user, "user must not be null");
        get(id); //check NotFound
        user.setId(id);
        return repository.save(user);
    }

    @Override
    @Transactional
    public User update(UserTo userTo, int id) {
        Assert.notNull(userTo, "user must not be null");
        User user = get(id);
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return repository.save(user);
    }

    @Override
    public void delete(int id) {
        checkNotFound(repository.delete(id), id);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        Assert.notNull(email, "email must not be null");
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }

    @Override
    public void vote(User user, int restaurantId) {
        checkVotingAccess(repository.vote(user, restaurantId, endVotingTime));
    }

    @Override
    public void setEndVotingTime(LocalTime endVotingTime) {
        this.endVotingTime = endVotingTime;
    }
}
