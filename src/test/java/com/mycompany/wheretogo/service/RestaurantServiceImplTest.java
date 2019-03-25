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
        Restaurant newRestaurant = getCreated();
        Restaurant created = restaurantService.add(newRestaurant);
        newRestaurant.setId(created.getId());
        assertThat(newRestaurant).isEqualTo(created);
        assertThat(restaurantService.getAll())
                .hasSize(3)
                .isEqualTo(List.of(BURGER_KING, created, RESTAURANT_ATEOTU));
    }

    @Test
    public void update() throws Exception {
        Restaurant updated = getUpdated();
        restaurantService.update(updated);
        assertThat(restaurantService.get(updated.getId())).isEqualTo(updated);
    }

    @Test
    public void getAll() throws Exception {
        assertThat(restaurantService.getAll())
                .hasSize(2)
                .isEqualTo(List.of(BURGER_KING, RESTAURANT_ATEOTU));
    }

    @Test
    public void addDish() throws Exception {
        Dish newDish = getCreatedDish();
        Dish created = restaurantService.addDish(newDish);
        newDish.setId(created.getId());
        assertThat(newDish).isEqualTo(created);
        assertThat(restaurantService.getAllDishes(BURGER_KING_ID))
                .hasSize(7)
                .isEqualTo(List.of(BURGER_KING_DISH1, BURGER_KING_DISH4, BURGER_KING_DISH2,
                        BURGER_KING_DISH6, BURGER_KING_DISH5, BURGER_KING_DISH3, created));
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
        Dish updated = getUpdatedDish();
        restaurantService.updateDish(updated);
        assertThat(restaurantService.getDish(updated.getId())).isEqualTo(updated);
    }

    @Test
    public void addExistedDish() throws Exception {
        Dish newDish = BURGER_KING_DISH1;
        Dish created = restaurantService.addDish(newDish);
        newDish.setId(created.getId());
        assertThat(newDish).isEqualTo(created);
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
        MenuItem menuItem = getCreatedMenuItem();
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