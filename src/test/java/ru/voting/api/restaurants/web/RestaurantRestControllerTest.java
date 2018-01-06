package ru.voting.api.restaurants.web;

import org.junit.Test;

import static ru.voting.api.restaurants.TestData.*;

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
}