package ru.voting.api.restaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.repository.RestaurantRepository;
import ru.voting.api.restaurants.repository.UserRepository;
import ru.voting.api.restaurants.to.MealTo;
import ru.voting.api.restaurants.to.RestaurantTo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.voting.api.restaurants.util.ValidationUtil.*;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RestaurantTo get(int id) {
        Restaurant restaurant =  checkNotFound(restaurantRepository.get(id), id);
        return new RestaurantTo(id, restaurant.getName(), restaurantRepository.getRating(restaurant));
    }

    @Override
    public List<RestaurantTo> getAll() {
        List<Restaurant> restaurants = restaurantRepository.getAll();
        Map<Integer, Integer> votes = userRepository.getTodayVotes().stream().collect(Collectors.toMap(k ->
                k.getRestaurant().getId(), i -> 1, (i, j) -> i + 1));
        return restaurants.stream()
                .map(restaurant -> new RestaurantTo(restaurant.getId(), restaurant.getName(),
                        votes.getOrDefault(restaurant.getId(), 0)))
                .sorted((o1, o2) -> o2.getRating() - o1.getRating())
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantTo create(RestaurantTo restaurantTo) {
        Assert.notNull(restaurantTo, "restaurant must not be null");
        Restaurant restaurant = restaurantRepository.save(new Restaurant(null, restaurantTo.getName()));
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), 0);
    }

    @Override
    @Transactional
    public RestaurantTo update(RestaurantTo restaurantTo, int id) {
        Assert.notNull(restaurantTo, "restaurant must not be null");
        Restaurant restaurant = checkNotFound(restaurantRepository.get(id), id);
        restaurant.setName(restaurantTo.getName());
        restaurantRepository.save(restaurant);
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurantRepository.getRating(restaurant));
    }

    @Override
    public void delete(int id) {
        checkNotFound(restaurantRepository.delete(id), id);
    }

    @Override
    public List<MealTo> getTodayMenu(int id) {
        Restaurant restaurant = checkNotFound(restaurantRepository.get(id), id);
        return restaurant.getMenu().stream()
                .map(meal -> new MealTo(meal.getTitle(), meal.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<MealTo> putMenu(List<MealTo> menuTo, int id) {
        Assert.notNull(menuTo, "Menu must not be null");
        restaurantRepository.putMenu(menuTo.stream()
                .map(mealTo -> new Meal(mealTo.getTitle(), mealTo.getPrice()))
                .collect(Collectors.toList()), id);
        return menuTo;
    }
}
