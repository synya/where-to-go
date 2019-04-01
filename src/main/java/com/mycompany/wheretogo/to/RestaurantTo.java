package com.mycompany.wheretogo.to;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestaurantTo {
    private Integer id;

    private String name;

    private List<DishOfTheDay> dishesOfTheDay = new ArrayList<>();

    private boolean elected;

    public RestaurantTo() {
    }

    public RestaurantTo(Integer id, String name, String dishName, Integer dishPrice, boolean elected) {
        this.id = id;
        this.name = name;
        addDishOfTheDay(dishName, dishPrice);
        this.elected = elected;
    }

    public void addDishOfTheDay(String dishName, Integer dishPrice) {
        dishesOfTheDay.add(new DishOfTheDay(dishName, dishPrice));
    }

    public void setElected(boolean elected) {
        this.elected = elected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTo that = (RestaurantTo) o;
        return elected == that.elected &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(dishesOfTheDay, that.dishesOfTheDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dishesOfTheDay, elected);
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dishesOfTheDay=" + dishesOfTheDay +
                ", elected=" + elected +
                '}';
    }

    public static class DishOfTheDay {
        private String name;

        private Integer price;

        public DishOfTheDay() {
        }

        public DishOfTheDay(String name, Integer price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DishOfTheDay that = (DishOfTheDay) o;
            return Objects.equals(name, that.name) &&
                    Objects.equals(price, that.price);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, price);
        }

        @Override
        public String toString() {
            return "DishOfTheDay{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
}
