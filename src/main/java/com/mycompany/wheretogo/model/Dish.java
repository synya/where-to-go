package com.mycompany.wheretogo.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "name"}, name = "dishes_unique_restaurant_name_idx")})
public class Dish extends AbstractNamedEntity {

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(String name) {
        this(null, name, null);
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
