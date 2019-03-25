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
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void update(Restaurant restaurant) throws NotFoundException {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }

    @Override
    public Restaurant get(Integer restaurantId) throws NotFoundException {
        return checkNotFoundWithId(restaurantRepository.findById(restaurantId).orElse(null), restaurantId);
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll(SORT_NAME);
    }

    @Override
    public Dish addDish(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    public List<Dish> getAllDishes(Integer restaurantId) {
        return dishRepository.findAll(restaurantId);
    }

    @Override
    public List<MenuItem> getAllMenuItemsBetween(LocalDate startDate, LocalDate endDate) {
        return menuItemRepository.findAllBetween(startDate, endDate);
    }
}
