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
        Vote addedVote = voteService.add(new Vote(TODAY_USER_VOTE.getDate(), TODAY_USER_VOTE.getTime()), RESTAURANT_ATEOTU_ID, USER_ID);
        addedVote.setId(addedVote.getId());
        addedVote.setRestaurant(RESTAURANT_ATEOTU);
        assertMatch(addedVote, TODAY_USER_VOTE);
        assertMatch(voteService.getToday(USER_ID), TODAY_USER_VOTE);
    }

    @Test
    public void testUpdateVote() throws Exception {
        voteService.add(new Vote(TODAY_USER_VOTE.getDate(), TODAY_USER_VOTE.getTime()), RESTAURANT_ATEOTU_ID, USER_ID);
        Vote updatedVote = voteService.getToday(USER_ID);
        updatedVote.setTime(LocalTime.of(10, 20));
        voteService.update(updatedVote, BURGER_KING_ID, USER_ID);
        assertMatch(voteService.getToday(USER_ID), updatedVote);
    }

    @Test(expected = OutOfDateTimeException.class)
    public void testUpdateVoteOutdated() throws Exception {
        voteService.add(new Vote(TODAY_USER_VOTE.getDate(), TODAY_USER_VOTE.getTime()), RESTAURANT_ATEOTU_ID, USER_ID);
        Vote updatedVote = voteService.getToday(USER_ID);
        updatedVote.setTime(LocalTime.of(11, 01));
        voteService.update(updatedVote, BURGER_KING_ID, USER_ID);
    }

    @Test(expected = OutOfDateTimeException.class)
    public void testAddVoteOutdated() throws Exception {
        Vote vote = new Vote(LocalDate.of(2019, Month.MARCH, 19), LocalTime.now());
        voteService.add(vote, BURGER_KING_ID, USER_ID);
    }

    @Test
    public void testGetToday() throws Exception {
        voteService.add(new Vote(TODAY_USER_VOTE.getDate(), TODAY_USER_VOTE.getTime()), RESTAURANT_ATEOTU_ID, USER_ID);
        assertMatch(voteService.getToday(USER_ID), TODAY_USER_VOTE);
    }

    @Test
    public void testGetTodayVoteNotFound() throws Exception {
        assertMatch(voteService.getToday(ADMIN_ID), null);
    }

    @Test
    public void testGetAllVotes() throws Exception {
        assertMatch(voteService.getAll(USER_ID), USER_VOTE2, USER_VOTE1);
    }

    @Test
    public void testGetAllVotesBetween() throws Exception {
        assertMatch(voteService.getAllBetweenDates(LocalDate.of(2019, Month.MARCH, 20), LocalDate.of(2019, Month.MARCH, 21), USER_ID),
                List.of(USER_VOTE2, USER_VOTE1));
    }

}
