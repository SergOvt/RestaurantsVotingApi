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
        User user = service.get("user1@mail.ru");
        assertMatch(user, USER_1);
    }

    @Test
    public void getAll() throws Exception {
        List<User> users = service.getAll();
        assertMatch(users, USER_1, USER_2, ADMIN_1, ADMIN_2);
    }

    @Test
    public void add() throws Exception {
        service.add(USER_NEW);
        assertMatch(service.get("new@mail.ru"), USER_NEW);
    }

    @Test
    public void update() throws Exception {
        service.update(USER_UPDATE);
        assertMatch(service.get("updated@mail.ru"), USER_UPDATE);
    }

    @Test
    public void delete() throws Exception {
        service.delete("user1@mail.ru");
        assertMatch(service.getAll(), USER_2, ADMIN_1, ADMIN_2);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get("dummy");
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete("dummy");
    }

}