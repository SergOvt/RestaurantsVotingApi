package ru.voting.api.restaurants.web;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.voting.api.restaurants.service.RestaurantService;
import ru.voting.api.restaurants.to.RestaurantTo;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(AdminRestaurantRestController.REST_URL)
public class AdminRestaurantRestController {

    @VisibleForTesting
    static final String REST_URL = "/rest/admin/restaurants";

    private RestaurantService restaurantService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public AdminRestaurantRestController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantTo> create(@Valid @RequestBody RestaurantTo restaurantTo) {
        log.info("Admin create new restaurant");
        RestaurantTo created = restaurantService.create(restaurantTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantTo update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable("id") int id) {
        log.info("Admin update restaurant id={}", id);
        return restaurantService.update(restaurantTo, id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("Admin delete restaurant id={}", id);
        restaurantService.delete(id);
    }
}
