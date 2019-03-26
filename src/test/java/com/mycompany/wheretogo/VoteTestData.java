package com.mycompany.wheretogo;

import com.mycompany.wheretogo.model.Vote;

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
    public static final Vote ADMIN_VOTE1 = new Vote(VOTE_START_ID + 1, BURGER_KING,
            LocalDateTime.of(2019, Month.MARCH, 20, 9, 0, 0));
    public static final Vote USER_VOTE2 = new Vote(VOTE_START_ID + 2, RESTAURANT_ATEOTU,
            LocalDateTime.of(2019, Month.MARCH, 21, 8, 0, 0));
    public static final Vote ADMIN_VOTE2 = new Vote(VOTE_START_ID + 3, RESTAURANT_ATEOTU,
            LocalDateTime.of(2019, Month.MARCH, 21, 9, 30, 0));

    public static Vote getNewVote() {
        Vote vote = new Vote();
        vote.setDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 45)));
        return vote;
    }

    public static Vote getUpdatedVote() {
        Vote vote = new Vote();
        vote.setDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 30)));
        return vote;
    }

    public static Vote getOutdatedVote() {
        Vote vote = new Vote();
        vote.setDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 1)));
        return vote;
    }
}
