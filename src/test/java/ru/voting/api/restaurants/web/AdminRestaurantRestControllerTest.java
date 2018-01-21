package ru.voting.api.restaurants.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.voting.api.restaurants.TestUtil;
import ru.voting.api.restaurants.service.RestaurantService;
import ru.voting.api.restaurants.to.RestaurantTo;
import ru.voting.api.restaurants.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.api.restaurants.TestData.*;
import static ru.voting.api.restaurants.TestUtil.userAuth;

public class AdminRestaurantRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = AdminRestaurantRestController.REST_URL + '/';
    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void testCreate() throws Exception {
        RestaurantTo createdTo = new RestaurantTo(RESTAURANT_NEW.getName());
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isCreated());
        RestaurantTo returned = TestUtil.readFromJson(action, RestaurantTo.class);
        RestaurantTo created = new RestaurantTo(returned.getId(), RESTAURANT_NEW.getName(), 0);
        assertMatch(returned, created);
    }

    @Test
    public void testUpdate() throws Exception {
        RestaurantTo updatedTo = new RestaurantTo("Updated Name");
        mockMvc.perform(put(REST_URL + RESTAURANT_1.getId())
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isOk());
        RestaurantTo updated = new RestaurantTo(RESTAURANT_1);
        updated.setName("Updated Name");
        assertMatch(restaurantService.get(RESTAURANT_1.getId()), updated);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT_1.getId())
                .with(userAuth(ADMIN)))
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.getAll(), RESTAURANT_2, RESTAURANT_3);
    }

    @Test
    public void testCreateConflict() throws Exception {
        RestaurantTo createdTo = new RestaurantTo(RESTAURANT_1.getName());
        mockMvc.perform(post(REST_URL)
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testCreateValidation() throws Exception {
        RestaurantTo createdTo = new RestaurantTo("");
        mockMvc.perform(post(REST_URL)
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateConflict() throws Exception {
        RestaurantTo createdTo = new RestaurantTo(RESTAURANT_2.getName());
        mockMvc.perform(put(REST_URL + RESTAURANT_1.getId())
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testUpdateValidation() throws Exception {
        RestaurantTo createdTo = new RestaurantTo("");
        mockMvc.perform(put(REST_URL + RESTAURANT_1.getId())
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        RestaurantTo createdTo = new RestaurantTo("New");
        mockMvc.perform(put(REST_URL + "10")
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + "10")
                .with(userAuth(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }
}