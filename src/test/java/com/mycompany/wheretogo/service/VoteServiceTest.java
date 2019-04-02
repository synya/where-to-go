package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.util.exception.OutOfDateTimeException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static com.mycompany.wheretogo.RestaurantTestData.*;
import static com.mycompany.wheretogo.UserTestData.ADMIN_ID;
import static com.mycompany.wheretogo.UserTestData.USER_ID;
import static com.mycompany.wheretogo.VoteTestData.*;
import static com.mycompany.wheretogo.VoteTestData.assertMatch;

public class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    private VoteService voteService;

    @Test
    public void testAddVote() throws Exception {
        Vote newVote = new Vote(null, RESTAURANT_ATEOTU, LocalDate.now(), LocalTime.of(9, 45));
        Vote addedVote = voteService.add(newVote, RESTAURANT_ATEOTU_ID, ADMIN_ID);
        newVote.setId(addedVote.getId());
        newVote.setRestaurant(RESTAURANT_ATEOTU);
        assertMatch(newVote, addedVote);
        assertMatch(voteService.getToday(ADMIN_ID), newVote);
    }

    @Test
    public void testUpdateVote() throws Exception {
        Vote updatedVote = voteService.getToday(USER_ID);
        updatedVote.setTime(LocalTime.of(10, 20));
        voteService.update(updatedVote, BURGER_KING_ID, USER_ID);
        assertMatch(voteService.getToday(USER_ID), updatedVote);
    }

    @Test(expected = OutOfDateTimeException.class)
    public void testUpdateVoteOutdated() throws Exception {
        Vote updatedVote = voteService.getToday(USER_ID);
        updatedVote.setTime(LocalTime.of(11, 01));
        voteService.update(updatedVote, BURGER_KING_ID, USER_ID);
    }

    @Test(expected = OutOfDateTimeException.class)
    public void testAddVoteOutdated() throws Exception {
        Vote vote = new Vote(null, RESTAURANT_ATEOTU, LocalDate.of(2019, Month.MARCH, 19), LocalTime.now());
        voteService.add(vote, BURGER_KING_ID, USER_ID);
    }

    @Test
    public void testGetToday() throws Exception {
        assertMatch(voteService.getToday(USER_ID), TODAY_USER_VOTE);
    }

    @Test
    public void testGetTodayVoteNotFound() throws Exception {
        assertMatch(voteService.getToday(ADMIN_ID), null);
    }

    @Test
    public void testGetAllVotes() throws Exception {
        assertMatch(voteService.getAll(USER_ID), TODAY_USER_VOTE, USER_VOTE2, USER_VOTE1);
    }

    @Test
    public void testGetAllVotesBetween() throws Exception {
        assertMatch(voteService.getAllBetweenDates(LocalDate.of(2019, Month.MARCH, 20), LocalDate.of(2019, Month.MARCH, 21), USER_ID),
                List.of(USER_VOTE2, USER_VOTE1));
    }

}
