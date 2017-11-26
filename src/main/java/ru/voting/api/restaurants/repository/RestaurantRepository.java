package ru.voting.api.restaurants.repository;

import ru.voting.api.restaurants.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    Restaurant get(int id);

    List<Restaurant> getAll();

    Restaurant add(Restaurant restaurant);

    Restaurant update(Restaurant restaurant);

    boolean delete(int id);

}
