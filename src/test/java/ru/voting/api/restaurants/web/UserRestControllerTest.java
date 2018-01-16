package ru.voting.api.restaurants.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.service.UserService;
import ru.voting.api.restaurants.to.UserTo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.api.restaurants.TestData.*;
import static ru.voting.api.restaurants.TestUtil.userAuth;
import static ru.voting.api.restaurants.web.UserRestController.REST_URL;

public class UserRestControllerTest extends AbstractControllerTest{

    @Autowired
    private UserService userService;

    @Test
    public void testGet() throws Exception {
        testGetEntities(REST_URL, USER_1, USER_1);
    }

    @Test
    public void testUpdate() throws Exception {
        UserTo userTo = new UserTo("New Name", "new@email.ru", "password");
        testUpdateEntity(REST_URL, USER_1, userTo);
        User expected = new User(USER_1);
        expected.setName(userTo.getName());
        expected.setEmail(userTo.getEmail());
        expected.setPassword(userTo.getPassword());
        assertMatch(userService.get(USER_1.getId()), expected);
    }

    @Test
    public void testDelete() throws Exception {
        testDeleteEntity(REST_URL, USER_1);
        assertMatch(userService.getAll(), USER_2, ADMIN);
    }

    @Test
    public void testGetNotAuthorized() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetNotUserAuthorized() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userAuth(ADMIN)))
                .andExpect(status().isForbidden());
    }

}