package ru.voting.api.restaurants.web;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.service.MealService;
import ru.voting.api.restaurants.service.RestaurantService;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(RestaurantAdminRestController.REST_URL)
public class RestaurantAdminRestController {

    @VisibleForTesting
    static final String REST_URL = "/rest/admin/restaurants";

    private RestaurantService restaurantService;
    private MealService mealService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public RestaurantAdminRestController(RestaurantService restaurantService, MealService mealService) {
        this.restaurantService = restaurantService;
        this.mealService = mealService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody Restaurant restaurant){
        log.info("Admin create new restaurant");
        restaurant.setId(null);
        restaurant.setRating(0);
        Restaurant created = null;
        try {
            created = restaurantService.create(restaurant);
        } catch (PersistenceException | HttpMessageNotReadableException | ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.TEXT_HTML)
                    .body("Incorrect data in request body");
        }
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Restaurant restaurant){
        log.info("Admin update restaurant id={}", restaurant.getId());
        restaurantService.update(restaurant);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id){
        log.info("Admin delete restaurant id={}", id);
        restaurantService.delete(id);
    }

    @PutMapping(value = "/{id}/menu", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Meal>> putMenu(@RequestBody List<Meal> menu, @PathVariable("id") int id) {
        log.info("Admin put new menu for restaurant id={}", id);
        List<Meal> newMenu = mealService.putMenu(menu, id);
        return ResponseEntity.ok(newMenu);
    }
}
