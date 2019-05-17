package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Dish;
import com.mycompany.wheretogo.model.MenuItem;
import com.mycompany.wheretogo.model.Restaurant;
import com.mycompany.wheretogo.repository.DishRepository;
import com.mycompany.wheretogo.repository.MenuItemRepository;
import com.mycompany.wheretogo.repository.RestaurantRepository;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.mycompany.wheretogo.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {
    private static final Sort SORT_NAME = new Sort(Sort.Direction.ASC, "name");

    private final RestaurantRepository restaurantRepository;

    private final MenuItemRepository menuItemRepository;

    private final DishRepository dishRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository, DishRepository dishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.dishRepository = dishRepository;
    }

    public Restaurant add(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    public Restaurant get(Integer id) throws NotFoundException {
        return checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll(SORT_NAME);
    }

    public void update(Restaurant restaurant) throws NotFoundException {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
    }

    @Transactional
    public Dish addDish(Dish dish, Integer restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        Assert.notNull(restaurantId, "restaurantId must not be null");
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        return dishRepository.save(dish);
    }

    public Dish getDish(Integer id) throws NotFoundException {
        return checkNotFoundWithId(dishRepository.findById(id).orElse(null), id);
    }

    public List<Dish> getAllDishes(Integer restaurantId) {
        Assert.notNull(restaurantId, "restaurantId must not be null");
        return dishRepository.findAll(restaurantId);
    }

    @Transactional
    public void updateDish(Dish dish, Integer restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        Assert.notNull(restaurantId, "restaurantId must not be null");
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        checkNotFoundWithId(dishRepository.save(dish), dish.getId());
    }

    public void deleteDish(int id) throws NotFoundException {
        checkNotFoundWithId(dishRepository.delete(id) != 0, id);
    }

    @CacheEvict(value = "todayMenuItems", allEntries = true)
    @Transactional
    public MenuItem addMenuItem(Integer dishId, LocalDate date, Integer price) {
        Assert.notNull(dishId, "dishId must not be null");
        Assert.notNull(date, "date must not be null");
        Assert.notNull(date, "price must not be null");
        return menuItemRepository.save(new MenuItem(dishRepository.getOne(dishId), date, price));
    }

    public MenuItem getMenuItem(Integer id) throws NotFoundException {
        return checkNotFoundWithId(menuItemRepository.findById(id).orElse(null), id);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Cacheable("todayMenuItems")
    public List<MenuItem> getAllTodayMenuItems() {
        return menuItemRepository.findAllByDate(LocalDate.now());
    }

    public List<MenuItem> getAllMenuItemsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return menuItemRepository.findAllBetweenDates(startDate, endDate);
    }

    @CacheEvict(value = "todayMenuItems", allEntries = true)
    public void updateMenuItem(MenuItem menuItem) throws NotFoundException {
        Assert.notNull(menuItem, "menuItem must not be null");
        checkNotFoundWithId(menuItemRepository.save(menuItem), menuItem.getId());
    }

    @CacheEvict(value = "todayMenuItems", allEntries = true)
    public void deleteMenuItem(int id) throws NotFoundException {
        checkNotFoundWithId(menuItemRepository.delete(id) != 0, id);
    }
}
