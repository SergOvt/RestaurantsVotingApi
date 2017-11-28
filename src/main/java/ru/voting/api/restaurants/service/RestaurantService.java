package ru.voting.api.restaurants.service;

import ru.voting.api.restaurants.model.Restaurant;

import java.util.List;

public interface RestaurantService {

    Restaurant get(int id);

    List<Restaurant> getAll();

    Restaurant add(Restaurant restaurant);

    Restaurant update(Restaurant restaurant);

    void delete(int id);

    void setVote(int id, int userId);

}
