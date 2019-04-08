package com.mycompany.wheretogo;

import com.mycompany.wheretogo.model.Restaurant;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static com.mycompany.wheretogo.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final int BURGER_KING_ID = START_SEQ + 2;
    public static final int RESTAURANT_ATEOTU_ID = START_SEQ + 3;

    public static final Restaurant BURGER_KING = new Restaurant(BURGER_KING_ID, "Burger King");
    public static final Restaurant RESTAURANT_ATEOTU = new Restaurant(RESTAURANT_ATEOTU_ID, "The Restaurant at the End of the Universe");

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        TestUtil.assertMatch(actual, expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        TestUtil.assertMatch(actual, expected);
    }

    public static ResultMatcher fromJsonAndAssert(Restaurant... expected) {
        return TestUtil.fromJsonAndAssert(List.of(expected), Restaurant.class);
    }

    public static ResultMatcher fromJsonAndAssert(Restaurant expected) {
        return TestUtil.fromJsonAndAssert(expected, Restaurant.class);
    }
}
