package com.mycompany.wheretogo.web.restaurant;

import com.mycompany.wheretogo.service.RestaurantService;
import com.mycompany.wheretogo.to.RestaurantsTo;
import com.mycompany.wheretogo.web.AbstractRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.mycompany.wheretogo.util.MenuItemsUtil.asRestaurantsTo;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController extends AbstractRestController {
    static final String REST_URL = REST_BASE_URL + "/restaurants";

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public RestaurantsTo getRestaurants() {
        return asRestaurantsTo(LocalDate.now(), restaurantService.getAllTodayMenuItems());
    }
}
