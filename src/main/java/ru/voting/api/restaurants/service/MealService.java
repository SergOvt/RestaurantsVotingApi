package ru.voting.api.restaurants.service;

import ru.voting.api.restaurants.model.Meal;

import java.util.List;

public interface MealService {

    List<Meal> getTodayMenu(int restaurantId);

    List<Meal> putMenu(List<Meal> menu, int restaurantId);

}
