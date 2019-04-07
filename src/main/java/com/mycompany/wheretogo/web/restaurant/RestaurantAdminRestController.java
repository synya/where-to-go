package com.mycompany.wheretogo.web.restaurant;

import com.mycompany.wheretogo.model.Dish;
import com.mycompany.wheretogo.model.Restaurant;
import com.mycompany.wheretogo.service.RestaurantService;
import com.mycompany.wheretogo.web.AbstractRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.mycompany.wheretogo.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = RestaurantAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantAdminRestController extends AbstractRestController {
    static final String REST_URL = REST_BASE_URL + "/management/restaurants";

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/{restaurantId}")
    public Restaurant get(@PathVariable int restaurantId) {
        return restaurantService.get(restaurantId);
    }

    @GetMapping("/{restaurantId}/dishes/{dishId}")
    public Dish getDish(@PathVariable int restaurantId, @PathVariable int dishId) {
        return restaurantService.getDish(dishId);
    }


    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") Integer restaurantId) {
        restaurantService.delete(restaurantId);
    }

    @DeleteMapping("/{restaurantId}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable int restaurantId, @PathVariable int dishId) {
        restaurantService.deleteDish(dishId);
    }

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable int restaurantId) {
        assureIdConsistent(restaurant, restaurantId);
        restaurantService.update(restaurant);
    }

    @PutMapping(value = "/{restaurantId}/dishes/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateDish(@RequestBody Dish dish, @PathVariable int restaurantId, @PathVariable int dishId) {
        assureIdConsistent(dish, dishId);
        restaurantService.updateDish(dish, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        Restaurant created = restaurantService.add(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping(value = "/{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDishWithLocation(@RequestBody Dish dish, @PathVariable int restaurantId) {
        Dish created = restaurantService.addDish(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}/dishes/{dishId}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/{restaurantId}/dishes")
    public List<Dish> getAllDishes(@PathVariable int restaurantId) {
        return restaurantService.getAllDishes(restaurantId);
    }

}