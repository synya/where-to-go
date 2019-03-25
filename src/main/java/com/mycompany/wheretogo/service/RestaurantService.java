package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Dish;
import com.mycompany.wheretogo.model.MenuItem;
import com.mycompany.wheretogo.model.Restaurant;
import com.mycompany.wheretogo.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantService {

    Restaurant add(Restaurant restaurant);

    Restaurant get(Integer restaurantId) throws NotFoundException;

    void update(Restaurant restaurant) throws NotFoundException;

    List<Restaurant> getAll();

    Dish addDish(Dish dish);

    List<Dish> getAllDishes(Integer restaurantId);

    List<MenuItem> getAllMenuItemsBetween(LocalDate startDate, LocalDate endDate);

}
