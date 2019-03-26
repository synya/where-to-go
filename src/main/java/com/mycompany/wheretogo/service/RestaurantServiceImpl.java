package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Dish;
import com.mycompany.wheretogo.model.MenuItem;
import com.mycompany.wheretogo.model.Restaurant;
import com.mycompany.wheretogo.repository.DishRepository;
import com.mycompany.wheretogo.repository.MenuItemRepository;
import com.mycompany.wheretogo.repository.RestaurantRepository;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.mycompany.wheretogo.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private static final Sort SORT_NAME = new Sort(Sort.Direction.ASC, "name");

    private final RestaurantRepository restaurantRepository;

    private final MenuItemRepository menuItemRepository;

    private final DishRepository dishRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository, DishRepository dishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    public Restaurant add(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void update(Restaurant restaurant) throws NotFoundException {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }

    @Override
    public Restaurant get(Integer restaurantId) throws NotFoundException {
        Assert.notNull(restaurantId, "restaurantId must not be null");
        return checkNotFoundWithId(restaurantRepository.findById(restaurantId).orElse(null), restaurantId);
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll(SORT_NAME);
    }

    @Override
    public Dish addDish(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return dishRepository.save(dish);
    }

    @Override
    public Dish getDish(Integer dishId) throws NotFoundException {
        Assert.notNull(dishId, "dishId must not be null");
        return checkNotFoundWithId(dishRepository.findById(dishId).orElse(null), dishId);
    }

    @Override
    public void updateDish(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(dishRepository.save(dish), dish.getId());
    }

    @Override
    public List<Dish> getAllDishes(Integer restaurantId) {
        Assert.notNull(restaurantId, "restaurantId must not be null");
        return dishRepository.findAll(restaurantId);
    }

    @Override
    @Transactional
    public MenuItem addMenuItem(Integer dishId, LocalDate date, Integer price) {
        Assert.notNull(dishId, "dishId must not be null");
        Assert.notNull(date, "date must not be null");
        Assert.notNull(date, "price must not be null");
        return menuItemRepository.save(new MenuItem(dishRepository.getOne(dishId), date, price));
    }

    @Override
    public List<MenuItem> getAllMenuItemsBetween(LocalDate startDate, LocalDate endDate) {
        Assert.notNull(startDate, "startDate must not be null");
        Assert.notNull(endDate, "endDate must not be null");
        return menuItemRepository.findAllBetween(startDate, endDate);
    }
}
