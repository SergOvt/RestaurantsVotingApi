package ru.voting.api.restaurants.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.util.exception.NotFoundException;

import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository{

    private final CrudMealRepository crudMealRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    @Autowired
    public RestaurantRepositoryImpl(CrudMealRepository crudMealRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public Restaurant get(int id) {
        return crudRestaurantRepository.getWithMenu(id);
    }

    @Override
    public List<Restaurant> getAll() {
        return crudRestaurantRepository.getAllWithMenu();
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return crudRestaurantRepository.save(restaurant);
    }

    @Override
    public boolean delete(int id) {
        return crudRestaurantRepository.delete(id) != 0;
    }

    @Override
    @Transactional
    public List<Meal> putMenu(List<Meal> menu, int id) {
        Restaurant restaurant = crudRestaurantRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found entity with id=" + id));
        crudMealRepository.deleteInBatch(restaurant.getMenu());
        menu.forEach(meal -> meal.setRestaurant(restaurant));
        return crudMealRepository.saveAll(menu);

    }
}
