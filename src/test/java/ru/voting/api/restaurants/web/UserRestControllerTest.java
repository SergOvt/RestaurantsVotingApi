package ru.voting.api.restaurants.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.service.RestaurantService;
import ru.voting.api.restaurants.service.UserService;
import ru.voting.api.restaurants.to.RestaurantTo;
import ru.voting.api.restaurants.to.UserTo;
import ru.voting.api.restaurants.web.json.JsonUtil;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.api.restaurants.TestData.*;
import static ru.voting.api.restaurants.TestUtil.userAuth;

public class UserRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = UserRestController.REST_URL + '/';
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + "profile")
                .with(userAuth(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER_1));
    }

    @Test
    public void testUpdate() throws Exception {
        UserTo userTo = new UserTo(USER_NEW);
        mockMvc.perform(put(REST_URL + "profile")
                .with(userAuth(USER_1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userTo)))
                .andExpect(status().isOk());
        User expected = new User(USER_1);
        expected.setName(userTo.getName());
        expected.setEmail(userTo.getEmail());
        expected.setPassword(userTo.getPassword());
        assertMatch(userService.get(USER_1.getId()), expected);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "profile")
                .with(userAuth(USER_1)))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), USER_2, ADMIN);
    }

    @Test
    public void testNewVote() throws Exception {
        mockMvc.perform(put(REST_URL + "restaurants/" + RESTAURANT_1.getId())
                .with(userAuth(USER_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        RestaurantTo restaurant = restaurantService.get(RESTAURANT_1.getId());
        assertMatch(restaurant.getRating(), RESTAURANT_1.getRating() + 1);
    }

    @Test
    public void testReVote() throws Exception {
        userService.setEndVotingTime(LocalTime.now().plusMinutes(1));
        mockMvc.perform(put(REST_URL + "restaurants/" + RESTAURANT_2.getId())
                .with(userAuth(USER_2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertMatch(restaurantService.get(RESTAURANT_2.getId()).getRating(), RESTAURANT_2.getRating() + 1);
        assertMatch(restaurantService.get(RESTAURANT_1.getId()).getRating(), RESTAURANT_1.getRating() - 1);
    }

    @Test
    public void testGetNotAuthorized() throws Exception {
        mockMvc.perform(get(REST_URL + "profile"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetNotUserAuthorized() throws Exception {
        mockMvc.perform(get(REST_URL + "profile")
                .with(userAuth(ADMIN)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdateConflict() throws Exception {
        UserTo userTo = new UserTo(USER_2);
        mockMvc.perform(put(REST_URL + "profile")
                .with(userAuth(USER_1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userTo)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testUpdateValidation() throws Exception {
        UserTo userTo = new UserTo(USER_1);
        userTo.setPassword("");
        mockMvc.perform(put(REST_URL + "profile")
                .with(userAuth(USER_1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userTo)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testReVoteLocked() throws Exception {
        userService.setEndVotingTime(LocalTime.now().minusMinutes(1));
        mockMvc.perform(put(REST_URL + "restaurants/" + RESTAURANT_2.getId())
                .with(userAuth(USER_2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isLocked());
        assertMatch(restaurantService.get(RESTAURANT_2.getId()).getRating(), RESTAURANT_2.getRating());
        assertMatch(restaurantService.get(RESTAURANT_1.getId()).getRating(), RESTAURANT_1.getRating());
    }

    @Test
    public void testVoteNotFound() throws Exception {
        mockMvc.perform(put(REST_URL + "restaurants/" + 10)
                .with(userAuth(USER_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }
}