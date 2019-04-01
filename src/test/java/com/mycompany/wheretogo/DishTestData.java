package com.mycompany.wheretogo;

import com.mycompany.wheretogo.model.Dish;

import java.util.List;

import static com.mycompany.wheretogo.RestaurantTestData.BURGER_KING;
import static com.mycompany.wheretogo.RestaurantTestData.RESTAURANT_ATEOTU;
import static com.mycompany.wheretogo.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static final int BURGER_KING_DISH_ID = START_SEQ + 4;
    public static final int RESTAURANT_ATEOTU_DISH_ID = START_SEQ + 10;

    public static final Dish BURGER_KING_DISH1 = new Dish(BURGER_KING_DISH_ID, "Burger", BURGER_KING);
    public static final Dish BURGER_KING_DISH2 = new Dish(BURGER_KING_DISH_ID + 1, "Cola", BURGER_KING);
    public static final Dish BURGER_KING_DISH3 = new Dish(BURGER_KING_DISH_ID + 2, "Snack", BURGER_KING);
    public static final Dish BURGER_KING_DISH4 = new Dish(BURGER_KING_DISH_ID + 3, "Chicken Toast", BURGER_KING);
    public static final Dish BURGER_KING_DISH5 = new Dish(BURGER_KING_DISH_ID + 4, "Latte", BURGER_KING);
    public static final Dish BURGER_KING_DISH6 = new Dish(BURGER_KING_DISH_ID + 5, "Ice Cream", BURGER_KING);

    public static final Dish RESTAURANT_ATEOTU_DISH1 = new Dish(RESTAURANT_ATEOTU_DISH_ID, "Meet The Meat", RESTAURANT_ATEOTU);
    public static final Dish RESTAURANT_ATEOTU_DISH2 = new Dish(RESTAURANT_ATEOTU_DISH_ID + 1, "The Pan Galactic Gargle Blaster", RESTAURANT_ATEOTU);
    public static final Dish RESTAURANT_ATEOTU_DISH3 = new Dish(RESTAURANT_ATEOTU_DISH_ID + 2, "Janx Spirit", RESTAURANT_ATEOTU);
    public static final Dish RESTAURANT_ATEOTU_DISH4 = new Dish(RESTAURANT_ATEOTU_DISH_ID + 3, "Hagro biscuit", RESTAURANT_ATEOTU);
    public static final Dish RESTAURANT_ATEOTU_DISH5 = new Dish(RESTAURANT_ATEOTU_DISH_ID + 4, "Peanuts", RESTAURANT_ATEOTU);
    public static final Dish RESTAURANT_ATEOTU_DISH6 = new Dish(RESTAURANT_ATEOTU_DISH_ID + 5, "Jynnan tonnyx", RESTAURANT_ATEOTU);

    public static void assertMatch(Dish actual, Dish expected) {
        TestUtil.assertMatch(actual, expected);
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        TestUtil.assertMatch(actual, expected);
    }
}
