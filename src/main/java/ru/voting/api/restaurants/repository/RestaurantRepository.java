package ru.voting.api.restaurants.repository;

import ru.voting.api.restaurants.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    Restaurant get(int id);

    List<Restaurant> getAll();

    Restaurant save(Restaurant restaurant);

    boolean delete(int id);

    boolean setVote(int userId, int restaurantId);

}
