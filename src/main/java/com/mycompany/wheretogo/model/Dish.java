package com.mycompany.wheretogo.model;

public class Dish extends AbstractNamedEntity {

    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(String name, Restaurant restaurant) {
        this(null, name, restaurant);
    }

    public Dish(Integer id, String name, Restaurant restaurant) {
        super(id, name);
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", restaurant='" + restaurant + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
