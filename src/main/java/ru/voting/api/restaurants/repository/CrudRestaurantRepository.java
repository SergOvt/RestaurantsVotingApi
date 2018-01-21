package ru.voting.api.restaurants.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.api.restaurants.model.Restaurant;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menu m WHERE r.id=?1 AND (m.date=CURDATE() OR m IS NULL)")
    @EntityGraph(attributePaths = {"menu", "votes"}, type = EntityGraph.EntityGraphType.LOAD)
    Restaurant getWithMenu(int id);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menu m WHERE m.date=CURDATE() OR m IS NULL")
    @EntityGraph(attributePaths = {"menu", "votes"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Restaurant> getAllWithMenu();

    @Override
    @Transactional
    Restaurant save(Restaurant restaurant);

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=?1")
    int delete(int id);
}
