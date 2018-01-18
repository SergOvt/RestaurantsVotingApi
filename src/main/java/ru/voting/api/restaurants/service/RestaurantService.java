package ru.voting.api.restaurants.service;

import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.to.MealTo;
import ru.voting.api.restaurants.to.RestaurantTo;

import java.util.List;

public interface RestaurantService {

    Restaurant get(int id);

    List<Restaurant> getAll();

    Restaurant create(RestaurantTo restaurantTo);

    Restaurant update(RestaurantTo restaurantTo, int id);

    void delete(int id);

    List<MealTo> getTodayMenu(int id);

    List<MealTo> putMenu(List<MealTo> menuTo, int id);

}
