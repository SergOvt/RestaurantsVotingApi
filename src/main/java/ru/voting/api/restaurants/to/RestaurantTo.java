package ru.voting.api.restaurants.to;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class RestaurantTo implements Serializable {

    @NotBlank
    private String name;

    public RestaurantTo() {
    }

    public RestaurantTo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
