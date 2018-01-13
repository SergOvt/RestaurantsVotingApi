package ru.voting.api.restaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.repository.MealRepository;
import ru.voting.api.restaurants.repository.RestaurantRepository;
import ru.voting.api.restaurants.to.RestaurantTo;
import ru.voting.api.restaurants.util.ValidationUtil;

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
    public Restaurant create(RestaurantTo restaurantTo) {
        Assert.notNull(restaurantTo, "restaurant must not be null");
        return restaurantRepository.save(new Restaurant(null, restaurantTo.getName(), 0));
    }

    @Override
    @Transactional
    public Restaurant update(RestaurantTo restaurantTo, int id) {
        Assert.notNull(restaurantTo, "restaurant must not be null");
        Restaurant restaurant = get(id);
        restaurant.setName(restaurantTo.getName());
        return checkNotFound(restaurantRepository.save(restaurant), id);
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
        return checkNotFound(mealRepository.getTodayMenu(id), "Not found menu for restaurant id=" + id);
    }

    @Override
    public List<Meal> putMenu(List<Meal> menu, int id) {
        Assert.notNull(menu, "Menu must not be null");
        menu.forEach(ValidationUtil::checkNew);
        return checkNotFound(mealRepository.putMenu(menu, id), id);
    }

    @Override
    public void setEndVotingTime(LocalTime endVotingTime) {
        this.endVotingTime = endVotingTime;
    }
}
