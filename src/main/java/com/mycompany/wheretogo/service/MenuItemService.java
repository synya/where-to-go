package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.MenuItem;
import com.mycompany.wheretogo.repository.DishRepository;
import com.mycompany.wheretogo.repository.MenuItemRepository;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.mycompany.wheretogo.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    private final DishRepository dishRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository, DishRepository dishRepository) {
        this.menuItemRepository = menuItemRepository;
        this.dishRepository = dishRepository;
    }

    @CacheEvict(value = "todayMenuItems", allEntries = true)
    @Transactional
    public MenuItem add(int dishId, LocalDate date, int price) {
        Assert.notNull(date, "date must not be null");
        Assert.notNull(date, "price must not be null");
        return menuItemRepository.save(new MenuItem(dishRepository.getOne(dishId), date, price));
    }

    public MenuItem get(int id) throws NotFoundException {
        return checkNotFoundWithId(menuItemRepository.findById(id).orElse(null), id);
    }

    public List<MenuItem> getAll() {
        return menuItemRepository.findAll();
    }

    @Cacheable("todayMenuItems")
    public List<MenuItem> getAllToday() {
        return menuItemRepository.findAllByDate(LocalDate.now());
    }

    public List<MenuItem> getAllBetweenDates(LocalDate startDate, LocalDate endDate) {
        return menuItemRepository.findAllBetweenDates(startDate, endDate);
    }

    @CacheEvict(value = "todayMenuItems", allEntries = true)
    public void update(MenuItem menuItem) throws NotFoundException {
        Assert.notNull(menuItem, "menuItem must not be null");
        checkNotFoundWithId(menuItemRepository.save(menuItem), menuItem.getId());
    }

    @CacheEvict(value = "todayMenuItems", allEntries = true)
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(menuItemRepository.delete(id) != 0, id);
    }
}
