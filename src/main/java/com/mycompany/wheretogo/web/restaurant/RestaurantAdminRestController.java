package com.mycompany.wheretogo.web.restaurant;

import com.mycompany.wheretogo.model.Dish;
import com.mycompany.wheretogo.model.MenuItem;
import com.mycompany.wheretogo.model.Restaurant;
import com.mycompany.wheretogo.service.DishService;
import com.mycompany.wheretogo.service.MenuItemService;
import com.mycompany.wheretogo.service.RestaurantService;
import com.mycompany.wheretogo.to.RestaurantsTo;
import com.mycompany.wheretogo.util.exception.IllegalRequestDataException;
import com.mycompany.wheretogo.web.AbstractRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.mycompany.wheretogo.util.MenuItemsUtil.asListOfRestaurantsTo;
import static com.mycompany.wheretogo.util.ValidationUtil.assureIdConsistent;
import static com.mycompany.wheretogo.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantAdminRestController extends AbstractRestController {
    static final String REST_URL = REST_BASE_URL + "/management/restaurants";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DishService dishService;

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        Restaurant created = restaurantService.add(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}")
                .buildAndExpand(created.getId()).toUri();
        log.info("added new restaurant with id = {}", created.getId());
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{restaurantId}")
    public Restaurant get(@PathVariable int restaurantId) {
        return restaurantService.get(restaurantId);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int restaurantId) {
        assureIdConsistent(restaurant, restaurantId);
        restaurantService.update(restaurant);
        log.info("updated restaurant with id = {}", restaurantId);
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") int restaurantId) {
        restaurantService.delete(restaurantId);
        log.info("deleted restaurant with id = {}", restaurantId);
    }

    @PostMapping(value = "/{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Dish> createDishWithLocation(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        checkNew(dish);
        Dish created = dishService.add(dish, restaurantId);
        created.setRestaurant(restaurantService.get(restaurantId));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}/dishes/{dishId}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        log.info("added new dish with id = {} for restaurant with id = {}", created.getId(), restaurantId);
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{restaurantId}/dishes/{dishId}")
    public Dish getDish(@PathVariable int restaurantId, @PathVariable int dishId) {
        return dishService.get(dishId);
    }

    @GetMapping("/{restaurantId}/dishes")
    public List<Dish> getAllDishes(@PathVariable int restaurantId) {
        return dishService.getAll(restaurantId);
    }

    @PutMapping(value = "/{restaurantId}/dishes/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateDish(@Valid @RequestBody Dish dish, @PathVariable int restaurantId, @PathVariable int dishId) {
        assureIdConsistent(dish, dishId);
        dishService.update(dish, restaurantId);
        log.info("updated dish with id = {} for restaurant with id = {}", dishId, restaurantId);
    }

    @DeleteMapping("/{restaurantId}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable int restaurantId, @PathVariable int dishId) {
        dishService.delete(dishId);
        log.info("deleted dish with id = {} for restaurant with id = {}", dishId, restaurantId);
    }

    @PostMapping("/menus/daily/today/items")
    @Transactional
    public ResponseEntity<MenuItem> createTodayMenuItem(@RequestParam("dishId") int dishId, @RequestParam("price") int price) {
        if (price < 0 || price > 1_000_000_00) {
            throw new IllegalRequestDataException("price must be in range [0...100000000]");
        }
        MenuItem created = menuItemService.add(dishId, LocalDate.now(), price);
        created.setDish(dishService.get(dishId));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/menus/daily/today/items/{menuItemId}")
                .buildAndExpand(created.getId()).toUri();
        log.info("added new menu item with id = {} from dish with id = {}", created.getId(), dishId);
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "/menus/daily/today/items/{menuItemId}")
    public MenuItem getMenuItem(@PathVariable int menuItemId) {
        return menuItemService.get(menuItemId);
    }

    @GetMapping("/menus/daily/today/items")
    public List<MenuItem> getTodayMenuItems() {
        return menuItemService.getAllToday();
    }

    @PutMapping(value = "/menus/daily/today/items/{menuItemId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateMenuItem(@Valid @RequestBody MenuItem menuItem, @PathVariable int menuItemId) {
        assureIdConsistent(menuItem, menuItemId);
        menuItemService.update(menuItem);
        log.info("updated menu item with id = {}", menuItem.getId());
    }

    @DeleteMapping("/menus/daily/today/items/{menuItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuItem(@PathVariable int menuItemId) {
        menuItemService.delete(menuItemId);
        log.info("deleted menu item with id = {}", menuItemId);
    }

    @GetMapping("/menus/daily")
    public List<RestaurantsTo> getAllMenus() {
        return asListOfRestaurantsTo(menuItemService.getAll());
    }

    @GetMapping("/menus/daily/between")
    public List<RestaurantsTo> getAllMenusBetweenDates(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                                                       @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return asListOfRestaurantsTo(menuItemService.getAllBetweenDates(startDate, endDate));
    }
}