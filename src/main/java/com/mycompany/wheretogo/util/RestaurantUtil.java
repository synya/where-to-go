package com.mycompany.wheretogo.util;

import com.mycompany.wheretogo.model.MenuItem;
import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.to.RestaurantTo;
import com.mycompany.wheretogo.to.RestaurantsTo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RestaurantUtil {
    private RestaurantUtil() {
    }

    public static List<RestaurantTo> groupByRestaurant(List<MenuItem> menuItems) {
        return new ArrayList<>(fetchMenuItemsOfRestaurants(menuItems).values());
    }


    public static List<RestaurantTo> groupByRestaurantWithVote(List<MenuItem> menuItems, Vote vote) {
        Map<Integer, RestaurantTo> linkedHashMap = fetchMenuItemsOfRestaurants(menuItems);
        if (vote != null) {
            linkedHashMap.computeIfPresent(vote.getRestaurant().getId(), (i, r) -> {
                r.setElected(true);
                return r;
            });
        }
        return new ArrayList<>(linkedHashMap.values());
    }

    public static List<RestaurantsTo> groupByDateAndRestaurant(List<MenuItem> menuItems) {
        return menuItems.stream()
                .collect(Collectors.groupingBy(MenuItem::getDate)).entrySet().stream()
                .map(e -> new RestaurantsTo(e.getKey(), groupByRestaurant(e.getValue())))
                .collect(Collectors.toList());
    }

    private static Map<Integer, RestaurantTo> fetchMenuItemsOfRestaurants(List<MenuItem> menuItems) {
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
        return linkedHashMap;
    }
}
