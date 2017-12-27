package ru.voting.api.restaurants.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.voting.api.restaurants.model.Meal;

import java.util.Arrays;
import java.util.List;

import static ru.voting.api.restaurants.TestData.*;


@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/initDB.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void testGetTodayMenu() throws Exception {
        assertMatch(service.getTodayMenu(1), MEAL_2, MEAL_3);
    }

    @Test
    public void testPutMenu() throws Exception {
        List<Meal> newMenu = service.putMenu(Arrays.asList(MEAL_NEW, new Meal(MEAL_NEW)), 2);
        assertMatch(service.getTodayMenu(2), newMenu);
    }
}