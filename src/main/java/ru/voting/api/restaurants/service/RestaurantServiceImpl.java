package ru.voting.api.restaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.repository.RestaurantRepository;

import java.util.List;

import static ru.voting.api.restaurants.util.ValidationUtil.*;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository repository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public Restaurant get(int id) {
        return checkNotFound(repository.get(id), id);
    }

    @Override
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Override
    public Restaurant update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return checkNotFound(repository.save(restaurant), restaurant.getId());
    }

    @Override
    public void delete(int id) {
        checkNotFound(repository.delete(id), id);
    }

    @Override
    public void vote(int id, String userEmail) {
        checkNotFound(repository.vote(id, userEmail), "vote");
    }

}
