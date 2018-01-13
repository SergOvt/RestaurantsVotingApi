package ru.voting.api.restaurants.web;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.service.RestaurantService;

import java.util.List;

import static ru.voting.api.restaurants.util.ErrorsHandler.checkExceptions;


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
    public ResponseEntity get(@PathVariable("id") int id){
        log.info("Get restaurant id={}", id);
        return checkExceptions(() -> ResponseEntity.ok(restaurantService.get(id)));
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll(){
        log.info("Get all restaurants");
        return restaurantService.getAll();
    }

    @GetMapping(value = "/{id}/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTodayMenu(@PathVariable("id") int id) {
        log.info("Get today menu from restaurant id={}", id);
        return checkExceptions(() -> ResponseEntity.ok(restaurantService.getTodayMenu(id)));
    }
}