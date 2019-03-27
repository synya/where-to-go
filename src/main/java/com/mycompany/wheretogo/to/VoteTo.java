package com.mycompany.wheretogo.to;

import java.time.LocalDateTime;

public class VoteTo {
    private final Integer id;

    private final Integer restaurantId;

    private final String restaurantName;

    private final LocalDateTime dateTime;

    public VoteTo(Integer id, Integer restaurantId, LocalDateTime dateTime) {
        this(id, restaurantId, null, dateTime);
    }

    public VoteTo(Integer id, Integer restaurantId, String restaurantName, LocalDateTime dateTime) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.dateTime = dateTime;
    }

    public Integer getId() {
        return id;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", restaurantId=" + restaurantId +
                ", restaurantName='" + restaurantName + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
