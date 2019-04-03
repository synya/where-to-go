package com.mycompany.wheretogo.to;

import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Objects;

public class VoteTo {
    private Integer id;

    private String restaurantName;

    private LocalDateTime dateTime;

    public VoteTo() {
    }

    public VoteTo(@NonNull Integer id, @NonNull String restaurantName, @NonNull LocalDateTime dateTime) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteTo voteTo = (VoteTo) o;
        return Objects.equals(id, voteTo.id) &&
                Objects.equals(restaurantName, voteTo.restaurantName) &&
                Objects.equals(dateTime, voteTo.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, restaurantName, dateTime);
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", restaurantName='" + restaurantName + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
