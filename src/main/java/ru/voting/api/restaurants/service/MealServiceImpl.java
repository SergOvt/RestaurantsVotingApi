package ru.voting.api.restaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.repository.MealRepository;
import ru.voting.api.restaurants.util.exception.NotFoundException;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Meal> getTodayMenu(int restaurantId) {
        List<Meal> menu = repository.getTodayMenu(restaurantId);
        if (menu.isEmpty()) throw new NotFoundException("Not found menu for restaurant id=" + restaurantId);
        return menu;
    }

    @Override
    public List<Meal> putMenu(List<Meal> menu, int restaurantId) {
        Assert.notNull(menu, "Menu must not be null");
        return repository.putMenu(menu, restaurantId);
    }
}
