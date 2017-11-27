package ru.voting.api.restaurants;

import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.model.Restaurant;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestData {

    public static final Meal MEAL_1 = new Meal(1, "meat", 10000, LocalDate.now());
    public static final Meal MEAL_2 = new Meal(2, "fish", 15000, LocalDate.now());
    public static final Meal MEAL_3 = new Meal(3, "chicken", 5000, LocalDate.now());
    public static final Meal MEAL_4 = new Meal(4, "meat", 15000, LocalDate.now());
    public static final Meal MEAL_5 = new Meal(5, "fish", 20000, LocalDate.now());
    public static final Meal MEAL_6 = new Meal(6, "chicken", 10000, LocalDate.now());

    public static final List<Meal> MENU_1 = Arrays.asList(MEAL_1, MEAL_2, MEAL_3);
    public static final List<Meal> MENU_2 = Arrays.asList(MEAL_4, MEAL_5, MEAL_6);

    public static final Restaurant RESTAURANT_1 = new Restaurant(1, "Restaurant1", 3);
    public static final Restaurant RESTAURANT_2 = new Restaurant(2, "Restaurant2", 1);

    public static <T> void assertMatch(T actual, T expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected,"restaurant");

    }

    public static <T> void assertMatch(Iterable<? extends T> actual, Iterable<? extends T> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }
}
