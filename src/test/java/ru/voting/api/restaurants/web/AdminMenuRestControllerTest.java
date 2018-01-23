package ru.voting.api.restaurants.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.api.restaurants.service.RestaurantService;
import ru.voting.api.restaurants.to.MealTo;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.api.restaurants.TestData.*;
import static ru.voting.api.restaurants.TestUtil.*;

public class AdminMenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminMenuRestController.REST_URL + '/';

    @Autowired
    private RestaurantService restaurantService;

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testPutMenu() throws Exception {
        List<MealTo> newMenu = Arrays.asList(new MealTo(MEAL_5), new MealTo(MEAL_6));
        mockMvc.perform(put(REST_URL + RESTAURANT_1.getId() + "/menu")
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newMenu)))
                .andExpect(status().isOk());
        assertMatch(restaurantService.getTodayMenu(RESTAURANT_1.getId()), newMenu);
        // No Rollback
        restaurantService.putMenu(Arrays.asList(new MealTo(MEAL_2), new MealTo(MEAL_3)),
                RESTAURANT_1.getId());
    }

    @Test
    public void testPutMenuValidation() throws Exception {
        List<MealTo> newMenu = Arrays.asList(new MealTo("", 0), new MealTo(MEAL_1));
        mockMvc.perform(put(REST_URL + RESTAURANT_1.getId() + "/menu")
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newMenu)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutMenuNotFound() throws Exception {
        List<MealTo> newMenu = Arrays.asList(new MealTo(MEAL_1), new MealTo(MEAL_2));
        mockMvc.perform(put(REST_URL + 10 + "/menu")
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newMenu)))
                .andExpect(status().isUnprocessableEntity());
    }
}