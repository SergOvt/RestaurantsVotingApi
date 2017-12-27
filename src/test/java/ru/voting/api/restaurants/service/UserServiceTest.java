package ru.voting.api.restaurants.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.util.exception.NotFoundException;

import java.util.List;

import static ru.voting.api.restaurants.TestData.*;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/initDB.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void get() throws Exception {
        User user = service.get(1);
        assertMatch(user, USER_1);
    }

    @Test
    public void getAll() throws Exception {
        List<User> users = service.getAll();
        assertMatch(users, USER_1, USER_2, ADMIN_1, ADMIN_2);
    }

    @Test
    public void add() throws Exception {
        service.create(USER_NEW);
        assertMatch(service.get(5), USER_NEW);
    }

    @Test
    public void update() throws Exception {
        service.update(USER_UPDATE);
        assertMatch(service.get(1), USER_UPDATE);
    }

    @Test
    public void delete() throws Exception {
        service.delete(1);
        assertMatch(service.getAll(), USER_2, ADMIN_1, ADMIN_2);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(0);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(0);
    }

}