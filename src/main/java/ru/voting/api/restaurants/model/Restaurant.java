package ru.voting.api.restaurants.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity{

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @Formula("(SELECT COUNT(*) FROM votes v WHERE v.date = CURDATE() AND v.rest_id = id)")
    private int rating;

    public Restaurant() {
    }

    public Restaurant(int id, String name, int rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
