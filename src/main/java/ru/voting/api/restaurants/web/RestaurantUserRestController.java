package ru.voting.api.restaurants.web;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.voting.api.restaurants.AuthorizedUser;
import ru.voting.api.restaurants.service.RestaurantService;

import static ru.voting.api.restaurants.util.ErrorsHandler.checkExceptions;


@RestController
@RequestMapping(RestaurantUserRestController.REST_URL)
@Transactional
public class RestaurantUserRestController {

    @VisibleForTesting
    static final String REST_URL = "/rest/user/restaurants";

    private RestaurantService restaurantService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public RestaurantUserRestController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity vote(@PathVariable("id") int restaurantId){
        log.info("User id={} vote for restaurant id={}", AuthorizedUser.id(), restaurantId);
        return checkExceptions(() -> {
            restaurantService.vote(restaurantId, AuthorizedUser.id());
            return ResponseEntity.ok().build();
        });
    }
}
