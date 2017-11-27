package ru.voting.api.restaurants.service;

import ru.voting.api.restaurants.model.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealService {

    Meal get(int id, int restaurantId);

    List<Meal> getAll(int restaurantId);

    List<Meal> getAllByDate(LocalDate date, int restaurantId);

    Meal add(Meal meal, int restaurantId);

    Meal update(Meal meal, int restaurantId);

    void delete(int id, int restaurantId);

}
