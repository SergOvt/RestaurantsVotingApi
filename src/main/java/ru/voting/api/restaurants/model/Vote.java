package ru.voting.api.restaurants.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = "vote.get", query = "SELECT v FROM Vote v WHERE v.userId=:userId AND v.date=:date")
})
@Entity
@Table(name = "votes")
public class Vote extends BaseEntity{

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "user_id", nullable = false)
    @Min(0)
    private int userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rest_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(int userId) {
        this.userId = userId;
        this.date = LocalDate.now();
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
