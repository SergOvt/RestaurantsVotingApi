package ru.voting.api.restaurants.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;


public class RestaurantTo {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotBlank(message = "name mast not be empty")
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int rating = 0;

    public RestaurantTo() {
    }

    public RestaurantTo(String name) {
        this.name = name;
    }

    public RestaurantTo(Integer id, String name, int rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    public RestaurantTo(RestaurantTo restaurantTo){
        this(restaurantTo.getId(), restaurantTo.getName(), restaurantTo.getRating());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                '}';
    }
}
