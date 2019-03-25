package com.mycompany.wheretogo.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"dish_id", "date"}, name = "menus_unique_dish_date_idx")})
public class MenuItem extends AbstractBaseEntity {
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Dish dish;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "price", nullable = false)
    @Range(min = 0, max = 1_000_000_00)
    private Integer price;

    public MenuItem() {
    }

    public MenuItem(@NotNull Dish dish, @NotNull LocalDate date, @Range(min = 0, max = 1_000_000_00) Integer price) {
        this(null, dish, date, price);
    }

    public MenuItem(Integer id, @NotNull Dish dish, @NotNull LocalDate date, @Range(min = 0, max = 1_000_000_00) Integer price) {
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
