package ru.voting.api.restaurants.repository;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.model.Vote;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalTime;
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
        return em.createNamedQuery("restaurant.getAll", Restaurant.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        if (restaurant.isNew()) {
            em.persist(restaurant);
            return restaurant;
        } else {
            return em.merge(restaurant);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery("restaurant.delete")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    @Transactional
    public boolean vote(int restaurantId, int userId, LocalTime endVotingTime) {
        List<Vote> votes = em.createNamedQuery("vote.get", Vote.class)
                .setParameter("userId", userId)
                .setParameter("date", LocalDate.now())
                .getResultList();
        Vote vote = DataAccessUtils.singleResult(votes);
        Restaurant restaurant = em.getReference(Restaurant.class, restaurantId);
        if (vote == null) {
            em.persist(new Vote(restaurant, em.getReference(User.class, userId)));
            return true;
        } else {
            if (LocalTime.now().isBefore(endVotingTime)) {
                vote.setRestaurant(restaurant);
                return em.merge(vote) != null;
            }
        }
        return false;
    }
}
