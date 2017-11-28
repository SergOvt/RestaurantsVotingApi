package ru.voting.api.restaurants.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.util.exception.NotFoundException;

import java.time.LocalDate;

import static ru.voting.api.restaurants.TestData.*;


@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/initDB.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal actual = service.get(1, 1);
        assertMatch(actual, MEAL_1);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(1), MEAL_2, MEAL_3, MEAL_1);
    }

    public void getByDate() throws Exception {
        assertMatch(service.getAllByDate(LocalDate.of(2000,1,1), 1), MEAL_1);
    }

    @Test
    public void add() throws Exception {
        service.add(MEAL_NEW, 1);
        assertMatch(service.getAll(1), MEAL_2, MEAL_3, MEAL_NEW, MEAL_1);
    }

    @Test
    public void update() throws Exception {
        service.update(MEAL_UPDATED, 1);
        assertMatch(service.get(1, 1), MEAL_UPDATED);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, 2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(4,1);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        service.update(MEAL_1,2);
    }

}