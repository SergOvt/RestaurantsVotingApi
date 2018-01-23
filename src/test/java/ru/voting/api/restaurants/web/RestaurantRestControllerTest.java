package ru.voting.api.restaurants.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.voting.api.restaurants.to.MealTo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.api.restaurants.TestData.*;
import static ru.voting.api.restaurants.TestUtil.contentJson;

public class RestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT_1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT_1));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT_1, RESTAURANT_2, RESTAURANT_3));
    }

    @Test
    public void testGetTodayMenu() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT_1.getId() + "/menu"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(new MealTo(MEAL_2), new MealTo(MEAL_3)));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + "100"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testGetTodayMenuNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 100 + "/menu"))
                .andExpect(status().isUnprocessableEntity());
    }
}