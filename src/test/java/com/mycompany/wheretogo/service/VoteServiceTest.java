package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.util.exception.OutOfDateTimeException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static com.mycompany.wheretogo.RestaurantTestData.*;
import static com.mycompany.wheretogo.UserTestData.USER_ID;
import static com.mycompany.wheretogo.VoteTestData.*;
import static com.mycompany.wheretogo.VoteTestData.assertMatch;

public class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    private VoteService voteService;

    @Test
    public void testAddVote() throws Exception {
        Vote newVote = new Vote(null, RESTAURANT_ATEOTU, LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 45)));
        Vote addedVote = voteService.add(newVote, RESTAURANT_ATEOTU_ID, USER_ID);
        newVote.setId(addedVote.getId());
        newVote.setRestaurant(RESTAURANT_ATEOTU);
        assertMatch(newVote, addedVote);
        assertMatch(voteService.getAll(USER_ID), newVote, USER_VOTE2, USER_VOTE1);
    }

    @Test
    public void testUpdateVote() throws Exception {
        Vote newVote = new Vote(null, RESTAURANT_ATEOTU, LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 45)));
        Vote addedUpdatedVote = voteService.add(newVote, RESTAURANT_ATEOTU_ID, USER_ID);
        addedUpdatedVote.setDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 20)));
        voteService.update(addedUpdatedVote, BURGER_KING_ID, USER_ID);
        newVote.setId(addedUpdatedVote.getId());
        newVote.setRestaurant(BURGER_KING);
        assertMatch(newVote, addedUpdatedVote);
        assertMatch(voteService.getAll(USER_ID), newVote, USER_VOTE2, USER_VOTE1);
    }

    @Test(expected = OutOfDateTimeException.class)
    public void testUpdateVoteOutdated() throws Exception {
        Vote newVote = new Vote(null, RESTAURANT_ATEOTU, LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 45)));
        Vote addedUpdatedVote = voteService.add(newVote, RESTAURANT_ATEOTU_ID, USER_ID);
        addedUpdatedVote.setDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 01)));
        voteService.update(addedUpdatedVote, BURGER_KING_ID, USER_ID);
    }

    @Test(expected = OutOfDateTimeException.class)
    public void testAddVoteOutdated() throws Exception {
        Vote vote = new Vote(null, RESTAURANT_ATEOTU, LocalDateTime.of(LocalDate.of(2019, Month.MARCH, 19), LocalTime.now()));
        voteService.add(vote, BURGER_KING_ID, USER_ID);
    }

    @Test
    public void testGetAllVotes() throws Exception {
        assertMatch(voteService.getAll(USER_ID), USER_VOTE2, USER_VOTE1);
    }

    @Test
    public void testGetAllVotesBetween() throws Exception {
        assertMatch(voteService.getAllBetweenDates(LocalDate.of(2019, Month.MARCH, 20), LocalDate.of(2019, Month.MARCH, 20), USER_ID),
                List.of(USER_VOTE1));
    }

}
