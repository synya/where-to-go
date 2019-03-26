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
    public static final Vote ADMIN_VOTE1 = new Vote(VOTE_START_ID + 1, BURGER_KING,
            LocalDateTime.of(2019, Month.MARCH, 20, 9, 0, 0));
    public static final Vote USER_VOTE2 = new Vote(VOTE_START_ID + 2, RESTAURANT_ATEOTU,
            LocalDateTime.of(2019, Month.MARCH, 21, 8, 0, 0));
    public static final Vote ADMIN_VOTE2 = new Vote(VOTE_START_ID + 3, RESTAURANT_ATEOTU,
            LocalDateTime.of(2019, Month.MARCH, 21, 9, 30, 0));

    public static VoteTo getNewVoteTo() {
        return new VoteTo(null, RESTAURANT_ATEOTU_ID, LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 45)));
    }

    public static Vote getNewVote() {
        return new Vote(null, RESTAURANT_ATEOTU, LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 45)));
    }

    public static VoteTo getUpdatedVoteTo() {
        return new VoteTo(null, BURGER_KING_ID, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 30)));
    }

    public static VoteTo getTodayTooLateVoteTo() {
        return new VoteTo(null, BURGER_KING_ID, LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 1)));
    }

    public static VoteTo getOutdatedVoteTo() {
        return new VoteTo(null, BURGER_KING_ID, LocalDateTime.of(2019, Month.MARCH, 1, 0, 0, 0));
    }
}
