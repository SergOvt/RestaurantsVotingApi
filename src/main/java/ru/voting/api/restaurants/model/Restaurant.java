package ru.voting.api.restaurants.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity{

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
    @NotNull
    private List<Meal> menu;

    @Formula("(SELECT COUNT(*) FROM votes v WHERE v.date = CURDATE() AND v.rest_id = id)")
    private int rating;

    public Restaurant() {
    }

    public Restaurant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Meal> getMenu() {
        return menu;
    }

    public void setMenu(List<Meal> menu) {
        this.menu = menu;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
