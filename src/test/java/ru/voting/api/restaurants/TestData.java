package ru.voting.api.restaurants;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;
import ru.voting.api.restaurants.model.User;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.voting.api.restaurants.model.Role.*;
import static ru.voting.api.restaurants.web.json.JsonUtil.writeValue;

public class TestData {

    public static final Meal MEAL_1 = new Meal(1, "meat", 10000, LocalDate.of(2000,1,1));
    public static final Meal MEAL_2 = new Meal(2, "fish", 15000, LocalDate.now());
    public static final Meal MEAL_3 = new Meal(3, "chicken", 5000, LocalDate.now());
    public static final Meal MEAL_4 = new Meal(4, "meat", 15000, LocalDate.of(2000,1,1));
    public static final Meal MEAL_5 = new Meal(5, "fish", 20000, LocalDate.now());
    public static final Meal MEAL_6 = new Meal(6, "chicken", 10000, LocalDate.now());
    public static final Meal MEAL_NEW = new Meal("new", 9999);

    public static final Restaurant RESTAURANT_1 = new Restaurant(1, "Restaurant1", 1);
    public static final Restaurant RESTAURANT_2 = new Restaurant(2, "Restaurant2", 2);
    public static final Restaurant RESTAURANT_3 = new Restaurant(3, "Restaurant3", 0);
    public static final Restaurant RESTAURANT_NEW = new Restaurant("new");

    public static final User USER_1 = new User(1, "user1", "user1@mail.ru", "qwerty", USER);
    public static final User USER_2 = new User(2, "user2", "user2@mail.ru", "qwerty", USER);
    public static final User ADMIN_1 = new User(3, "admin1", "admin1@mail.ru", "qwerty", ADMIN);
    public static final User ADMIN_2 = new User(4, "admin2", "admin2@mail.ru", "qwerty", ADMIN);
    public static final User USER_NEW = new User("new", "new@mail.ru", "qwerty", USER);

    public static <T> void assertMatch(T actual, T expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    @SafeVarargs
    public static <T> void assertMatch(Iterable<? extends T> actual, T... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static <T> void assertMatch(Iterable<? extends T> actual, Iterable<? extends T> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

    public static <T> ResultMatcher contentJson(T... expected) {
        return content().json(writeValue(Arrays.asList(expected)));
    }

    public static <T> ResultMatcher contentJson(T expected) {
        return content().json(writeValue(expected));
    }
}
