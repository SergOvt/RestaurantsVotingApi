package ru.voting.api.restaurants.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.voting.api.restaurants.service.RestaurantService;
import ru.voting.api.restaurants.to.MealTo;
import ru.voting.api.restaurants.web.json.JsonUtil;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.api.restaurants.TestData.*;
import static ru.voting.api.restaurants.TestUtil.userAuth;

public class AdminMenuRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = AdminMenuRestController.REST_URL + '/';
    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void testPutMenu() throws Exception {
        List<MealTo> newMenu = Arrays.asList(new MealTo(MEAL_5), new MealTo(MEAL_6));
        mockMvc.perform(put(REST_URL + RESTAURANT_1.getId() + "/menu")
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andExpect(status().isOk());
        assertMatch(restaurantService.getTodayMenu(RESTAURANT_1.getId()), newMenu);
    }

    @Test
    public void testPutMenuValidation() throws Exception {
        List<MealTo> newMenu = Arrays.asList(new MealTo("", 0), new MealTo(MEAL_1));
        mockMvc.perform(put(REST_URL + RESTAURANT_1.getId() + "/menu")
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutMenuNotFound() throws Exception {
        List<MealTo> newMenu = Arrays.asList(new MealTo(MEAL_1), new MealTo(MEAL_2));
        mockMvc.perform(put(REST_URL + 10 + "/menu")
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andExpect(status().isUnprocessableEntity());
    }
}