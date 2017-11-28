package ru.voting.api.restaurants.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;

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
        Meal meal = em.find(Meal.class, id);
        return meal == null || meal.getRestaurant().getId() != restaurantId ? null : meal;
    }

    @Override
    public List<Meal> getAll(int restaurantId) {
        return em.createNamedQuery("meal.getAll", Meal.class)
                .setParameter("rest_id", restaurantId)
                .getResultList();
    }

    @Override
    public List<Meal> getByDate(LocalDate date, int restaurantId) {
        return em.createNamedQuery("meal.getByDate", Meal.class)
                .setParameter("rest_id", restaurantId)
                .setParameter("date", date)
                .getResultList();
    }

    @Override
    @Transactional
    public Meal save(Meal meal, int restaurantId) {
        Restaurant ref = em.getReference(Restaurant.class, restaurantId);
        meal.setRestaurant(ref);
        if (meal.isNew()) {
            em.persist(meal);
        } else {
            if (get(meal.getId(), restaurantId) == null) {
                return null;
            }
            em.merge(meal);
        }
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int restaurantId) {
        return em.createNamedQuery("meal.delete")
                .setParameter("id", id)
                .setParameter("rest_id", restaurantId)
                .executeUpdate() != 0;
    }
}
