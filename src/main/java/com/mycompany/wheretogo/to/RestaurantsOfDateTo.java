package com.mycompany.wheretogo.to;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class RestaurantsOfDateTo {
    private LocalDate localDate;

    private List<RestaurantTo> restaurants;

    public RestaurantsOfDateTo() {
    }

    public RestaurantsOfDateTo(LocalDate localDate, List<RestaurantTo> restaurants) {
        this.localDate = localDate;
        this.restaurants = restaurants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantsOfDateTo that = (RestaurantsOfDateTo) o;
        return Objects.equals(localDate, that.localDate) &&
                Objects.equals(restaurants, that.restaurants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localDate, restaurants);
    }

    @Override
    public String toString() {
        return "RestaurantsOfDateTo{" +
                "localDate=" + localDate +
                ", restaurants=" + restaurants +
                '}';
    }
}
