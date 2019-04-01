package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.DishTestData;
import com.mycompany.wheretogo.MenuItemTestData;
import com.mycompany.wheretogo.RestaurantTestData;
import com.mycompany.wheretogo.model.Dish;
import com.mycompany.wheretogo.model.MenuItem;
import com.mycompany.wheretogo.model.Restaurant;
import com.mycompany.wheretogo.util.JpaUtil;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.mycompany.wheretogo.DishTestData.*;
import static com.mycompany.wheretogo.MenuItemTestData.*;
import static com.mycompany.wheretogo.RestaurantTestData.*;

public class RestaurantServiceImplTest extends AbstractServiceTest {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JpaUtil jpaUtil;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("menuItems").clear();
        cacheManager.getCache("dishes").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void testAddRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant("KFC");
        Restaurant addedRestaurant = restaurantService.add(restaurant);
        restaurant.setId(addedRestaurant.getId());
        assertMatch(restaurant, addedRestaurant);
        assertMatch(restaurantService.getAll(), BURGER_KING, addedRestaurant, RESTAURANT_ATEOTU);
    }

    @Test
    public void testGetRestaurant() throws Exception {
        assertMatch(restaurantService.get(RESTAURANT_ATEOTU_ID), RESTAURANT_ATEOTU);
    }


    @Test(expected = NotFoundException.class)
    public void testGetRestaurantNotFound() throws Exception {
        restaurantService.get(ENTITY_NOT_FOUND_ID);
    }

    @Test
    public void testUpdateRestaurant() throws Exception {
        Restaurant updatedRestaurant = restaurantService.get(BURGER_KING_ID);
        updatedRestaurant.setName("Rebranded Burger King");
        restaurantService.update(updatedRestaurant);
        assertMatch(restaurantService.getAll(), updatedRestaurant, RESTAURANT_ATEOTU);
    }

    @Test
    public void testDeleteRestaurant() throws Exception {
        restaurantService.delete(BURGER_KING_ID);
        assertMatch(restaurantService.getAll(), RESTAURANT_ATEOTU);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteRestaurantNotFound() throws Exception {
        restaurantService.delete(ENTITY_NOT_FOUND_ID);
    }

    @Test
    public void testGetAllRestaurants() throws Exception {
        assertMatch(restaurantService.getAll(), BURGER_KING, RESTAURANT_ATEOTU);
    }

    @Test
    public void testAddDish() throws Exception {
        Dish dish = new Dish("The Brand New Mega Hamburger", BURGER_KING);
        Dish addedDish = restaurantService.addDish(dish);
        dish.setId(addedDish.getId());
        assertMatch(dish, addedDish);
        assertMatch(restaurantService.getAllDishes(BURGER_KING_ID),
                BURGER_KING_DISH1, BURGER_KING_DISH4, BURGER_KING_DISH2,
                BURGER_KING_DISH6, BURGER_KING_DISH5, BURGER_KING_DISH3, addedDish);
    }

    @Test
    public void testGetDish() throws Exception {
        assertMatch(restaurantService.getDish(RESTAURANT_ATEOTU_DISH_ID), RESTAURANT_ATEOTU_DISH1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetDishNotFound() throws Exception {
        restaurantService.getDish(ENTITY_NOT_FOUND_ID);
    }

    @Test
    public void testUpdateDish() throws Exception {
        Dish updatedDish = restaurantService.getDish(RESTAURANT_ATEOTU_DISH_ID + 1);
        updatedDish.setName("The Pan Galactic Gargle Blaster Updated");
        restaurantService.updateDish(updatedDish);
        assertMatch(restaurantService.getAllDishes(RESTAURANT_ATEOTU_ID),
                RESTAURANT_ATEOTU_DISH4, RESTAURANT_ATEOTU_DISH3, RESTAURANT_ATEOTU_DISH6,
                RESTAURANT_ATEOTU_DISH1, RESTAURANT_ATEOTU_DISH5, updatedDish);
    }

    @Test
    public void testDeleteDish() throws Exception {
        restaurantService.deleteDish(RESTAURANT_ATEOTU_DISH_ID);
        assertMatch(restaurantService.getAllDishes(RESTAURANT_ATEOTU_ID),
                RESTAURANT_ATEOTU_DISH4, RESTAURANT_ATEOTU_DISH3, RESTAURANT_ATEOTU_DISH6,
                RESTAURANT_ATEOTU_DISH5, RESTAURANT_ATEOTU_DISH2);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteDishNotFound() throws Exception {
        restaurantService.deleteDish(ENTITY_NOT_FOUND_ID);
    }

    @Test
    public void testGetAllDishes() throws Exception {
        assertMatch(restaurantService.getAllDishes(RESTAURANT_ATEOTU_ID),
                RESTAURANT_ATEOTU_DISH4, RESTAURANT_ATEOTU_DISH3, RESTAURANT_ATEOTU_DISH6,
                RESTAURANT_ATEOTU_DISH1, RESTAURANT_ATEOTU_DISH5, RESTAURANT_ATEOTU_DISH2);
    }

    @Test
    public void testAddMenuItem() throws Exception {
        MenuItem menuItem = new MenuItem(RESTAURANT_ATEOTU_DISH4, LocalDate.of(2019, Month.MARCH, 25), 2000);
        MenuItem addedMenuItem = restaurantService.addMenuItem(menuItem.getDish().getId(), menuItem.getDate(), menuItem.getPrice());
        menuItem.setId(addedMenuItem.getId());
        assertMatch(restaurantService.getAllMenuItemsBetweenDates(menuItem.getDate(), menuItem.getDate()),
                List.of(menuItem));
    }

    @Test
    public void testGetMenuItem() throws Exception {
        assertMatch(restaurantService.getMenuItem(RESTAURANTS_MENU_ITEMS_ID), MENU_ITEM1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetMenuItemNotFound() throws Exception {
        restaurantService.getMenuItem(ENTITY_NOT_FOUND_ID);
    }

    @Test
    public void testUpdateMenuItem() throws Exception {
        MenuItem updatedMenuItem = TODAY_MENU_ITEM1;
        updatedMenuItem.setPrice(200_000);
        restaurantService.updateMenuItem(updatedMenuItem);
        assertMatch(restaurantService.getAllTodayMenuItems(),
                TODAY_MENU_ITEM2, TODAY_MENU_ITEM1, TODAY_MENU_ITEM6, TODAY_MENU_ITEM4, TODAY_MENU_ITEM3, TODAY_MENU_ITEM5);
    }

    @Test
    public void testDeleteMenuItem() throws Exception {
        restaurantService.deleteMenuItem(TODAY_MENU_ITEM2.getId());
        assertMatch(restaurantService.getAllTodayMenuItems(),
                TODAY_MENU_ITEM1, TODAY_MENU_ITEM6, TODAY_MENU_ITEM4, TODAY_MENU_ITEM3, TODAY_MENU_ITEM5);

    }

    @Test(expected = NotFoundException.class)
    public void testDeleteMenuItemNotFound() throws Exception {
        restaurantService.deleteMenuItem(ENTITY_NOT_FOUND_ID);
    }

    @Test
    public void testGetAllTodayMenuItems() throws Exception {
        assertMatch(restaurantService.getAllTodayMenuItems(),
                TODAY_MENU_ITEM2, TODAY_MENU_ITEM1, TODAY_MENU_ITEM6, TODAY_MENU_ITEM4, TODAY_MENU_ITEM3, TODAY_MENU_ITEM5);
    }

    @Test
    public void testGetAllMenuItemsBetweenDates() throws Exception {
        LocalDate localDate = LocalDate.of(2019, Month.MARCH, 20);
        assertMatch(restaurantService.getAllMenuItemsBetweenDates(localDate, localDate),
                MENU_ITEM1, MENU_ITEM2, MENU_ITEM3, MENU_ITEM6, MENU_ITEM4, MENU_ITEM5);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> restaurantService.add(new Restaurant(null, " ")), ConstraintViolationException.class);
        validateRootCause(() -> restaurantService.add(new Restaurant(null, null)), ConstraintViolationException.class);
        validateRootCause(() -> restaurantService.addDish(new Dish(null, null, null)), ConstraintViolationException.class);
    }

}