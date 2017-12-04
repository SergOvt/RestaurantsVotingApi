package ru.voting.api.restaurants.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NamedQueries({
        @NamedQuery(name = "restaurant.delete", query = "DELETE FROM Restaurant r WHERE r.id=:id"),
        @NamedQuery(name = "restaurant.getAll", query = "SELECT r FROM Restaurant r ORDER BY r.rating DESC")
})
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

    public Restaurant(String name) {
        this.name = name;
    }

    public Restaurant(int id, String name, int rating) {
        this(name);
        this.id = id;
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
