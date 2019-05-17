package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Dish;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;

import static com.mycompany.wheretogo.DishTestData.*;
import static com.mycompany.wheretogo.RestaurantTestData.*;
import static com.mycompany.wheretogo.RestaurantTestData.RESTAURANT_ATEOTU_ID;

public class DishServiceTest extends AbstractServiceTest {
    @Autowired
    private DishService dishService;

    @Test
    public void testAddDish() throws Exception {
        Dish dish = new Dish("The Brand New Mega Hamburger");
        Dish addedDish = dishService.add(dish, BURGER_KING_ID);
        dish.setId(addedDish.getId());
        assertMatch(dish, addedDish);
        dish.setRestaurant(BURGER_KING);
        assertMatch(dishService.getAll(BURGER_KING_ID),
                BURGER_KING_DISH1, BURGER_KING_DISH4, BURGER_KING_DISH2,
                BURGER_KING_DISH6, BURGER_KING_DISH5, BURGER_KING_DISH3, dish);
    }

    @Test
    public void testGetDish() throws Exception {
        assertMatch(dishService.get(RESTAURANT_ATEOTU_DISH_ID), RESTAURANT_ATEOTU_DISH1);
    }

    @Test
    public void testGetAllDishes() throws Exception {
        assertMatch(dishService.getAll(RESTAURANT_ATEOTU_ID),
                RESTAURANT_ATEOTU_DISH4, RESTAURANT_ATEOTU_DISH3, RESTAURANT_ATEOTU_DISH6,
                RESTAURANT_ATEOTU_DISH1, RESTAURANT_ATEOTU_DISH5, RESTAURANT_ATEOTU_DISH2);
    }

    @Test(expected = NotFoundException.class)
    public void testGetDishNotFound() throws Exception {
        dishService.get(ENTITY_NOT_FOUND_ID);
    }

    @Test
    public void testUpdateDish() throws Exception {
        Dish updatedDish = new Dish(RESTAURANT_ATEOTU_DISH2);
        updatedDish.setName("The Pan Galactic Gargle Blaster Updated");
        dishService.update(updatedDish, RESTAURANT_ATEOTU_ID);
        updatedDish.setRestaurant(RESTAURANT_ATEOTU);
        assertMatch(dishService.getAll(RESTAURANT_ATEOTU_ID),
                RESTAURANT_ATEOTU_DISH4, RESTAURANT_ATEOTU_DISH3, RESTAURANT_ATEOTU_DISH6,
                RESTAURANT_ATEOTU_DISH1, RESTAURANT_ATEOTU_DISH5, updatedDish);
    }

    @Test
    public void testDeleteDish() throws Exception {
        dishService.delete(RESTAURANT_ATEOTU_DISH_ID);
        assertMatch(dishService.getAll(RESTAURANT_ATEOTU_ID),
                RESTAURANT_ATEOTU_DISH4, RESTAURANT_ATEOTU_DISH3, RESTAURANT_ATEOTU_DISH6,
                RESTAURANT_ATEOTU_DISH5, RESTAURANT_ATEOTU_DISH2);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteDishNotFound() throws Exception {
        dishService.delete(ENTITY_NOT_FOUND_ID);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> dishService.add(new Dish(null, null, null), 100002), ConstraintViolationException.class);
    }
}