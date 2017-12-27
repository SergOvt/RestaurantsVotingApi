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
    public List<Meal> getTodayMenu(int restaurantId) {
        return getMenuByDate(LocalDate.now(), restaurantId);
    }

    @Override
    public List<Meal> getMenuByDate(LocalDate date, int restaurantId) {
        return em.createNamedQuery("meal.getByDate", Meal.class)
                .setParameter("rest_id", restaurantId)
                .setParameter("date", date)
                .getResultList();
    }

    @Override
    @Transactional
    public List<Meal> putMenu(List<Meal> menu, int restaurantId) {
        em.createNamedQuery("meal.delete")
                .setParameter("date", LocalDate.now())
                .setParameter("rest_id", restaurantId)
                .executeUpdate();
        menu.forEach(meal -> {
            meal.setRestaurant(em.getReference(Restaurant.class, restaurantId));
            meal.setId(null);
            em.persist(meal);
        });
        return menu;
    }
}
