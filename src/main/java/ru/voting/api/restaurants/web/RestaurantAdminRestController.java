package ru.voting.api.restaurants.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.service.MealService;
import ru.voting.api.restaurants.service.RestaurantService;

import java.net.URI;
import java.util.List;

import static ru.voting.api.restaurants.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(RestaurantAdminRestController.REST_URL)
public class RestaurantAdminRestController {

    static final String REST_URL = "/rest/admin/restaurants";

    private RestaurantService restaurantService;
    private MealService mealService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public RestaurantAdminRestController(RestaurantService restaurantService, MealService mealService) {
        this.restaurantService = restaurantService;
        this.mealService = mealService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id){
        log.info("admin get restaurant id={}", id);
        return restaurantService.get(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll(){
        log.info("admin get all restaurants");
        return restaurantService.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant){
        log.info("admin create new restaurant");
        Restaurant created = restaurantService.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Restaurant restaurant, @PathVariable("id") int id){
        log.info("admin update restaurant id={}", restaurant.getId());
        assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id){
        log.info("admin delete restaurant id={}", id);
        restaurantService.delete(id);
    }

    @GetMapping(value = "/{id}/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Meal> getTodayMenu(@PathVariable("id") int id) {
        log.info("admin get today menu from restaurant id={}", id);
        return mealService.getTodayMenu(id);
    }

    @PutMapping(value = "/{id}/menu", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Meal>> putMenu(@RequestBody List<Meal> menu, @PathVariable("id") int id) {
        log.info("admin put new menu for restaurant id={}", id);
        List<Meal> newMenu = mealService.putMenu(menu, id);
        return ResponseEntity.ok(newMenu);
    }
}
