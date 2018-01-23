package ru.voting.api.restaurants.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.api.restaurants.to.MealTo;
import ru.voting.api.restaurants.to.RestaurantTo;
import ru.voting.api.restaurants.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static ru.voting.api.restaurants.TestData.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Before
    @CacheEvict(value = "restaurants", allEntries = true)
    public void setUp() throws Exception {
    }

    @Test
    public void testGet() throws Exception {
        RestaurantTo actual = service.get(RESTAURANT_1.getId());
        assertMatch(actual, RESTAURANT_1);
    }

    @Test
    public void testGetAll() throws Exception {
        List<RestaurantTo> actual = service.getAll();
        assertMatch(actual, RESTAURANT_1, RESTAURANT_2, RESTAURANT_3);
    }

    @Test
    public void testCreate() throws Exception {
        service.create(new RestaurantTo(RESTAURANT_NEW.getName()));
        assertMatch(service.get(4), RESTAURANT_NEW);
    }

    @Test
    public void testUpdate() throws Exception {
        RestaurantTo updatedTo = new RestaurantTo("New Name");
        service.update(updatedTo, RESTAURANT_1.getId());
        RestaurantTo updated = new RestaurantTo(RESTAURANT_1);
        updated.setName("New Name");
        assertMatch(service.get(RESTAURANT_1.getId()), updated);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(RESTAURANT_1.getId());
        assertMatch(service.getAll(), RESTAURANT_2, RESTAURANT_3);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1000);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(1000);
    }

    @Test
    public void testGetTodayMenu() throws Exception {
        assertMatch(service.getTodayMenu(RESTAURANT_1.getId()), MEAL_2, MEAL_3);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testPutMenu() throws Exception {
        List<MealTo> menuTo = Arrays.asList(new MealTo("meal1", 2000), new MealTo("meal2", 1000));
        service.putMenu(menuTo, RESTAURANT_2.getId());
        assertMatch(service.getTodayMenu(RESTAURANT_2.getId()), menuTo);
    }

}