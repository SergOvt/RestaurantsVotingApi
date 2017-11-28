package ru.voting.api.restaurants;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.voting.api.restaurants.service.MealServiceTest;
import ru.voting.api.restaurants.service.RestaurantServiceTest;
import ru.voting.api.restaurants.service.UserServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                MealServiceTest.class,
                RestaurantServiceTest.class,
                UserServiceTest.class
        })

public class AllTests {
}

