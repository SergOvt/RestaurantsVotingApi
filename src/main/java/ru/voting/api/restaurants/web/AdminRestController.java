package ru.voting.api.restaurants.web;

import com.google.common.annotations.VisibleForTesting;
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

@RestController
@RequestMapping(AdminRestController.REST_URL)
public class AdminRestController {

    @VisibleForTesting
    static final String REST_URL = "/rest/admin/users";

    private UserService userService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id){
        log.info("Admin get user id={}", id);
        return userService.get(id);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll(){
        log.info("Admin get all users");
        return userService.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestBody User user){
        log.info("Admin create new user");
        user.setId(null);
        User created = userService.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user){
        log.info("Admin update user id={}", user.getId());
        userService.update(user);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id){
        log.info("Admin delete user id={}", id);
        userService.delete(id);
    }
}
