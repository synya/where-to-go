package com.mycompany.wheretogo.to;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Objects;

public class VoteTo {
    private Integer id;

    private String restaurantName;

    private LocalDateTime dateTime;

    private Boolean voteDone;

    public VoteTo() {
    }

    public VoteTo(Boolean voteDone) {
        this(null, null, null, voteDone);
    }

    public VoteTo(Integer id, String restaurantName, LocalDateTime dateTime) {
        this(id, restaurantName, dateTime, null);
    }

    public VoteTo(@Nullable Integer id, @Nullable String restaurantName, @Nullable LocalDateTime dateTime, @Nullable Boolean voteDone) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.dateTime = dateTime;
        this.voteDone = voteDone;
    }

    public Integer getId() {
        return id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Boolean getRemark() {
        return voteDone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteTo voteTo = (VoteTo) o;
        return Objects.equals(id, voteTo.id) &&
                Objects.equals(restaurantName, voteTo.restaurantName) &&
                Objects.equals(dateTime, voteTo.dateTime) &&
                Objects.equals(voteDone, voteTo.voteDone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, restaurantName, dateTime, voteDone);
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", restaurantName='" + restaurantName + '\'' +
                ", dateTime=" + dateTime +
                ", voteDone=" + voteDone +
                '}';
    }
}
