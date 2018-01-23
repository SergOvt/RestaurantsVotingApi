package ru.voting.api.restaurants.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@Table(name = "votes")
public class Vote extends BaseEntity {

    @Column(name = "date", nullable = false)
    @NotNull(message = "date must not be null")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    @NotNull(message = "user must not be null")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rest_id", nullable = false)
    @NotNull(message = "restaurant must not be null")
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(Restaurant restaurant, User user) {
        this.restaurant = restaurant;
        this.user = user;
        this.date = LocalDate.now();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
