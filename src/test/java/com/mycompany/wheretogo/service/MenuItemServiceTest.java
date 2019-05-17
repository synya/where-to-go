package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.MenuItem;
import com.mycompany.wheretogo.util.JpaUtil;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.mycompany.wheretogo.DishTestData.RESTAURANT_ATEOTU_DISH4;
import static com.mycompany.wheretogo.MenuItemTestData.*;
import static com.mycompany.wheretogo.MenuItemTestData.TODAY_MENU_ITEM5;

public class MenuItemServiceTest extends AbstractServiceTest {
    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JpaUtil jpaUtil;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("todayMenuItems").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void testAddMenuItem() throws Exception {
        MenuItem menuItem = new MenuItem(RESTAURANT_ATEOTU_DISH4, LocalDate.of(2019, Month.MARCH, 25), 2000);
        MenuItem addedMenuItem = menuItemService.add(menuItem.getDish().getId(), menuItem.getDate(), menuItem.getPrice());
        menuItem.setId(addedMenuItem.getId());
        assertMatch(menuItemService.getAllBetweenDates(menuItem.getDate(), menuItem.getDate()),
                List.of(menuItem));
    }

    @Test
    public void testGetMenuItem() throws Exception {
        assertMatch(menuItemService.get(RESTAURANTS_MENU_ITEMS_ID), MENU_ITEM1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetMenuItemNotFound() throws Exception {
        menuItemService.get(ENTITY_NOT_FOUND_ID);
    }

    @Test
    public void testGetAllMenuItems() throws Exception {
        assertMatch(menuItemService.getAll(), ALL_MENU_ITEMS);
    }

    @Test
    public void testGetAllTodayMenuItems() throws Exception {
        assertMatch(menuItemService.getAllToday(), TODAY_MENU_ITEMS);
    }

    @Test
    public void testGetAllMenuItemsBetweenDates() throws Exception {
        LocalDate localDate = LocalDate.of(2019, Month.MARCH, 20);
        assertMatch(menuItemService.getAllBetweenDates(localDate, localDate),
                MENU_ITEM1, MENU_ITEM2, MENU_ITEM3, MENU_ITEM6, MENU_ITEM4, MENU_ITEM5);
    }

    @Test
    public void testUpdateMenuItem() throws Exception {
        MenuItem updatedMenuItem = new MenuItem(TODAY_MENU_ITEM1);
        updatedMenuItem.setPrice(200_000);
        menuItemService.update(updatedMenuItem);
        assertMatch(menuItemService.getAllToday(),
                TODAY_MENU_ITEM2, updatedMenuItem, TODAY_MENU_ITEM6, TODAY_MENU_ITEM4, TODAY_MENU_ITEM3, TODAY_MENU_ITEM5);
    }

    @Test
    public void testDeleteMenuItem() throws Exception {
        menuItemService.delete(TODAY_RESTAURANTS_MENU_ITEMS_ID + 1);
        assertMatch(menuItemService.getAllToday(),
                TODAY_MENU_ITEM1, TODAY_MENU_ITEM6, TODAY_MENU_ITEM4, TODAY_MENU_ITEM3, TODAY_MENU_ITEM5);

    }

    @Test(expected = NotFoundException.class)
    public void testDeleteMenuItemNotFound() throws Exception {
        menuItemService.delete(ENTITY_NOT_FOUND_ID);
    }


}