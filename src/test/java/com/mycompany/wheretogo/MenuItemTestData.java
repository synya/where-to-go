package com.mycompany.wheretogo;

import com.mycompany.wheretogo.model.MenuItem;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.mycompany.wheretogo.DishTestData.*;
import static com.mycompany.wheretogo.model.AbstractBaseEntity.START_SEQ;

public class MenuItemTestData {
    public static final int RESTAURANTS_MENU_ITEMS_ID = START_SEQ + 16;

    public static final MenuItem MENU_ITEM1 = new MenuItem(RESTAURANTS_MENU_ITEMS_ID, BURGER_KING_DISH1,
            LocalDate.of(2019, Month.MARCH, 20), 1000);
    public static final MenuItem MENU_ITEM2 = new MenuItem(RESTAURANTS_MENU_ITEMS_ID + 1, BURGER_KING_DISH2,
            LocalDate.of(2019, Month.MARCH, 20), 200);
    public static final MenuItem MENU_ITEM3 = new MenuItem(RESTAURANTS_MENU_ITEMS_ID + 2, BURGER_KING_DISH3,
            LocalDate.of(2019, Month.MARCH, 20), 500);
    public static final MenuItem MENU_ITEM4 = new MenuItem(RESTAURANTS_MENU_ITEMS_ID + 3, RESTAURANT_ATEOTU_DISH1,
            LocalDate.of(2019, Month.MARCH, 20), 9900);
    public static final MenuItem MENU_ITEM5 = new MenuItem(RESTAURANTS_MENU_ITEMS_ID + 4, RESTAURANT_ATEOTU_DISH2,
            LocalDate.of(2019, Month.MARCH, 20), 4560);
    public static final MenuItem MENU_ITEM6 = new MenuItem(RESTAURANTS_MENU_ITEMS_ID + 5, RESTAURANT_ATEOTU_DISH3,
            LocalDate.of(2019, Month.MARCH, 20), 1000);
    public static final MenuItem MENU_ITEM7 = new MenuItem(RESTAURANTS_MENU_ITEMS_ID + 6, BURGER_KING_DISH4,
            LocalDate.of(2019, Month.MARCH, 21), 1000);
    public static final MenuItem MENU_ITEM8 = new MenuItem(RESTAURANTS_MENU_ITEMS_ID + 7, BURGER_KING_DISH5,
            LocalDate.of(2019, Month.MARCH, 21), 320);
    public static final MenuItem MENU_ITEM9 = new MenuItem(RESTAURANTS_MENU_ITEMS_ID + 8, BURGER_KING_DISH6,
            LocalDate.of(2019, Month.MARCH, 21), 450);
    public static final MenuItem MENU_ITEM10 = new MenuItem(RESTAURANTS_MENU_ITEMS_ID + 9, RESTAURANT_ATEOTU_DISH4,
            LocalDate.of(2019, Month.MARCH, 21), 9900);
    public static final MenuItem MENU_ITEM11 = new MenuItem(RESTAURANTS_MENU_ITEMS_ID + 10, RESTAURANT_ATEOTU_DISH5,
            LocalDate.of(2019, Month.MARCH, 21), 4560);
    public static final MenuItem MENU_ITEM12 = new MenuItem(RESTAURANTS_MENU_ITEMS_ID + 11, RESTAURANT_ATEOTU_DISH6,
            LocalDate.of(2019, Month.MARCH, 21), 1000);

    public static final int TODAY_RESTAURANTS_MENU_ITEMS_ID = START_SEQ + 28;

    public static final MenuItem TODAY_MENU_ITEM1 = new MenuItem(TODAY_RESTAURANTS_MENU_ITEMS_ID, BURGER_KING_DISH4, LocalDate.now(), 1500);
    public static final MenuItem TODAY_MENU_ITEM2 = new MenuItem(TODAY_RESTAURANTS_MENU_ITEMS_ID + 1, BURGER_KING_DISH1, LocalDate.now(), 210);
    public static final MenuItem TODAY_MENU_ITEM3 = new MenuItem(TODAY_RESTAURANTS_MENU_ITEMS_ID + 2, RESTAURANT_ATEOTU_DISH1, LocalDate.now(), 560);
    public static final MenuItem TODAY_MENU_ITEM4 = new MenuItem(TODAY_RESTAURANTS_MENU_ITEMS_ID + 3, RESTAURANT_ATEOTU_DISH6, LocalDate.now(), 9700);
    public static final MenuItem TODAY_MENU_ITEM5 = new MenuItem(TODAY_RESTAURANTS_MENU_ITEMS_ID + 4, RESTAURANT_ATEOTU_DISH2, LocalDate.now(), 4310);
    public static final MenuItem TODAY_MENU_ITEM6 = new MenuItem(TODAY_RESTAURANTS_MENU_ITEMS_ID + 5, RESTAURANT_ATEOTU_DISH4, LocalDate.now(), 1230);

    public static final List<MenuItem> TODAY_MENU_ITEMS = List.of(TODAY_MENU_ITEM2, TODAY_MENU_ITEM1, TODAY_MENU_ITEM6, TODAY_MENU_ITEM4, TODAY_MENU_ITEM3, TODAY_MENU_ITEM5);
    public static final List<MenuItem> ALL_MENU_ITEMS = List.of(TODAY_MENU_ITEM2, TODAY_MENU_ITEM1, TODAY_MENU_ITEM6, TODAY_MENU_ITEM4, TODAY_MENU_ITEM3, TODAY_MENU_ITEM5,
            MENU_ITEM7, MENU_ITEM9, MENU_ITEM8, MENU_ITEM10, MENU_ITEM12, MENU_ITEM11,
            MENU_ITEM1, MENU_ITEM2, MENU_ITEM3, MENU_ITEM6, MENU_ITEM4, MENU_ITEM5);
    public static final List<MenuItem> HISTORY_MENU_ITEMS = List.of(MENU_ITEM7, MENU_ITEM9, MENU_ITEM8, MENU_ITEM10, MENU_ITEM12, MENU_ITEM11,
            MENU_ITEM1, MENU_ITEM2, MENU_ITEM3, MENU_ITEM6, MENU_ITEM4, MENU_ITEM5);


    public static void assertMatch(MenuItem actual, MenuItem expected) {
        TestUtil.assertMatch(actual, expected);
    }

    public static void assertMatch(Iterable<MenuItem> actual, MenuItem... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<MenuItem> actual, Iterable<MenuItem> expected) {
        TestUtil.assertMatch(actual, expected);
    }

    public static ResultMatcher fromJsonAndAssert(MenuItem... expected) {
        return TestUtil.fromJsonAndAssert(List.of(expected), MenuItem.class);
    }

    public static ResultMatcher fromJsonAndAssert(MenuItem expected) {
        return TestUtil.fromJsonAndAssert(expected, MenuItem.class);
    }
}
