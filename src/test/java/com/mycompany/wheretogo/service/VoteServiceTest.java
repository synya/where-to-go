package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import com.mycompany.wheretogo.util.exception.VotingRulesException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static com.mycompany.wheretogo.MenuItemTestData.TODAY_RESTAURANTS_MENU_ITEMS_ID;
import static com.mycompany.wheretogo.RestaurantTestData.*;
import static com.mycompany.wheretogo.UserTestData.USER_ID;
import static com.mycompany.wheretogo.VoteTestData.*;
import static com.mycompany.wheretogo.VoteTestData.assertMatch;

public class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    private VoteService voteService;

    @Autowired
    private MenuItemService menuItemService;

    @Test
    public void testAddVote() throws Exception {
        Vote addedVote = voteService.addToday(RESTAURANT_ATEOTU_ID, USER_ID);
        Vote expectedVote = new Vote(RESTAURANT_ATEOTU, addedVote.getDate(), addedVote.getTime());
        expectedVote.setId(addedVote.getId());
        assertMatch(voteService.getToday(USER_ID), expectedVote);
    }

    @Test
    public void testUpdateVote() throws Exception {
        voteService.addToday(RESTAURANT_ATEOTU_ID, USER_ID);
        Vote updatedVote = voteService.getToday(USER_ID);
        updatedVote.setTime(LocalTime.of(10, 20));
        voteService.updateToday(updatedVote, BURGER_KING_ID, USER_ID);
        updatedVote.setRestaurant(BURGER_KING);
        assertMatch(voteService.getToday(USER_ID), updatedVote);
    }

    @Test(expected = VotingRulesException.class)
    public void testUpdateVoteOutdated() throws Exception {
        voteService.addToday(RESTAURANT_ATEOTU_ID, USER_ID);
        Vote updatedVote = voteService.getToday(USER_ID);
        updatedVote.setTime(LocalTime.of(11, 1));
        voteService.updateToday(updatedVote, BURGER_KING_ID, USER_ID);
    }

    @Test(expected = VotingRulesException.class)
    public void testAddNotWithinTodayRestaurants() throws Exception {
        menuItemService.delete(TODAY_RESTAURANTS_MENU_ITEMS_ID);
        menuItemService.delete(TODAY_RESTAURANTS_MENU_ITEMS_ID + 1);
        voteService.addToday(BURGER_KING_ID, USER_ID);
    }

    @Test
    public void testGetToday() throws Exception {
        Vote addedVote = voteService.addToday(RESTAURANT_ATEOTU_ID, USER_ID);
        Vote expectedVote = new Vote(RESTAURANT_ATEOTU, addedVote.getDate(), addedVote.getTime());
        expectedVote.setId(addedVote.getId());
        assertMatch(voteService.getToday(USER_ID), expectedVote);
    }

    @Test(expected = NotFoundException.class)
    public void testGetTodayVoteNotFound() throws Exception {
        voteService.getToday(USER_ID);
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
