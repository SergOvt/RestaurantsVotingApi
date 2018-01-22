package ru.voting.api.restaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.voting.api.restaurants.AuthorizedUser;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.model.Vote;
import ru.voting.api.restaurants.repository.RestaurantRepository;
import ru.voting.api.restaurants.repository.UserRepository;
import ru.voting.api.restaurants.to.UserTo;
import ru.voting.api.restaurants.util.exception.VotingAccessException;

import java.time.LocalTime;
import java.util.List;

import static ru.voting.api.restaurants.util.UserUtil.*;
import static ru.voting.api.restaurants.util.ValidationUtil.*;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private LocalTime endVotingTime = LocalTime.of(11,0);
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RestaurantRepository restaurantRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User get(int id) {
        return checkNotFound(userRepository.get(id), id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        checkNew(user);
        return userRepository.save(prepareToSave(user, passwordEncoder));
    }

    @Override
    @Transactional
    public User update(User user, int id) {
        Assert.notNull(user, "user must not be null");
        get(id); //check NotFound
        user.setId(id);
        return userRepository.save(prepareToSave(user, passwordEncoder));
    }

    @Override
    @Transactional
    public User update(UserTo userTo, int id) {
        Assert.notNull(userTo, "user must not be null");
        User user = updateFromTo(get(id), userTo);
        return userRepository.save(prepareToSave(user, passwordEncoder));
    }

    @Override
    public void delete(int id) {
        checkNotFound(userRepository.delete(id), id);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        Assert.notNull(email, "email must not be null");
        User user = userRepository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }

    @Override
    @Transactional
    public void vote(User user, int restaurantId) {
        Restaurant restaurant = checkNotFound(restaurantRepository.get(restaurantId), restaurantId);
        Vote vote = userRepository.getVote(user);
        if (vote == null) {
            userRepository.setVote(new Vote(restaurant, user));
        } else {
            if (LocalTime.now().isBefore(endVotingTime)) {
                vote.setRestaurant(restaurant);
                userRepository.setVote(vote);
            } else {
                throw new VotingAccessException("Voting time is out");
            }
        }
    }

    @Override
    public void setEndVotingTime(LocalTime endVotingTime) {
        this.endVotingTime = endVotingTime;
    }
}
