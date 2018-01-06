package ru.voting.api.restaurants.web;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.service.MealService;
import ru.voting.api.restaurants.service.RestaurantService;
import ru.voting.api.restaurants.util.exception.NotFoundException;

import java.util.List;

@RestController
@RequestMapping(RestaurantRestController.REST_URL)
public class RestaurantRestController {

    @VisibleForTesting
    static final String REST_URL = "/rest/restaurants";

    private RestaurantService restaurantService;
    private MealService mealService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public RestaurantRestController(RestaurantService restaurantService, MealService mealService) {
        this.restaurantService = restaurantService;
        this.mealService = mealService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(@PathVariable("id") int id){
        log.info("Get restaurant id={}", id);
        Restaurant restaurant = null;
        try {
            restaurant = restaurantService.get(id);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.TEXT_HTML)
                    .body("Restaurant not found");
        }
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll(){
        log.info("Get all restaurants");
        return restaurantService.getAll();
    }

    @GetMapping(value = "/{id}/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTodayMenu(@PathVariable("id") int id) {
        log.info("Get today menu from restaurant id={}", id);
        List<Meal> menu = null;
        try {
            menu = mealService.getTodayMenu(id);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.TEXT_HTML)
                    .body("Menu not found");
        }
        return ResponseEntity.ok(menu);
    }
}
