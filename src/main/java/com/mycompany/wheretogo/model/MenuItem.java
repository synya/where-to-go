package com.mycompany.wheretogo.model;

import com.mycompany.wheretogo.View;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "menu_items", uniqueConstraints = {@UniqueConstraint(columnNames = {"dish_id", "date"}, name = "menu_items_unique_dish_date_idx")})
public class MenuItem extends AbstractBaseEntity {
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    private Dish dish;

    @Column(name = "date", nullable = false)
    @NotNull(groups = View.Persist.class)
    private LocalDate date;

    @Column(name = "price", nullable = false)
    @Range(min = 0, max = 1_000_000_00)
    private Integer price;

    public MenuItem() {
    }

    public MenuItem(MenuItem menuItem) {
        this(menuItem.getId(), menuItem.getDish(), menuItem.getDate(), menuItem.getPrice());
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
