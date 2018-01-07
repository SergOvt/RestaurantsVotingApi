package ru.voting.api.restaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.repository.MealRepository;
import ru.voting.api.restaurants.repository.RestaurantRepository;
import ru.voting.api.restaurants.util.exception.NotFoundException;

import java.time.LocalTime;
import java.util.List;

import static ru.voting.api.restaurants.util.ValidationUtil.*;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    private LocalTime endVotingTime = LocalTime.of(11,0);

    private final RestaurantRepository restaurantRepository;
    private final MealRepository mealRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, MealRepository mealRepository) {
        this.restaurantRepository = restaurantRepository;
        this.mealRepository = mealRepository;
    }

    @Override
    public Restaurant get(int id) {
        return checkNotFound(restaurantRepository.get(id), id);
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        restaurant.setId(null);
        restaurant.setRating(0);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return checkNotFound(restaurantRepository.save(restaurant), restaurant.getId());
    }

    @Override
    public void delete(int id) {
        checkNotFound(restaurantRepository.delete(id), id);
    }

    @Override
    public void vote(int restaurantId, int userId) {
        checkVotingAccess(restaurantRepository.vote(restaurantId, userId, endVotingTime));
    }

    @Override
    public List<Meal> getTodayMenu(int id) {
        List<Meal> menu = mealRepository.getTodayMenu(id);
        if (menu.isEmpty()) throw new NotFoundException("Not found menu for restaurant id=" + id);
        return menu;
    }

    @Override
    public List<Meal> putMenu(List<Meal> menu, int id) {
        Assert.notNull(menu, "Menu must not be null");
        return checkNotFound(mealRepository.putMenu(menu, id), id);
    }

    @Override
    public void setEndVotingTime(LocalTime endVotingTime) {
        this.endVotingTime = endVotingTime;
    }
}
