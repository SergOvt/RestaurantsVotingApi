package ru.voting.api.restaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.repository.MealRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.voting.api.restaurants.util.ValidationUtil.*;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal get(int id, int restaurantId) {
        return checkNotFound(repository.get(id, restaurantId), id);
    }

    @Override
    public List<Meal> getAll(int restaurantId) {
        return repository.getAll(restaurantId);
    }

    @Override
    public List<Meal> getAllByDate(LocalDate date, int restaurantId) {
        Assert.notNull(date, "date must not be null");
        return repository.getByDate(date, restaurantId);
    }

    @Override
    public Meal add(Meal meal, int restaurantId) {
        Assert.notNull(meal, "meal must not be null");
        return repository.save(meal, restaurantId);
    }

    @Override
    public Meal update(Meal meal, int restaurantId) {
        Assert.notNull(meal, "meal must not be null");
        return checkNotFound(repository.save(meal, restaurantId), meal.getId());
    }

    @Override
    public void delete(int id, int restaurantId) {
        checkNotFound(repository.delete(id, restaurantId), id);
    }
}
