package com.mycompany.wheretogo.util;

import com.mycompany.wheretogo.model.MenuItem;
import com.mycompany.wheretogo.to.RestaurantTo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RestaurantUtil {
    private RestaurantUtil() {
    }

    public static List<RestaurantTo> groupMenuItemsByRestaurant(List<MenuItem> menuItems, Integer electedRestaurantId) {
        Map<Integer, RestaurantTo> linkedHashMap = new LinkedHashMap<>();
        menuItems.forEach(mi -> {
            Integer restaurantId = mi.getDish().getRestaurant().getId();
            String restaurantName = mi.getDish().getRestaurant().getName();
            linkedHashMap.merge(restaurantId,
                    new RestaurantTo(restaurantId, restaurantName, mi.getDish().getName(), mi.getPrice(), false),
                    (o, n) -> {
                        o.addDishOfTheDay(mi.getDish().getName(), mi.getPrice());
                        return o;
                    });
        });
        if (electedRestaurantId != null) {
            linkedHashMap.computeIfPresent(electedRestaurantId, (i, r) -> {
                r.setElected(true);
                return r;
            });
        }
        return new ArrayList<>(linkedHashMap.values());
    }
}
