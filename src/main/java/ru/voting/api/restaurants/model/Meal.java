package ru.voting.api.restaurants.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = "meal.delete", query = "DELETE FROM Meal m WHERE m.date=:date AND m.restaurant.id=:rest_id"),
        @NamedQuery(name = "meal.getByDate", query = "SELECT m FROM Meal m WHERE m.date=:date AND m.restaurant.id=:rest_id"),
})
@Entity
@Table(name = "meals")
public class Meal extends BaseEntity {

    @Column(name = "title", nullable = false)
    @NotBlank
    private String title;

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 1000000)
    private int price;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rest_id", nullable = false)
    @NotNull
    @JsonIgnore
    private Restaurant restaurant;

    public Meal() {
    }

    public Meal(String title, int price) {
        this.title = title;
        this.price = price;
        this.date = LocalDate.now();
    }

    public Meal(Integer id, String title, int price, LocalDate date) {
        this(title, price);
        this.id = id;
        this.date = date;
    }

    public Meal(Meal meal) {
        this(meal.getId(), meal.getTitle(), meal.getPrice(), meal.getDate());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
