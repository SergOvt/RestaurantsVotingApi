package ru.voting.api.restaurants.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.api.restaurants.model.Restaurant;
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
    public boolean vote(int id, String userEmail, LocalTime endVotingTime) {
        List<Vote> votes = em.createNamedQuery("vote.get", Vote.class)
                .setParameter("userEmail", userEmail)
                .setParameter("date", LocalDate.now())
                .getResultList();
        if (votes.size() == 0) {
            Vote vote = new Vote(userEmail);
            vote.setRestaurant(em.getReference(Restaurant.class, id));
            em.persist(vote);
            return true;
        } else {
            if (LocalTime.now().isBefore(endVotingTime)) {
                Vote vote = votes.get(0);
                vote.setRestaurant(em.getReference(Restaurant.class, id));
                return em.merge(vote) != null;
            }
        }
        return false;
    }
}
