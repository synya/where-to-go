package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Dish;
import com.mycompany.wheretogo.model.MenuItem;
import com.mycompany.wheretogo.model.Restaurant;
import com.mycompany.wheretogo.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantService {
    Restaurant add(Restaurant restaurant);

    void update(Restaurant restaurant) throws NotFoundException;

    List<Restaurant> getAll();

    Dish addDish(Dish dish);

    void updateDish(Dish dish) throws NotFoundException;

    List<Dish> getAllDishes(Integer restaurantId);

    MenuItem addMenuItem(Integer dishId, LocalDate date, Integer price);

    List<MenuItem> getAllMenuItemsByDate(LocalDate date);

    List<MenuItem> getAllMenuItemsBetweenDates(LocalDate startDate, LocalDate endDate);
}
