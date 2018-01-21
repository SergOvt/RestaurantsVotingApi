package ru.voting.api.restaurants.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    private final CrudVoteRepository crudVoteRepository;

    @Autowired
    public RestaurantRepositoryImpl(CrudMealRepository crudMealRepository,
                                    CrudRestaurantRepository crudRestaurantRepository,
                                    CrudVoteRepository crudVoteRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.crudVoteRepository = crudVoteRepository;
    }

    @Override
    @Cacheable("restaurants")
    public Restaurant get(int id) {
        return crudRestaurantRepository.getWithMenu(id);
    }

    @Override
    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return crudRestaurantRepository.getAllWithMenu();
    }

    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant save(Restaurant restaurant) {
        return crudRestaurantRepository.save(restaurant);
    }

    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
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

    @Override
    public int getRating(Restaurant restaurant) {
        return crudVoteRepository.countByRestaurant(restaurant);
    }
}
