package ru.voting.api.restaurants.repository;

import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    Restaurant get(int id);

    List<Restaurant> getAll();

    Restaurant save(Restaurant restaurant);

    boolean delete(int id);

    List<Meal> putMenu(List<Meal> menu, int id);

}
