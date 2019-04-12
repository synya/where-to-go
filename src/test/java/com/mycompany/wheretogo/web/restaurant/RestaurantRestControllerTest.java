package com.mycompany.wheretogo.web.restaurant;

import com.mycompany.wheretogo.TestUtil;
import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.service.VoteService;
import com.mycompany.wheretogo.to.RestaurantTo;
import com.mycompany.wheretogo.to.VoteTo;
import com.mycompany.wheretogo.web.AbstractRestControllerTest;
import com.mycompany.wheretogo.web.user.UserProfileRestController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static com.mycompany.wheretogo.MenuItemTestData.TODAY_MENU_ITEMS;
import static com.mycompany.wheretogo.RestaurantTestData.RESTAURANT_ATEOTU_ID;
import static com.mycompany.wheretogo.TestUtil.userHttpBasic;
import static com.mycompany.wheretogo.UserTestData.USER;
import static com.mycompany.wheretogo.UserTestData.USER_ID;
import static com.mycompany.wheretogo.VoteTestData.*;
import static com.mycompany.wheretogo.util.MenuItemsUtil.toRestaurantToWithVote;
import static com.mycompany.wheretogo.util.VotesUtil.toVoteTo;
import static com.mycompany.wheretogo.util.VotesUtil.toVoteTos;
import static com.mycompany.wheretogo.web.restaurant.RestaurantRestController.REST_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantRestControllerTest extends AbstractRestControllerTest {
    @Autowired
    private VoteService voteService;

    @Test
    public void testGetUnauthorized() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetRestaurants() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(toRestaurantToWithVote(TODAY_MENU_ITEMS, null), RestaurantTo.class));
    }

    @Test
    public void testGetVotes() throws Exception {
        mockMvc.perform(get(REST_URL + "/votes")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(toVoteTos(List.of(USER_VOTE2, USER_VOTE1)), VoteTo.class));
    }

    @Test
    public void testGetVotesBetweenDates() throws Exception {
        mockMvc.perform(get(REST_URL + "/votes/between?startDate=2019-03-20&endDate=2019-03-21")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(toVoteTos(List.of(USER_VOTE2, USER_VOTE1)), VoteTo.class));
    }

    @Test
    public void testGetVotesBetweenDatesEmpty() throws Exception {
        mockMvc.perform(get(REST_URL + "/votes/between?startDate=&endDate=")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(toVoteTos(List.of(USER_VOTE2, USER_VOTE1)), VoteTo.class));
    }

    @Test
    public void testGetTodayVote() throws Exception {
        voteService.addToday(new Vote(TODAY_USER_VOTE.getDate(), TODAY_USER_VOTE.getTime()), RESTAURANT_ATEOTU_ID, USER_ID);
        Vote vote = voteService.getToday(USER_ID);
        mockMvc.perform(get(REST_URL + "/votes/today")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(toVoteTo(vote), VoteTo.class));
    }

    @Test
    public void testMakeVote() throws Exception {
        mockMvc.perform(post(REST_URL + "/votes/today?restaurantId=" + RESTAURANT_ATEOTU_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(toVoteTo(voteService.getToday(USER_ID)), VoteTo.class, "dateTime"));
    }
}