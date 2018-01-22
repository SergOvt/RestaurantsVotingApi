package ru.voting.api.restaurants.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Override
    @Transactional
    Vote save(Vote vote);

    List<Vote> getAllByDate(LocalDate date);

    int countByRestaurantAndDate(Restaurant restaurant, LocalDate date);

}
