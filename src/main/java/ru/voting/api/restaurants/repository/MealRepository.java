package ru.voting.api.restaurants.repository;

import ru.voting.api.restaurants.model.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository {

    List<Meal> getTodayMenu(int restaurantId);

    List<Meal> getMenuByDate(LocalDate date, int restaurantId);

    List<Meal> putMenu(List<Meal> menu, int restaurantId);
}
