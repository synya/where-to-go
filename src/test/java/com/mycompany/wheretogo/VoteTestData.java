package com.mycompany.wheretogo;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static com.mycompany.wheretogo.RestaurantTestData.*;
import static com.mycompany.wheretogo.model.AbstractBaseEntity.*;

public class VoteTestData {
    public static final int VOTE_START_ID = START_SEQ + 28;

    public static final Vote USER_VOTE1 = new Vote(VOTE_START_ID, BURGER_KING,
            LocalDateTime.of(2019, Month.MARCH, 20, 10, 0, 0));
    public static final Vote USER_VOTE2 = new Vote(VOTE_START_ID + 2, RESTAURANT_ATEOTU,
            LocalDateTime.of(2019, Month.MARCH, 21, 8, 0, 0));

    public static Vote getNewVote() {
        return new Vote(null, RESTAURANT_ATEOTU, LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 45)));
    }

    public static Vote getUpdatedVote() {
        return new Vote(null, BURGER_KING, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 30)));
    }
}
