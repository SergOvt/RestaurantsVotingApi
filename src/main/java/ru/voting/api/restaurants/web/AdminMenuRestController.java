package ru.voting.api.restaurants.web;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.voting.api.restaurants.service.RestaurantService;
import ru.voting.api.restaurants.to.MealTo;
import ru.voting.api.restaurants.util.CollectionValidator;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(AdminMenuRestController.REST_URL)
public class AdminMenuRestController {

    @VisibleForTesting
    static final String REST_URL = "/rest/admin/restaurants";

    private RestaurantService restaurantService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private LocalValidatorFactoryBean validator;

    @Autowired
    public AdminMenuRestController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new CollectionValidator(validator));
    }

    @PutMapping(value = "/{id}/menu", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity putMenu(@Valid @RequestBody List<MealTo> menu, @PathVariable("id") int id) {
        log.info("Admin put new menu for restaurant id={}", id);
        return ResponseEntity.ok(restaurantService.putMenu(menu, id));
    }

}
