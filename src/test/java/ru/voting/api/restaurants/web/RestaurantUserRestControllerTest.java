package ru.voting.api.restaurants.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.api.restaurants.service.RestaurantService;

import java.time.LocalTime;

import static ru.voting.api.restaurants.TestData.*;
import static ru.voting.api.restaurants.TestData.MEAL_3;

public class RestaurantUserRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = RestaurantUserRestController.REST_URL + '/';
    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void testGet() throws Exception {
        testGetEntities(REST_URL + RESTAURANT_1.getId(), RESTAURANT_1);
    }

    @Test
    public void testGetAll() throws Exception {
        testGetEntities(REST_URL, RESTAURANT_1, RESTAURANT_2, RESTAURANT_3);
    }

    @Test
    public void testGetTodayMenu() throws Exception {
        testGetEntities(REST_URL + RESTAURANT_1.getId() + "/menu", MEAL_2, MEAL_3);
    }

    @Test
    public void testNewVote() throws Exception {
        testUpdateEntity(REST_URL + RESTAURANT_1.getId() + "/vote", null);
        assertMatch(restaurantService.get(RESTAURANT_1.getId()).getRating(), RESTAURANT_1.getRating() + 1);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testReVote() throws Exception {
        restaurantService.setEndVotingTime(LocalTime.now().plusHours(1));
        testUpdateEntity(REST_URL + RESTAURANT_1.getId() + "/vote", null);
        assertMatch(restaurantService.get(RESTAURANT_1.getId()).getRating(), RESTAURANT_1.getRating() + 1);
        testUpdateEntity(REST_URL + RESTAURANT_2.getId() + "/vote", null);
        assertMatch(restaurantService.get(RESTAURANT_1.getId()).getRating(), RESTAURANT_1.getRating());
        assertMatch(restaurantService.get(RESTAURANT_2.getId()).getRating(), RESTAURANT_2.getRating() + 1);
    }

}