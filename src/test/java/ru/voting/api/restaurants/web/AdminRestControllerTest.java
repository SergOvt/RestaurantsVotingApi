package ru.voting.api.restaurants.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.api.restaurants.model.Role;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.service.UserService;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.api.restaurants.TestData.*;
import static ru.voting.api.restaurants.TestUtil.*;

public class AdminRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + '/';
    @Autowired
    private UserService userService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + USER_1.getId())
                .with(userAuth(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(contentJson(USER_1));

    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userAuth(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER_1, USER_2, ADMIN));
    }

    @Test
    public void testCreate() throws Exception {
        User created = new User(USER_NEW);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(created)))
                .andExpect(status().isCreated());
        User returned = readValue(action, User.class);
        created.setId(returned.getId());
        assertMatch(returned, created);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER_1);
        updated.setName("Updated Name");
        updated.setRoles(Collections.singleton(Role.ROLE_ADMIN));
        mockMvc.perform(put(REST_URL + USER_1.getId())
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andExpect(status().isOk());
        assertMatch(userService.get(USER_1.getId()), updated);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER_1.getId())
                .with(userAuth(ADMIN)))
                .andExpect(status().isNoContent());
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

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + "100")
                .with(userAuth(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testCreateConflict() throws Exception {
        User user = new User(USER_NEW);
        user.setEmail(USER_1.getEmail());
        mockMvc.perform(post(REST_URL)
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(user)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testCreateValidation() throws Exception {
        User user = new User(USER_NEW);
        user.setPassword("");
        mockMvc.perform(post(REST_URL)
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testUpdateConflict() throws Exception {
        User user = new User(USER_1);
        user.setEmail(USER_2.getEmail());
        mockMvc.perform(put(REST_URL + USER_1.getId())
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(user)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testUpdateValidation() throws Exception {
        User user = new User(USER_1);
        user.setPassword("");
        mockMvc.perform(put(REST_URL + USER_1.getId())
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        User updated = new User(USER_1);
        updated.setEmail("Updated@email.com");
        mockMvc.perform(put(REST_URL + 10)
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + "10")
                .with(userAuth(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }
}