package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Restaurant;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;

import static com.mycompany.wheretogo.RestaurantTestData.assertMatch;
import static com.mycompany.wheretogo.RestaurantTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    private RestaurantService restaurantService;

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
    public void testGetAllRestaurants() throws Exception {
        assertMatch(restaurantService.getAll(), BURGER_KING, RESTAURANT_ATEOTU);
    }

    @Test
    public void testUpdateRestaurant() throws Exception {
        Restaurant updatedRestaurant = new Restaurant(BURGER_KING);
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
    public void testValidation() throws Exception {
        validateRootCause(() -> restaurantService.add(new Restaurant(null, " ")), ConstraintViolationException.class);
        validateRootCause(() -> restaurantService.add(new Restaurant(null, null)), ConstraintViolationException.class);
    }
}