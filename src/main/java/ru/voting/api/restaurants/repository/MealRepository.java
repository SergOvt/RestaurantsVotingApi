package ru.voting.api.restaurants.repository;

import ru.voting.api.restaurants.model.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository {

    Meal get(int id, int restaurantId);

    List<Meal> getAll(int restaurantId);

    List<Meal> getByDate(LocalDate date, int restaurantId);

    Meal save(Meal meal, int restaurantId);

    boolean delete(int id, int restaurantId);

}
