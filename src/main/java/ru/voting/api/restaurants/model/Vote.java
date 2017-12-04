package ru.voting.api.restaurants.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = "vote.get", query = "SELECT v FROM Vote v WHERE v.userEmail=:userEmail AND v.date=:date")
})
@Entity
@Table(name = "votes")
public class Vote extends BaseEntity{

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "user_email", nullable = false)
    @NotBlank
    @Email
    private String userEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rest_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(String userEmail) {
        this.userEmail = userEmail;
        this.date = LocalDate.now();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userId) {
        this.userEmail = userId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
