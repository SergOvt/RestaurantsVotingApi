package ru.voting.api.restaurants.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting.api.restaurants.model.Role;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.service.UserService;

import java.util.Collections;

import static ru.voting.api.restaurants.TestData.*;
import static ru.voting.api.restaurants.web.UserRestController.REST_URL;

public class UserRestControllerTest extends AbstractControllerTest{

    @Autowired
    private UserService userService;

    @Test
    public void testGet() throws Exception {
        testGetEntities(REST_URL, USER_1);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER_1);
        updated.setName("Updated Name");
        updated.setRoles(Collections.singleton(Role.ADMIN));
        testUpdateEntity(REST_URL, updated);
        assertMatch(userService.get(USER_1.getId()), updated);
    }

    @Test
    public void testDelete() throws Exception {
        testDeleteEntity(REST_URL);
        assertMatch(userService.getAll(), USER_2, ADMIN_1, ADMIN_2);
    }

}