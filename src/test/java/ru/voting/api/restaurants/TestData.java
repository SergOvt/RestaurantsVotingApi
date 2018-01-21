package ru.voting.api.restaurants;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.to.RestaurantTo;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.voting.api.restaurants.model.Role.*;
import static ru.voting.api.restaurants.web.json.JsonUtil.writeIgnoreProps;

public class TestData {

    public static final Meal MEAL_1 = new Meal(1, "meat", 10000, LocalDate.of(2000,1,1));
    public static final Meal MEAL_2 = new Meal(2, "fish", 15000, LocalDate.now());
    public static final Meal MEAL_3 = new Meal(3, "chicken", 5000, LocalDate.now());
    public static final Meal MEAL_4 = new Meal(4, "meat", 15000, LocalDate.of(2000,1,1));
    public static final Meal MEAL_5 = new Meal(5, "fish", 20000, LocalDate.now());
    public static final Meal MEAL_6 = new Meal(6, "chicken", 10000, LocalDate.now());

    public static final RestaurantTo RESTAURANT_1 = new RestaurantTo(1, "Restaurant1", 1);
    public static final RestaurantTo RESTAURANT_2 = new RestaurantTo(2, "Restaurant2", 0);
    public static final RestaurantTo RESTAURANT_3 = new RestaurantTo(3, "Restaurant3", 0);
    public static final RestaurantTo RESTAURANT_NEW = new RestaurantTo(4, "new", 0);

    public static final User USER_1 = new User(1, "user1", "user1@mail.ru", "qwerty", ROLE_USER);
    public static final User USER_2 = new User(2, "user2", "user2@mail.ru", "qwerty", ROLE_USER);
    public static final User ADMIN = new User(3, "admin", "admin@mail.ru", "qwerty", ROLE_ADMIN);
    public static final User USER_NEW = new User(null,"new", "new@mail.ru", "qwerty", ROLE_USER);

    public static <T> void assertMatch(T actual, T expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "password");
    }

    @SafeVarargs
    public static <T> void assertMatch(Iterable<? extends T> actual, T... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static <T> void assertMatch(Iterable<? extends T> actual, Iterable<? extends T> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("password").isEqualTo(expected);
    }

    public static <T> ResultMatcher contentJson(T expected) {
        return content().json(writeIgnoreProps(expected, "password"));
    }

    @SafeVarargs
    public static <T> ResultMatcher contentJson(T... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "password"));
    }
}
