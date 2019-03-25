package com.mycompany.wheretogo;

import com.mycompany.wheretogo.model.MenuItem;
import com.mycompany.wheretogo.model.Dish;
import com.mycompany.wheretogo.model.Restaurant;

import java.time.LocalDate;
import java.time.Month;

import static com.mycompany.wheretogo.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final int BURGER_KING_ID = START_SEQ + 2;
    public static final int RESTAURANT_ATEOTU_ID = START_SEQ + 3;

    public static final Restaurant BURGER_KING = new Restaurant(BURGER_KING_ID, "Burger King");
    public static final Restaurant RESTAURANT_ATEOTU = new Restaurant(RESTAURANT_ATEOTU_ID, "The Restaurant at the End of the Universe");

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

    public static Restaurant getCreated() {
        return new Restaurant("KFC");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(BURGER_KING_ID, "Rebranded Burger King");
    }

    public static Dish getCreatedDish() {
        return new Dish("The Brand New Mega Hamburger", BURGER_KING);
    }

    public static MenuItem getCreatedMenuItem() {
        return new MenuItem(RESTAURANTS_MENU_ITEMS_ID + 12, RESTAURANT_ATEOTU_DISH4,
                LocalDate.of(2019, Month.MARCH, 25), 2000);
    }

}
