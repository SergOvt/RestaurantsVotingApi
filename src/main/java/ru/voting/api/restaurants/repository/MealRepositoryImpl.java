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
    public List<Meal> getMenuByDate(LocalDate date, int restaurantId) {
        if (em.find(Restaurant.class, restaurantId) == null) return null;
        return em.createNamedQuery("meal.getByDate", Meal.class)
                .setParameter("rest_id", restaurantId)
                .setParameter("date", date)
                .getResultList();
    }

    @Override
    @Transactional
    public List<Meal> putMenu(List<Meal> menu, int restaurantId) {
        Restaurant restaurant = em.find(Restaurant.class, restaurantId);
        if (restaurant == null) return null;
        em.createNamedQuery("meal.delete")
                .setParameter("date", LocalDate.now())
                .setParameter("rest_id", restaurantId)
                .executeUpdate();
        menu.forEach(meal -> {
            meal.setRestaurant(restaurant);
            em.persist(meal);
        });
        return menu;
    }
}
