package com.mycompany.wheretogo.util;

import com.mycompany.wheretogo.model.MenuItem;
import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.to.RestaurantTo;
import com.mycompany.wheretogo.to.RestaurantsTo;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MenuItemsUtil {
    private MenuItemsUtil() {
    }

    public static Set<Integer> asSetOfRestaurantId(List<MenuItem> menuItems) {
        return menuItems.stream()
                .map(mi -> mi.getDish().getRestaurant().getId())
                .collect(Collectors.toSet());
    }

    public static RestaurantsTo asRestaurantsTo(LocalDate localDate, List<MenuItem> menuItems) {
        return new RestaurantsTo(localDate, new ArrayList<>(groupByRestaurantId(menuItems, null).values()));
    }

    public static RestaurantsTo asRestaurantsToWithVote(LocalDate localDate, List<MenuItem> menuItems, @Nullable Vote vote) {
        Map<Integer, RestaurantTo> linkedHashMap = groupByRestaurantId(menuItems, false);
        if (vote != null) {
            linkedHashMap.computeIfPresent(vote.getRestaurant().getId(), (i, r) -> {
                r.setElected(true);
                return r;
            });
        }
        return new RestaurantsTo(localDate, new ArrayList<>(linkedHashMap.values()));
    }

    public static List<RestaurantsTo> asListOfRestaurantsTo(List<MenuItem> menuItems) {
        return menuItems.stream()
                .collect(Collectors.groupingBy(MenuItem::getDate)).entrySet().stream()
                .map(e -> asRestaurantsTo(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    private static Map<Integer, RestaurantTo> groupByRestaurantId(List<MenuItem> menuItems, @Nullable Boolean elected) {
        Map<Integer, RestaurantTo> linkedHashMap = new LinkedHashMap<>();
        menuItems.forEach(mi -> {
            Integer restaurantId = mi.getDish().getRestaurant().getId();
            String restaurantName = mi.getDish().getRestaurant().getName();
            linkedHashMap.merge(restaurantId,
                    new RestaurantTo(restaurantId, restaurantName, mi.getDish().getName(), mi.getPrice(), elected),
                    (o, n) -> {
                        o.addDishOfTheDay(mi.getDish().getName(), mi.getPrice());
                        return o;
                    });
        });
        return linkedHashMap;
    }
}
