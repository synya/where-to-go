package com.mycompany.wheretogo.to;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class RestaurantsTo {
    private LocalDate localDate;

    private List<RestaurantTo> restaurants;

    public RestaurantsTo() {
    }

    public RestaurantsTo(LocalDate localDate, List<RestaurantTo> restaurants) {
        this.localDate = localDate;
        this.restaurants = restaurants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantsTo that = (RestaurantsTo) o;
        return Objects.equals(localDate, that.localDate) &&
                Objects.equals(restaurants, that.restaurants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localDate, restaurants);
    }

    @Override
    public String toString() {
        return "RestaurantsTo{" +
                "localDate=" + localDate +
                ", restaurants=" + restaurants +
                '}';
    }
}
