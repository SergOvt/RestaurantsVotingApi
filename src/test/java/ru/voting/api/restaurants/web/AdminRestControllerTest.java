package ru.voting.api.restaurants.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import ru.voting.api.restaurants.TestUtil;
import ru.voting.api.restaurants.model.Role;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.service.UserService;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.api.restaurants.TestData.*;
import static ru.voting.api.restaurants.TestUtil.userAuth;

public class AdminRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = AdminRestController.REST_URL + '/';
    @Autowired
    private UserService userService;

    @Test
    public void testGet() throws Exception {
        testGetEntities(REST_URL + USER_1.getId(), ADMIN, USER_1);
    }

    @Test
    public void testGetAll() throws Exception {
        testGetEntities(REST_URL + "/all", ADMIN, USER_1, USER_2, ADMIN);
    }

    @Test
    public void testCreate() throws Exception {
        User created = new User(USER_NEW);
        ResultActions action = testCreateEntity(REST_URL, ADMIN, created);
        User returned = TestUtil.readFromJson(action, User.class);
        created.setId(returned.getId());
        assertMatch(returned, created);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER_1);
        updated.setName("Updated Name");
        updated.setRoles(Collections.singleton(Role.ROLE_ADMIN));
        testUpdateEntity(REST_URL + USER_1.getId(), ADMIN, updated);
        assertMatch(userService.get(USER_1.getId()), updated);
    }

    @Test
    public void testDelete() throws Exception {
        testDeleteEntity(REST_URL + USER_1.getId(), ADMIN);
        assertMatch(userService.getAll(), USER_2, ADMIN);
    }

    @Test
    public void testGetNotAuthorized() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetNotAdminAuthorized() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN.getId())
                .with(userAuth(USER_1)))
                .andExpect(status().isForbidden());
    }
}