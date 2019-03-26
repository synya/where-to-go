package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Dish;
import com.mycompany.wheretogo.model.MenuItem;
import com.mycompany.wheretogo.model.Restaurant;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.mycompany.wheretogo.RestaurantTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantServiceImplTest extends AbstractServiceTest {
    @Autowired
    protected RestaurantService restaurantService;

    @Test
    public void get() throws Exception {
        assertThat(restaurantService.get(RESTAURANT_ATEOTU_ID)).isEqualTo(RESTAURANT_ATEOTU);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        restaurantService.get(1);
    }

    @Test
    public void add() throws Exception {
        Restaurant restaurant = getNew();
        Restaurant addedRestaurant = restaurantService.add(restaurant);
        restaurant.setId(addedRestaurant.getId());
        assertThat(restaurant).isEqualTo(addedRestaurant);
        assertThat(restaurantService.getAll())
                .hasSize(3)
                .isEqualTo(List.of(BURGER_KING, addedRestaurant, RESTAURANT_ATEOTU));
    }

    @Test
    public void update() throws Exception {
        Restaurant restaurant = getUpdated();
        restaurantService.update(restaurant);
        assertThat(restaurantService.get(restaurant.getId())).isEqualTo(restaurant);
    }

    @Test
    public void getAll() throws Exception {
        assertThat(restaurantService.getAll())
                .hasSize(2)
                .isEqualTo(List.of(BURGER_KING, RESTAURANT_ATEOTU));
    }

    @Test
    public void addDish() throws Exception {
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
    public void getDish() throws Exception {
        assertThat(restaurantService.getDish(BURGER_KING_DISH_ID)).isEqualTo(BURGER_KING_DISH1);
    }

    @Test(expected = NotFoundException.class)
    public void getDishNotFound() throws Exception {
        restaurantService.getDish(1);
    }

    @Test
    public void updateDish() throws Exception {
        Dish dish = getUpdatedDish();
        restaurantService.updateDish(dish);
        assertThat(restaurantService.getDish(dish.getId())).isEqualTo(dish);
    }

    @Test
    public void addExistedDish() throws Exception {
        Dish dish = BURGER_KING_DISH1;
        Dish addedDish = restaurantService.addDish(dish);
        dish.setId(addedDish.getId());
        assertThat(dish).isEqualTo(addedDish);
        assertThat(restaurantService.getAllDishes(BURGER_KING_ID))
                .hasSize(6)
                .isEqualTo(List.of(BURGER_KING_DISH1, BURGER_KING_DISH4, BURGER_KING_DISH2,
                        BURGER_KING_DISH6, BURGER_KING_DISH5, BURGER_KING_DISH3));
    }

    @Test
    public void getAllDishes() throws Exception {
        assertThat(restaurantService.getAllDishes(RESTAURANT_ATEOTU_ID))
                .hasSize(6)
                .isEqualTo(List.of(RESTAURANT_ATEOTU_DISH4, RESTAURANT_ATEOTU_DISH3, RESTAURANT_ATEOTU_DISH6,
                        RESTAURANT_ATEOTU_DISH1, RESTAURANT_ATEOTU_DISH5, RESTAURANT_ATEOTU_DISH2));
    }

    @Test
    public void addMenuItem() throws Exception {
        MenuItem menuItem = getNewMenuItem();
        restaurantService.addMenuItem(menuItem.getDish().getId(), menuItem.getDate(), menuItem.getPrice());
        assertThat(restaurantService.getAllMenuItemsBetween(menuItem.getDate(), menuItem.getDate()))
                .hasSize(1)
                .isEqualTo(List.of(menuItem));
    }

    @Test
    public void getAllMenuItemsBetween() throws Exception {
        assertThat(restaurantService.getAllMenuItemsBetween(LocalDate.of(2019, Month.MARCH, 20),
                LocalDate.of(2019, Month.MARCH, 20)))
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