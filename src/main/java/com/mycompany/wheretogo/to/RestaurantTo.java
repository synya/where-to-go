package com.mycompany.wheretogo.to;

import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class RestaurantTo {
    private final Integer id;

    private final String name;

    private final List<Pair<String, Integer>> menuItems = new ArrayList<>();

    public RestaurantTo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Pair<String, Integer>> getMenuItems() {
        return menuItems;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", menuItems=" + menuItems +
                '}';
    }
}
