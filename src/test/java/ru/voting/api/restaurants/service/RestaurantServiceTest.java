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
    public void get() throws Exception {
        Restaurant actual = service.get(1);
        assertMatch(actual, RESTAURANT_1);
    }

    @Test
    public void getAll() throws Exception {
        List<Restaurant> actual = service.getAll();
        assertMatch(actual, RESTAURANT_2, RESTAURANT_1, RESTAURANT_3);
    }

    @Test
    public void add() throws Exception {
        service.create(RESTAURANT_NEW);
        assertMatch(service.get(4), RESTAURANT_NEW);
    }

    @Test
    public void update() throws Exception {
        service.update(RESTAURANT_UPDATED);
        assertMatch(service.get(1), RESTAURANT_UPDATED);
    }

    @Test
    public void delete() throws Exception {
        service.delete(1);
        assertMatch(service.getAll(), RESTAURANT_2, RESTAURANT_3);
    }

    @Test
    public void setNewVote() throws Exception {
        service.vote(2, "user1@mail.ru");
        Assert.assertEquals(service.get(2).getRating(), 3);
    }

    @Test
    public void setVote() throws Exception {
        if (LocalTime.now().isBefore(LocalTime.of(11,0))) {
            service.vote(1, "user1@mail.ru");
            Assert.assertEquals(service.get(1).getRating(), 2);
        }
    }

    @Test(expected = NotFoundException.class)
    public void setVoteException() throws Exception {
        if (LocalTime.now().isAfter(LocalTime.of(11,0))) {
            service.vote(1, "admin2@mail.ru");
        }
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(5);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(4);
    }

}