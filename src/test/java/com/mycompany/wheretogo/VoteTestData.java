package com.mycompany.wheretogo;

import com.mycompany.wheretogo.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static com.mycompany.wheretogo.RestaurantTestData.*;
import static com.mycompany.wheretogo.model.AbstractBaseEntity.*;

public class VoteTestData {
    public static final int VOTE_START_ID = START_SEQ + 28;

    public static final Vote USER_VOTE1 = new Vote(VOTE_START_ID, BURGER_KING,
            LocalDateTime.of(2019, Month.MARCH, 20, 10, 0, 0));
    public static final Vote USER_VOTE2 = new Vote(VOTE_START_ID + 2, RESTAURANT_ATEOTU,
            LocalDateTime.of(2019, Month.MARCH, 21, 8, 0, 0));

    public static void assertMatch(Vote actual, Vote expected) {
        TestUtil.assertMatch(actual, expected);
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        TestUtil.assertMatch(actual, expected);
    }
}
