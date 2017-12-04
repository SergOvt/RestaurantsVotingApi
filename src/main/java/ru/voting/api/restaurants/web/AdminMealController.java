package ru.voting.api.restaurants.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.voting.api.restaurants.model.Meal;
import ru.voting.api.restaurants.service.MealService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AdminMealController {

    private MealService mealService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public AdminMealController(MealService mealService) {
        this.mealService = mealService;
    }

    public Meal get(int id, int restaurantId) {
        log.info("get meal id={}", id);
        return mealService.get(id, restaurantId);
    }

    public List<Meal> getAll(int restaurantId){
        log.info("get all meals from restaurant id={}", restaurantId);
        return mealService.getAll(restaurantId);
    }

    public List<Meal> getAllByDate(LocalDate date, int restaurantId){
        log.info("get all meals by date {} from restaurant id={}", date, restaurantId);
        return mealService.getAllByDate(date, restaurantId);
    }

    public Meal add(Meal meal, int restaurantId){
        log.info("add new meal to restaurant id={}", restaurantId);
        return mealService.add(meal, restaurantId);
    }

    public void update(Meal meal, int restaurantId){
        log.info("update meal id={} from restaurant id={}", meal.getId(), restaurantId);
        mealService.update(meal, restaurantId);
    }

    public void delete(int id, int restaurantId){
        log.info("delete meal id={} from restaurant id={}", id, restaurantId);
        mealService.delete(id, restaurantId);
    }
}
