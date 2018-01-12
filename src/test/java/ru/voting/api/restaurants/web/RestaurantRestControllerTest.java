package ru.voting.api.restaurants.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.voting.api.restaurants.model.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.api.restaurants.TestData.*;
import static ru.voting.api.restaurants.TestUtil.userAuth;

public class RestaurantRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = RestaurantRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        testGetEntities(REST_URL + RESTAURANT_1.getId(), RESTAURANT_1);
    }

    @Test
    public void testGetAll() throws Exception {
        testGetEntities(REST_URL + "/all", RESTAURANT_1, RESTAURANT_2, RESTAURANT_3);
    }

    @Test
    public void testGetTodayMenu() throws Exception {
        testGetEntities(REST_URL + RESTAURANT_1.getId() + "/menu", MEAL_2, MEAL_3);
    }

    protected <T> void testGetEntities(String restUrl, T... objects) throws Exception {
        mockMvc.perform(get(restUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(objects.length == 1 ? objects[0] : objects));
    }
}