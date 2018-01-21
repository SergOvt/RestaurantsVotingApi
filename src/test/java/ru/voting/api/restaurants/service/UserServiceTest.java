package ru.voting.api.restaurants.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.voting.api.restaurants.model.Role;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.util.exception.NotFoundException;
import ru.voting.api.restaurants.util.exception.VotingAccessException;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static ru.voting.api.restaurants.TestData.*;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/initDB.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void testGet() throws Exception {
        User user = userService.get(USER_1.getId());
        assertMatch(user, USER_1);
    }

    @Test
    public void testGetAll() throws Exception {
        List<User> users = userService.getAll();
        assertMatch(users, USER_1, USER_2, ADMIN);
    }

    @Test
    public void testCreate() throws Exception {
        User created = new User(USER_NEW);
        userService.create(created);
        created.setId(4);
        assertMatch(userService.get(4), created);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER_1);
        updated.setName("Updated");
        updated.setRoles(Collections.singleton(Role.ROLE_ADMIN));
        userService.update(updated, USER_1.getId());
        assertMatch(userService.get(USER_1.getId()), updated);
    }

    @Test
    public void testDelete() throws Exception {
        userService.delete(USER_1.getId());
        assertMatch(userService.getAll(), USER_2, ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        userService.get(0);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        userService.delete(0);
    }

    @Test
    public void testNewVote() throws Exception {
        userService.setEndVotingTime(LocalTime.now().plusMinutes(1));
        userService.vote(USER_1, RESTAURANT_2.getId());
        Assert.assertEquals(restaurantService.get(RESTAURANT_2.getId()).getRating(), RESTAURANT_2.getRating() + 1);
    }

    @Test
    public void testReVote() throws Exception {
        userService.setEndVotingTime(LocalTime.now().plusMinutes(1));
        userService.vote(USER_2, RESTAURANT_2.getId());
        Assert.assertEquals(restaurantService.get(RESTAURANT_1.getId()).getRating(), RESTAURANT_1.getRating() - 1);
        Assert.assertEquals(restaurantService.get(RESTAURANT_2.getId()).getRating(), RESTAURANT_2.getRating() + 1);
    }

    @Test(expected = VotingAccessException.class)
    public void testVoteException() throws Exception {
        userService.setEndVotingTime(LocalTime.now().minusMinutes(1));
        userService.vote(USER_2, RESTAURANT_1.getId());
    }

}