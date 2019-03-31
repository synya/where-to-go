package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Dish;
import com.mycompany.wheretogo.model.MenuItem;
import com.mycompany.wheretogo.model.Restaurant;
import com.mycompany.wheretogo.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantService {
    Restaurant add(Restaurant restaurant);

    Restaurant get(Integer id) throws NotFoundException;

    void update(Restaurant restaurant) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    List<Restaurant> getAll();

    Dish addDish(Dish dish);

    Dish getDish(Integer id) throws NotFoundException;

    void updateDish(Dish dish) throws NotFoundException;

    void deleteDish(int id) throws NotFoundException;

    List<Dish> getAllDishes(Integer restaurantId);

    MenuItem addMenuItem(Integer dishId, LocalDate date, Integer price);

    MenuItem getMenuItem(Integer id) throws NotFoundException;

    void updateMenuItem(MenuItem menuItem) throws NotFoundException;

    void deleteMenuItem(int id) throws NotFoundException;

    List<MenuItem> getAllMenuItemsByDate(LocalDate date);

    List<MenuItem> getAllMenuItemsBetweenDates(LocalDate startDate, LocalDate endDate);
}
