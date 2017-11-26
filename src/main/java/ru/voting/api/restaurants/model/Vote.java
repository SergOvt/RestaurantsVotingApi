package ru.voting.api.restaurants.model;

import java.time.LocalDate;

public class Vote extends BaseEntity{

    private LocalDate date;
    private int userId;

    private Restaurant restaurant;

    public Vote() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
