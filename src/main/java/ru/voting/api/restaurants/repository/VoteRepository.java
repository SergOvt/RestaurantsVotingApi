package ru.voting.api.restaurants.repository;

public interface VoteRepository {

    boolean vote(int userId, int restaurantId);
}
