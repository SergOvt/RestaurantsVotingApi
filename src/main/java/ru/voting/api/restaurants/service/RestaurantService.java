package ru.voting.api.restaurants.service;

import ru.voting.api.restaurants.to.MealTo;
import ru.voting.api.restaurants.to.RestaurantTo;

import java.util.List;

public interface RestaurantService {

    RestaurantTo get(int id);

    List<RestaurantTo> getAll();

    RestaurantTo create(RestaurantTo restaurantTo);

    RestaurantTo update(RestaurantTo restaurantTo, int id);

    void delete(int id);

    List<MealTo> getTodayMenu(int id);

    List<MealTo> putMenu(List<MealTo> menuTo, int id);

}
