package com.votingsystem.web.dish;

import com.votingsystem.web.MatcherFactory;
import com.votingsystem.model.Dish;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH1_ID = 1;
    public static final int NOT_FOUND = 100;

    public static final Dish dish1 = new Dish(DISH1_ID, "Rice with seafood", LocalDate.of(2020, 10, 10), new BigDecimal("400.00"));
    public static final Dish dish2 = new Dish(DISH1_ID + 1, "Soup puree", LocalDate.of(2020, 10, 10), new BigDecimal("300.00"));
    public static final Dish dish3 = new Dish(DISH1_ID + 2, "Royal roast beef", LocalDate.of(2020, 10, 10), new BigDecimal("600.00"));
    public static final Dish dish4 = new Dish(DISH1_ID + 3, "Orange juice", LocalDate.of(2020, 10, 10), new BigDecimal("10.00"));
    public static final Dish dish5 = new Dish(DISH1_ID + 4, "Coffee", LocalDate.of(2020, 10, 10), new BigDecimal("10.00"));
    public static final Dish dish6 = new Dish(DISH1_ID + 5, "Milk cocktail", LocalDate.of(2020, 10, 10), new BigDecimal("20.00"));
    public static final Dish dish7 = new Dish(DISH1_ID + 6, "Orange ice cream", LocalDate.now(), new BigDecimal("50.00"));
    public static final Dish dish8 = new Dish(DISH1_ID + 7, "Cherry ice cream", LocalDate.now(), new BigDecimal("55.00"));
    public static final Dish dish9 = new Dish(DISH1_ID + 8, "Apple ice cream", LocalDate.now(), new BigDecimal("51.00"));
    public static final Dish dish10 = new Dish(DISH1_ID + 9, "Beer", LocalDate.of(2020, 10, 10), new BigDecimal("100.00"));
    public static final Dish dish11 = new Dish(DISH1_ID + 10, "Beef", LocalDate.of(2020, 10, 10), new BigDecimal("200.00"));


    public static final List<Dish> DISHES = Arrays.asList(dish1, dish2, dish3, dish4, dish5, dish6, dish7, dish8, dish9, dish10, dish11);
}
