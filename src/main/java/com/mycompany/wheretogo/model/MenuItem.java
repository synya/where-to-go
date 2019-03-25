package com.mycompany.wheretogo.model;

import java.time.LocalDate;

public class MenuItem extends AbstractBaseEntity {
    private Dish dish;

    private LocalDate date;

    private Integer price;

    public MenuItem() {
    }

    public MenuItem(Dish dish, LocalDate date, Integer price) {
        this(null, dish, date, price);
    }

    public MenuItem(Integer id, Dish dish, LocalDate date, Integer price) {
        super(id);
        this.dish = dish;
        this.date = date;
        this.price = price;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", dish=" + dish +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}
