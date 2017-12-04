package ru.voting.api.restaurants.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.service.UserService;

@Controller
public class ProfileUserController {

    private UserService userService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public ProfileUserController(UserService userService) {
        this.userService = userService;
    }

    User get(int id){
        log.info("get user id={}", id);
        return userService.get(id);
    }

    void update(User user){
        log.info("update user id={}", user.getId());
        userService.update(user);
    }

    void delete(int id){
        log.info("delete user id={}", id);
        userService.delete(id);
    }
}
