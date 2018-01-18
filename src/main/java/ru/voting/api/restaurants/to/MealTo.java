package ru.voting.api.restaurants.to;

import org.hibernate.validator.constraints.Range;
import ru.voting.api.restaurants.model.Meal;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class MealTo implements Serializable {

    @Column(name = "title", nullable = false)
    @NotBlank(message = "title mast not be empty")
    private String title;

    @Column(name = "price", nullable = false)
    @Range(min = 0, max = 1000000, message = "price must be between 0 and 1 000 000")
    private int price;

    public MealTo() {
    }

    public MealTo(String title, int price) {
        this.title = title;
        this.price = price;
    }

    public MealTo (Meal meal) {
        this(meal.getTitle(), meal.getPrice());
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

}
