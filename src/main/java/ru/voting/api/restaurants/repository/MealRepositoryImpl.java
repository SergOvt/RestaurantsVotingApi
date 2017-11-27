package ru.voting.api.restaurants.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.api.restaurants.model.Meal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class MealRepositoryImpl implements MealRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Meal get(int id, int restaurantId) {
        return null;
    }

    @Override
    public List<Meal> getAll(int restaurantId) {
        return null;
    }

    @Override
    public List<Meal> getByDate(LocalDate date, int restaurantId) {
        return null;
    }

    @Override
    @Transactional
    public Meal add(Meal meal, int restaurantId) {
        return null;
    }

    @Override
    @Transactional
    public Meal update(Meal meal, int restaurantId) {
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int userId, int restaurantId) {
        return false;
    }
}
