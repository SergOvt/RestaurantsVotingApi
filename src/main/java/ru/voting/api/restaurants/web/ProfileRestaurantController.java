package ru.voting.api.restaurants.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.service.MealService;
import ru.voting.api.restaurants.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ProfileRestaurantController {

    private RestaurantService restaurantService;
    private MealService mealService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public ProfileRestaurantController(RestaurantService restaurantService, MealService mealService) {
        this.restaurantService = restaurantService;
        this.mealService = mealService;
    }

    public Restaurant get(int id){
        log.info("get restaurant id={}", id);
        return restaurantService.get(id);
    }

    public List<Restaurant> getAll(){
        log.info("get all restaurants");
        return restaurantService.getAll();
    }

    List<Meal> getMenu(int id) {
        log.info("get menu from restaurant id={}", id);
        return mealService.getAllByDate(LocalDate.now(), id);
    }

    void setVote(int id, String userEmail){
        log.info("user email={} vote for restaurant id={}", userEmail, id);
        restaurantService.setVote(id, userEmail);
    }
}
