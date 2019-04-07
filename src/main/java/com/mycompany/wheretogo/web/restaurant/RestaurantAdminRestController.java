package com.mycompany.wheretogo.web.restaurant;

import com.mycompany.wheretogo.model.Dish;
import com.mycompany.wheretogo.model.MenuItem;
import com.mycompany.wheretogo.model.Restaurant;
import com.mycompany.wheretogo.service.RestaurantService;
import com.mycompany.wheretogo.to.RestaurantTo;
import com.mycompany.wheretogo.to.RestaurantsTo;
import com.mycompany.wheretogo.web.AbstractRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.mycompany.wheretogo.util.RestaurantUtil.groupByDateAndRestaurant;
import static com.mycompany.wheretogo.util.RestaurantUtil.groupByRestaurant;
import static com.mycompany.wheretogo.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = RestaurantAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantAdminRestController extends AbstractRestController {
    static final String REST_URL = REST_BASE_URL + "/management/restaurants";

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        Restaurant created = restaurantService.add(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{restaurantId}")
    public Restaurant get(@PathVariable int restaurantId) {
        return restaurantService.get(restaurantId);
    }

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable int restaurantId) {
        assureIdConsistent(restaurant, restaurantId);
        restaurantService.update(restaurant);
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") Integer restaurantId) {
        restaurantService.delete(restaurantId);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @PostMapping(value = "/{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Dish> createDishWithLocation(@RequestBody Dish dish, @PathVariable int restaurantId) {
        Dish created = restaurantService.addDish(dish, restaurantId);
        created.setRestaurant(restaurantService.get(restaurantId));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}/dishes/{dishId}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{restaurantId}/dishes/{dishId}")
    public Dish getDish(@PathVariable int restaurantId, @PathVariable int dishId) {
        return restaurantService.getDish(dishId);
    }

    @PutMapping(value = "/{restaurantId}/dishes/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateDish(@RequestBody Dish dish, @PathVariable int restaurantId, @PathVariable int dishId) {
        assureIdConsistent(dish, dishId);
        restaurantService.updateDish(dish, restaurantId);
    }

    @DeleteMapping("/{restaurantId}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable int restaurantId, @PathVariable int dishId) {
        restaurantService.deleteDish(dishId);
    }

    @GetMapping("/{restaurantId}/dishes")
    public List<Dish> getAllDishes(@PathVariable int restaurantId) {
        return restaurantService.getAllDishes(restaurantId);
    }

    @GetMapping("/menus/daily/today/items")
    public List<MenuItem> getTodayMenuItems() {
        return restaurantService.getAllTodayMenuItems();
    }

    @PostMapping("/menus/daily/today/items")
    @Transactional
    public ResponseEntity<MenuItem> createTodayMenuItem(@RequestParam("dishId") int dishId, @RequestParam("price") int price) {
        MenuItem created = restaurantService.addMenuItem(dishId, LocalDate.now(), price);
        created.setDish(restaurantService.getDish(dishId));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/menus/daily/today/items/{menuItemId}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/menus/daily/today/items/{menuItemId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateMenuItem(@RequestBody MenuItem menuItem, @PathVariable int menuItemId) {
        assureIdConsistent(menuItem, menuItemId);
        restaurantService.updateMenuItem(menuItem);
    }

    @DeleteMapping("/menus/daily/today/items/{menuItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuItem(@PathVariable int menuItemId) {
        restaurantService.deleteMenuItem(menuItemId);
    }

    @GetMapping("/menus/daily/today")
    public List<RestaurantTo> getTodayMenus() {
        return groupByRestaurant(restaurantService.getAllTodayMenuItems());
    }

    @GetMapping("/menus/daily")
    public List<RestaurantsTo> getAllMenus() {
        return groupByDateAndRestaurant(restaurantService.getAllMenuItems());
    }

    @GetMapping("/menus/daily/between")
    public List<RestaurantsTo> getAllMenusBetweenDates(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                                                       @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return groupByDateAndRestaurant(restaurantService.getAllMenuItemsBetweenDates(startDate, endDate));
    }
}