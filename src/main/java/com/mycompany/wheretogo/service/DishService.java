package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Dish;
import com.mycompany.wheretogo.repository.DishRepository;
import com.mycompany.wheretogo.repository.RestaurantRepository;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.mycompany.wheretogo.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {
    private final RestaurantRepository restaurantRepository;

    private final DishRepository dishRepository;

    @Autowired
    public DishService(RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }


    @Transactional
    public Dish add(Dish dish, Integer restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        Assert.notNull(restaurantId, "restaurantId must not be null");
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        return dishRepository.save(dish);
    }

    public Dish get(Integer id) throws NotFoundException {
        return checkNotFoundWithId(dishRepository.findById(id).orElse(null), id);
    }

    public List<Dish> getAll(Integer restaurantId) {
        Assert.notNull(restaurantId, "restaurantId must not be null");
        return dishRepository.findAll(restaurantId);
    }

    @Transactional
    public void update(Dish dish, Integer restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        Assert.notNull(restaurantId, "restaurantId must not be null");
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        checkNotFoundWithId(dishRepository.save(dish), dish.getId());
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(dishRepository.delete(id) != 0, id);
    }
}
