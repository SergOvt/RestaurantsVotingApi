package ru.voting.api.restaurants.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.util.exception.NotFoundException;
import ru.voting.api.restaurants.util.exception.VotingAccessException;

import java.time.LocalTime;
import java.util.List;

import static ru.voting.api.restaurants.TestData.*;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/initDB.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    public void testGet() throws Exception {
        Restaurant actual = service.get(1);
        assertMatch(actual, RESTAURANT_1);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Restaurant> actual = service.getAll();
        assertMatch(actual, RESTAURANT_2, RESTAURANT_1, RESTAURANT_3);
    }

    @Test
    public void testCrete() throws Exception {
        service.create(RESTAURANT_NEW);
        assertMatch(service.get(4), RESTAURANT_NEW);
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("Updated");
        service.update(updated);
        assertMatch(service.get(RESTAURANT_1.getId()), updated);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(1);
        assertMatch(service.getAll(), RESTAURANT_2, RESTAURANT_3);
    }

    @Test
    public void testNewVote() throws Exception {
        service.vote(2, USER_1.getId());
        Assert.assertEquals(service.get(2).getRating(), 3);
    }

    @Test
    public void testReVote() throws Exception {
        service.setEndVotingTime(LocalTime.now().plusMinutes(1));
        service.vote(1, ADMIN_2.getId());
        Assert.assertEquals(service.get(1).getRating(), 2);
        Assert.assertEquals(service.get(2).getRating(), 1);
    }

    @Test(expected = VotingAccessException.class)
    public void testVoteException() throws Exception {
        service.setEndVotingTime(LocalTime.now().minusHours(1));
        service.vote(1, ADMIN_2.getId());
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(5);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(4);
    }

}