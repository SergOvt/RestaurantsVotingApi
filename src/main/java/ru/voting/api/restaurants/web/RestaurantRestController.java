package ru.voting.api.restaurants.web;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.service.RestaurantService;
import ru.voting.api.restaurants.to.MealTo;

import java.util.List;

@RestController
@RequestMapping(RestaurantRestController.REST_URL)
public class RestaurantRestController {

    @VisibleForTesting
    static final String REST_URL = "/rest/restaurants";

    private RestaurantService restaurantService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public RestaurantRestController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id){
        log.info("Get restaurant id={}", id);
        return restaurantService.get(id);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll(){
        log.info("Get all restaurants");
        return restaurantService.getAll();
    }

    @GetMapping(value = "/{id}/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getTodayMenu(@PathVariable("id") int id) {
        log.info("Get today menu from restaurant id={}", id);
        return restaurantService.getTodayMenu(id);
    }
}
