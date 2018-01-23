package ru.voting.api.restaurants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.voting.api.restaurants.model.Meal;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
}
