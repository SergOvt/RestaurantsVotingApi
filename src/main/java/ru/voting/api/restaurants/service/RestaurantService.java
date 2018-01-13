package ru.voting.api.restaurants.service;

import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.to.RestaurantTo;

import java.time.LocalTime;
import java.util.List;

public interface RestaurantService {

    Restaurant get(int id);

    List<Restaurant> getAll();

    Restaurant create(RestaurantTo restaurantTo);

    Restaurant update(RestaurantTo restaurantTo, int id);

    void delete(int id);

    void vote(int id, int userId);

    List<Meal> getTodayMenu(int id);

    List<Meal> putMenu(List<Meal> menu, int id);

    void setEndVotingTime (LocalTime endVotingTime);

}
