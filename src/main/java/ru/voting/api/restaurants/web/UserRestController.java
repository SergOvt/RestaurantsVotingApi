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

import static ru.voting.api.restaurants.util.ValidationUtil.assureIdConsistent;

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
        log.info("user id={} self get", AuthorizedUser.id());
        return userService.get(AuthorizedUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user){
        log.info("user id={} self update", user.getId());
        assureIdConsistent(user, AuthorizedUser.id());
        User currentUser = userService.get(AuthorizedUser.id());
        user.setEmail(currentUser.getEmail());
        user.setRoles(currentUser.getRoles());
        userService.update(user);
    }

    @DeleteMapping
    public void delete(){
        log.info("user id={} self delete", AuthorizedUser.id());
        userService.delete(AuthorizedUser.id());
    }
}
