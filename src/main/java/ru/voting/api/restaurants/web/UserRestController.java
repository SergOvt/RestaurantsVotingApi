package ru.voting.api.restaurants.web;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.voting.api.restaurants.AuthorizedUser;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.service.UserService;
import ru.voting.api.restaurants.to.UserTo;

import javax.validation.Valid;

@RestController
@RequestMapping(UserRestController.REST_URL)
public class UserRestController {

    @VisibleForTesting
    static final String REST_URL = "/rest/user";

    private UserService userService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        log.info("User id={} self get", AuthorizedUser.id());
        return AuthorizedUser.get().getUser();
    }

    @PutMapping(value = "/profile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User update(@Valid @RequestBody UserTo user) {
        log.info("User id={} self update", AuthorizedUser.id());
        return userService.update(user, AuthorizedUser.id());
    }

    @DeleteMapping(value = "/profile")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete() {
        log.info("User id={} self delete", AuthorizedUser.id());
        userService.delete(AuthorizedUser.id());
    }

    @PutMapping(value = "/restaurants/{id}")
    public void vote(@PathVariable("id") int restaurantId) {
        log.info("User id={} setVote for restaurant id={}", AuthorizedUser.id(), restaurantId);
        userService.vote(AuthorizedUser.get().getUser(), restaurantId);
    }
}
