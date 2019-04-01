package com.mycompany.wheretogo.web.restaurant;

import com.mycompany.wheretogo.service.RestaurantService;
import com.mycompany.wheretogo.to.RestaurantTo;
import com.mycompany.wheretogo.web.AbstractRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.mycompany.wheretogo.util.RestaurantUtil.groupMenuItemsByRestaurant;

@RestController
@RequestMapping(RestaurantRestController.REST_URL)
public class RestaurantRestController extends AbstractRestController {
    static final String REST_URL = REST_BASE_URL + "/restaurants";

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantTo> get() {
        return groupMenuItemsByRestaurant(restaurantService.getAllTodayMenuItems(), null);
    }
}
