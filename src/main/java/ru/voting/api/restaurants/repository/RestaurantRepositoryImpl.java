package ru.voting.api.restaurants.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.api.restaurants.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RestaurantRepositoryImpl implements RestaurantRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Restaurant get(int id) {
        return em.find(Restaurant.class, id);
    }

    @Override
    public List<Restaurant> getAll() {
        return null;
    }

    @Override
    @Transactional
    public Restaurant add(Restaurant restaurant) {
        return null;
    }

    @Override
    @Transactional
    public Restaurant update(Restaurant restaurant) {
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return false;
    }
}
