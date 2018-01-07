package ru.voting.api.restaurants.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.service.RestaurantService;

import java.util.Arrays;
import java.util.List;

import static ru.voting.api.restaurants.TestData.*;

public class RestaurantAdminRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = RestaurantAdminRestController.REST_URL + '/';
    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void testCreate() throws Exception {
        testCreateEntity(REST_URL, new Restaurant(RESTAURANT_NEW), Restaurant.class);
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("Updated Name");
        testUpdateEntity(REST_URL, updated);
        assertMatch(restaurantService.get(RESTAURANT_1.getId()), updated);
    }

    @Test
    public void testDelete() throws Exception {
        testDeleteEntity(REST_URL + RESTAURANT_1.getId());
        assertMatch(restaurantService.getAll(), RESTAURANT_2, RESTAURANT_3);
    }

    @Test
    public void testPutMenu() throws Exception {
        List<Meal> newMenu = Arrays.asList(new Meal(MEAL_NEW), new Meal(MEAL_NEW));
        testUpdateEntity(REST_URL + RESTAURANT_1.getId() + "/menu", newMenu);
        newMenu.get(0).setId(7);
        newMenu.get(1).setId(8);
        assertMatch(restaurantService.getTodayMenu(RESTAURANT_1.getId()), newMenu);
    }

}