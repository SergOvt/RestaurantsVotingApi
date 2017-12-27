package ru.voting.api.restaurants.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.service.UserService;

import java.net.URI;
import java.util.List;

import static ru.voting.api.restaurants.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(AdminUserRestController.REST_URL)
public class AdminUserRestController {

    static final String REST_URL = "/rest/admin/users";

    private UserService userService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public AdminUserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    User get(@PathVariable("id") int id){
        log.info("get user id={}", id);
        return userService.get(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<User> getAll(){
        log.info("get all users");
        return userService.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> create(@RequestBody User user){
        log.info("create new user");
        User created = userService.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    void update(@RequestBody User user, @PathVariable("id") int id){
        log.info("update user id={}", user.getId());
        assureIdConsistent(user, id);
        userService.update(user);
    }

    @DeleteMapping(value = "/{id}")
    void delete(@PathVariable("id") int id){
        log.info("delete user id={}", id);
        userService.delete(id);
    }
}
