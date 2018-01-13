package ru.voting.api.restaurants.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import ru.voting.api.restaurants.TestUtil;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.service.RestaurantService;
import ru.voting.api.restaurants.to.RestaurantTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.voting.api.restaurants.TestData.*;

public class RestaurantAdminRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = RestaurantAdminRestController.REST_URL + '/';
    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void testCreate() throws Exception {
        RestaurantTo createdTo = new RestaurantTo(RESTAURANT_NEW.getName());
        ResultActions action = testCreateEntity(REST_URL, ADMIN_1, createdTo);
        Restaurant returned = TestUtil.readFromJson(action, Restaurant.class);
        Restaurant created = new Restaurant(returned.getId(), RESTAURANT_NEW.getName(), 0);
        assertMatch(returned, created);
    }

    @Test
    public void testUpdate() throws Exception {
        RestaurantTo updatedTo = new RestaurantTo("Updated Name");
        testUpdateEntity(REST_URL + RESTAURANT_1.getId(), ADMIN_1, updatedTo);
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("Updated Name");
        assertMatch(restaurantService.get(RESTAURANT_1.getId()), updated);
    }

    @Test
    public void testDelete() throws Exception {
        testDeleteEntity(REST_URL + RESTAURANT_1.getId(), ADMIN_1);
        assertMatch(restaurantService.getAll(), RESTAURANT_2, RESTAURANT_3);
    }

    @Test
    public void testPutMenu() throws Exception {
        List<Meal> newMenu = Arrays.asList(new Meal(MEAL_NEW), new Meal(MEAL_NEW));
        testUpdateEntity(REST_URL + RESTAURANT_1.getId() + "/menu", ADMIN_1, newMenu);
        List<Meal> expectedMenu = new ArrayList<>(newMenu);
        expectedMenu.get(0).setId(7);
        expectedMenu.get(1).setId(8);
        assertMatch(restaurantService.getTodayMenu(RESTAURANT_1.getId()), expectedMenu);
    }

}