package ru.voting.api.restaurants.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting.api.restaurants.model.Role;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.service.UserService;

import java.util.Collections;

import static ru.voting.api.restaurants.TestData.*;

public class AdminRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = AdminRestController.REST_URL + '/';
    @Autowired
    private UserService userService;

    @Test
    public void testGet() throws Exception {
        testGetEntities(REST_URL + ADMIN_1.getId(), ADMIN_1);
    }

    @Test
    public void testGetAll() throws Exception {
        testGetEntities(REST_URL, USER_1, USER_2, ADMIN_1, ADMIN_2);
    }

    @Test
    public void testCreate() throws Exception {
        testCreateEntity(REST_URL, new User(USER_NEW), User.class);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER_1);
        updated.setName("Updated Name");
        updated.setRoles(Collections.singleton(Role.ADMIN));
        testUpdateEntity(REST_URL + USER_1.getId(), updated);
        assertMatch(userService.get(USER_1.getId()), updated);
    }

    @Test
    public void testDelete() throws Exception {
        testDeleteEntity(REST_URL + USER_1.getId());
        assertMatch(userService.getAll(), USER_2, ADMIN_1, ADMIN_2);
    }
}