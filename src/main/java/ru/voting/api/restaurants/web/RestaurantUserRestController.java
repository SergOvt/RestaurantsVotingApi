package ru.voting.api.restaurants.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.voting.api.restaurants.AuthorizedUser;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.service.MealService;
import ru.voting.api.restaurants.service.RestaurantService;
import ru.voting.api.restaurants.service.UserService;

import java.util.List;

@RestController
@RequestMapping(RestaurantUserRestController.REST_URL)
@Transactional
public class RestaurantUserRestController {

    static final String REST_URL = "/rest/user/restaurants";

    private RestaurantService restaurantService;
    private MealService mealService;
    private UserService userService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public RestaurantUserRestController(RestaurantService restaurantService, MealService mealService, UserService userService) {
        this.restaurantService = restaurantService;
        this.mealService = mealService;
        this.userService = userService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id){
        log.info("user get restaurant id={}", id);
        return restaurantService.get(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll(){
        log.info("user get all restaurants");
        return restaurantService.getAll();
    }

    @GetMapping(value = "/{id}/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Meal> getTodayMenu(@PathVariable("id") int id) {
        log.info("user get today menu from restaurant id={}", id);
        return mealService.getTodayMenu(id);
    }

    @PutMapping(value = "/{id}/vote")
    public void vote(@PathVariable("id") int id){
        String email = userService.get(AuthorizedUser.id()).getEmail();
        log.info("user email={} vote for restaurant id={}", email, id);
        restaurantService.vote(id, email);
    }
}
