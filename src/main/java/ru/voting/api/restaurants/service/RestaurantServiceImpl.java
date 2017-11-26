package ru.voting.api.restaurants.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.repository.RestaurantRepository;

import java.util.List;

import static ru.voting.api.restaurants.util.ValidationUtil.*;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository repository;

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
    public Restaurant add(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return checkNotFound(repository.add(restaurant), restaurant.getId());
    }

    @Override
    public Restaurant update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return checkNotFound(repository.update(restaurant), restaurant.getId());
    }

    @Override
    public void delete(int id) {
        checkNotFound(repository.delete(id), id);
    }
}
