package ru.voting.api.restaurants.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.service.RestaurantService;

import java.util.List;

@Controller
public class AdminRestaurantController {

    private RestaurantService restaurantService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public AdminRestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    public Restaurant get(int id){
        log.info("get restaurant id={}", id);
        return restaurantService.get(id);
    }

    public List<Restaurant> getAll(){
        log.info("get all restaurants");
        return restaurantService.getAll();
    }

    public Restaurant add(Restaurant restaurant){
        log.info("add new restaurant");
        return restaurantService.add(restaurant);
    }

    public void update(Restaurant restaurant){
        log.info("update restaurant id={}", restaurant.getId());
        restaurantService.update(restaurant);
    }

    public void delete(int id){
        log.info("delete restaurant id={}", id);
        restaurantService.delete(id);
    }
}
