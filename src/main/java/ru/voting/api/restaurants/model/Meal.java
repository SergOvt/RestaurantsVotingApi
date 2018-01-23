package ru.voting.api.restaurants.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "meals")
public class Meal extends BaseEntity {

    @Column(name = "title", nullable = false)
    @NotBlank(message = "title mast not be empty")
    private String title;

    @Column(name = "price", nullable = false)
    @Range(min = 0, max = 1000000, message = "price must be between 0 and 1 000 000")
    private int price;

    @Column(name = "date", nullable = false)
    @NotNull(message = "date must not be null")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rest_id", nullable = false)
    @NotNull(message = "restaurant must not be null")
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
