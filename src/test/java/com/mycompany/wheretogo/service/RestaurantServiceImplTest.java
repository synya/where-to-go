package com.mycompany.wheretogo.service;

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

import static com.mycompany.wheretogo.RestaurantTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

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
        Restaurant restaurant = getNew();
        Restaurant addedRestaurant = restaurantService.add(restaurant);
        restaurant.setId(addedRestaurant.getId());
        assertThat(restaurant).isEqualTo(addedRestaurant);
        assertThat(restaurantService.getAll())
                .hasSize(3)
                .isEqualTo(List.of(BURGER_KING, addedRestaurant, RESTAURANT_ATEOTU));
    }

    @Test
    public void testGetRestaurant() throws Exception {
        assertThat(restaurantService.get(RESTAURANT_ATEOTU_ID))
                .isEqualTo(RESTAURANT_ATEOTU);
    }


    @Test(expected = NotFoundException.class)
    public void testGetRestaurantNotFound() throws Exception {
        restaurantService.get(1);
    }

    @Test
    public void testUpdateRestaurant() throws Exception {
        Restaurant updatedRestaurant = BURGER_KING;
        updatedRestaurant.setName("Rebranded Burger King");
        restaurantService.update(updatedRestaurant);
        assertThat(restaurantService.getAll())
                .hasSize(2)
                .isEqualTo(List.of(updatedRestaurant, RESTAURANT_ATEOTU));
    }

    @Test
    public void testDeleteRestaurant() throws Exception {
        restaurantService.delete(BURGER_KING_ID);
        assertThat(restaurantService.getAll())
                .hasSize(1)
                .isEqualTo(List.of(RESTAURANT_ATEOTU));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteRestaurantNotFound() throws Exception {
        restaurantService.delete(1);
    }

    @Test
    public void testGetAllRestaurants() throws Exception {
        assertThat(restaurantService.getAll())
                .hasSize(2)
                .isEqualTo(List.of(BURGER_KING, RESTAURANT_ATEOTU));
    }

    @Test
    public void testAddDish() throws Exception {
        Dish dish = getNewDish();
        Dish addedDish = restaurantService.addDish(dish);
        dish.setId(addedDish.getId());
        assertThat(dish).isEqualTo(addedDish);
        assertThat(restaurantService.getAllDishes(BURGER_KING_ID))
                .hasSize(7)
                .isEqualTo(List.of(BURGER_KING_DISH1, BURGER_KING_DISH4, BURGER_KING_DISH2,
                        BURGER_KING_DISH6, BURGER_KING_DISH5, BURGER_KING_DISH3, addedDish));
    }

    @Test
    public void testGetDish() throws Exception {
        assertThat(restaurantService.getDish(RESTAURANT_ATEOTU_DISH_ID))
                .isEqualTo(RESTAURANT_ATEOTU_DISH1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetDishNotFound() throws Exception {
        restaurantService.getDish(1);
    }

    @Test
    public void testUpdateDish() throws Exception {
        Dish updatedDish = RESTAURANT_ATEOTU_DISH2;
        updatedDish.setName("The Pan Galactic Gargle Blaster Updated");
        restaurantService.updateDish(updatedDish);
        assertThat(restaurantService.getAllDishes(RESTAURANT_ATEOTU_ID))
                .hasSize(6)
                .isEqualTo(List.of(RESTAURANT_ATEOTU_DISH4, RESTAURANT_ATEOTU_DISH3, RESTAURANT_ATEOTU_DISH6,
                        RESTAURANT_ATEOTU_DISH1, RESTAURANT_ATEOTU_DISH5, updatedDish));
    }

    @Test
    public void testDeleteDish() throws Exception {
        restaurantService.deleteDish(RESTAURANT_ATEOTU_DISH_ID);
        assertThat(restaurantService.getAllDishes(RESTAURANT_ATEOTU_ID))
                .hasSize(5)
                .isEqualTo(List.of(RESTAURANT_ATEOTU_DISH4, RESTAURANT_ATEOTU_DISH3, RESTAURANT_ATEOTU_DISH6,
                        RESTAURANT_ATEOTU_DISH5, RESTAURANT_ATEOTU_DISH2));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteDishNotFound() throws Exception {
        restaurantService.deleteDish(1);
    }

    @Test
    public void testGetAllDishes() throws Exception {
        assertThat(restaurantService.getAllDishes(RESTAURANT_ATEOTU_ID))
                .hasSize(6)
                .isEqualTo(List.of(RESTAURANT_ATEOTU_DISH4, RESTAURANT_ATEOTU_DISH3, RESTAURANT_ATEOTU_DISH6,
                        RESTAURANT_ATEOTU_DISH1, RESTAURANT_ATEOTU_DISH5, RESTAURANT_ATEOTU_DISH2));
    }

    @Test
    public void testAddMenuItem() throws Exception {
        MenuItem menuItem = getNewMenuItem();
        restaurantService.addMenuItem(menuItem.getDish().getId(), menuItem.getDate(), menuItem.getPrice());
        assertThat(restaurantService.getAllMenuItemsBetweenDates(menuItem.getDate(), menuItem.getDate()))
                .hasSize(1)
                .isEqualTo(List.of(menuItem));
    }

    @Test
    public void testGetMenuItem() throws Exception {
        assertThat(restaurantService.getMenuItem(RESTAURANTS_MENU_ITEMS_ID)).isEqualTo(MENU_ITEM1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetMenuItemNotFound() throws Exception {
        restaurantService.getMenuItem(1);
    }

    @Test
    public void testUpdateMenuItem() throws Exception {
        MenuItem updatedMenuItem = TODAY_MENU_ITEM1;
        updatedMenuItem.setPrice(200_000);
        restaurantService.updateMenuItem(updatedMenuItem);
        assertThat(restaurantService.getAllTodayMenuItems())
                .hasSize(6)
                .isEqualTo(List.of(TODAY_MENU_ITEM2, TODAY_MENU_ITEM1, TODAY_MENU_ITEM6, TODAY_MENU_ITEM4, TODAY_MENU_ITEM3, TODAY_MENU_ITEM5));
    }

    @Test
    public void testDeleteMenuItem() throws Exception {
        restaurantService.deleteMenuItem(TODAY_MENU_ITEM2.getId());
        assertThat(restaurantService.getAllTodayMenuItems())
                .hasSize(5)
                .isEqualTo(List.of(TODAY_MENU_ITEM1, TODAY_MENU_ITEM6, TODAY_MENU_ITEM4, TODAY_MENU_ITEM3, TODAY_MENU_ITEM5));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteMenuItemNotFound() throws Exception {
        restaurantService.deleteMenuItem(1);
    }

    @Test
    public void testGetAllTodayMenuItems() throws Exception {
        assertThat(restaurantService.getAllTodayMenuItems())
                .hasSize(6)
                .isEqualTo(List.of(TODAY_MENU_ITEM2, TODAY_MENU_ITEM1, TODAY_MENU_ITEM6, TODAY_MENU_ITEM4, TODAY_MENU_ITEM3, TODAY_MENU_ITEM5));
    }

    @Test
    public void testGetAllMenuItemsBetweenDates() throws Exception {
        LocalDate localDate = LocalDate.of(2019, Month.MARCH, 20);
        assertThat(restaurantService.getAllMenuItemsBetweenDates(localDate, localDate))
                .hasSize(6)
                .isEqualTo(List.of(MENU_ITEM1, MENU_ITEM2, MENU_ITEM3, MENU_ITEM6, MENU_ITEM4, MENU_ITEM5));
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> restaurantService.add(new Restaurant(null, " ")), ConstraintViolationException.class);
        validateRootCause(() -> restaurantService.add(new Restaurant(null, null)), ConstraintViolationException.class);
        validateRootCause(() -> restaurantService.addDish(new Dish(null, null, null)), ConstraintViolationException.class);
    }

}