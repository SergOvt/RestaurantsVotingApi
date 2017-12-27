package ru.voting.api.restaurants.web;

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

    static final String REST_URL = "/rest/user";

    private UserService userService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    User get(){
        log.info("get user id={}", AuthorizedUser.id());
        return userService.get(AuthorizedUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    void update(@RequestBody User user){
        log.info("update user id={}", user.getId());
        assureIdConsistent(user, AuthorizedUser.id());
        userService.update(user);
    }

    @DeleteMapping
    void delete(){
        log.info("delete user id={}", AuthorizedUser.id());
        userService.delete(AuthorizedUser.id());
    }
}
