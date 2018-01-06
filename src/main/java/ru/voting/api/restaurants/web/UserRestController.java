package ru.voting.api.restaurants.web;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.voting.api.restaurants.AuthorizedUser;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.service.UserService;

@RestController
@RequestMapping(UserRestController.REST_URL)
public class UserRestController {

    @VisibleForTesting
    static final String REST_URL = "/rest/user/profile";

    private UserService userService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(){
        log.info("User id={} self get", AuthorizedUser.id());
        return userService.get(AuthorizedUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user){
        log.info("User id={} self update", user.getId());
        User currentUser = userService.get(AuthorizedUser.id());
        user.setId(AuthorizedUser.id());
        user.setEmail(currentUser.getEmail());
        user.setRoles(currentUser.getRoles());
        userService.update(user);
    }

    @DeleteMapping
    public void delete(){
        log.info("User id={} self delete", AuthorizedUser.id());
        userService.delete(AuthorizedUser.id());
    }
}
