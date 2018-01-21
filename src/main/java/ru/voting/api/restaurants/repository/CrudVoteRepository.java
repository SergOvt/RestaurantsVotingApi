package ru.voting.api.restaurants.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.model.Vote;

import java.time.LocalDate;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    int countByRestaurant(Restaurant restaurant);

    @Override
    @Transactional
    Vote save(Vote vote);

    Vote findByUserAndDate(User user, LocalDate date);

}
