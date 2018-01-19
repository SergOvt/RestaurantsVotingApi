package ru.voting.api.restaurants.repository;

import org.hibernate.jpa.QueryHints;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.model.Vote;
import ru.voting.api.restaurants.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.time.LocalTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserRepositoryImpl implements UserRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public User get(int id) {
        return em.find(User.class, id);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = em.createNamedQuery("user.byEmail", User.class)
                .setParameter("email", email)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return em.createNamedQuery("user.getAll", User.class).getResultList();
    }

    @Override
    @Transactional
    public User save(User user) {
        if (user.isNew()) {
            em.persist(user);
            return user;
        } else {
            return em.merge(user);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery("user.delete")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public boolean vote(User user, int restaurantId) {
        Restaurant restaurant = em.getReference(Restaurant.class, restaurantId);
        Vote vote = new Vote(restaurant, user);
        try {
            if (user.getVoteId() == null) {
                em.persist(vote);
                return true;
            } else {
                vote.setId(user.getVoteId());
                return em.merge(vote) != null;
            }
        } catch (PersistenceException e) {
            return false;
        }
    }
}
