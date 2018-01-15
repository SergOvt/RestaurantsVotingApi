package ru.voting.api.restaurants.web;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.service.RestaurantService;
import ru.voting.api.restaurants.to.RestaurantTo;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(RestaurantAdminRestController.REST_URL)
public class RestaurantAdminRestController {

    @VisibleForTesting
    static final String REST_URL = "/rest/admin/restaurants";

    private RestaurantService restaurantService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public RestaurantAdminRestController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody RestaurantTo restaurantTo) {
        log.info("Admin create new restaurant");
        Restaurant created = restaurantService.create(restaurantTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody RestaurantTo restaurantTo, @PathVariable("id") int id) {
        log.info("Admin update restaurant id={}", id);
        return ResponseEntity.ok(restaurantService.update(restaurantTo, id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        log.info("Admin delete restaurant id={}", id);
        restaurantService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/menu", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity putMenu(@RequestBody List<Meal> menu, @PathVariable("id") int id) {
        log.info("Admin put new menu for restaurant id={}", id);
        return ResponseEntity.ok(restaurantService.putMenu(menu, id));
    }
}
