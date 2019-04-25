package com.mycompany.wheretogo.web.user;

import com.mycompany.wheretogo.TestUtil;
import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.service.RestaurantService;
import com.mycompany.wheretogo.service.VoteService;
import com.mycompany.wheretogo.to.RestaurantsTo;
import com.mycompany.wheretogo.to.VoteTo;
import com.mycompany.wheretogo.web.AbstractRestControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;

import static com.mycompany.wheretogo.MenuItemTestData.TODAY_MENU_ITEMS;
import static com.mycompany.wheretogo.MenuItemTestData.TODAY_RESTAURANTS_MENU_ITEMS_ID;
import static com.mycompany.wheretogo.RestaurantTestData.BURGER_KING_ID;
import static com.mycompany.wheretogo.RestaurantTestData.RESTAURANT_ATEOTU_ID;
import static com.mycompany.wheretogo.TestUtil.userHttpBasic;
import static com.mycompany.wheretogo.UserTestData.USER;
import static com.mycompany.wheretogo.UserTestData.USER_ID;
import static com.mycompany.wheretogo.VoteTestData.*;
import static com.mycompany.wheretogo.VoteTestData.TODAY_USER_VOTE;
import static com.mycompany.wheretogo.util.MenuItemsUtil.asRestaurantsToWithVote;
import static com.mycompany.wheretogo.util.VoteUtil.asListOfTo;
import static com.mycompany.wheretogo.util.VoteUtil.asTo;
import static com.mycompany.wheretogo.web.user.UserVoteRestController.REST_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserVoteRestControllerTest extends AbstractRestControllerTest {
    @Autowired
    private VoteService voteService;

    @Autowired
    private RestaurantService restaurantService;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("todayMenuItems").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void testGetUnauthorized() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetVotes() throws Exception {
        mockMvc.perform(get(REST_URL + "/restaurants")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(asListOfTo(List.of(USER_VOTE2, USER_VOTE1)), VoteTo.class));
    }

    @Test
    public void testGetVotesBetweenDates() throws Exception {
        mockMvc.perform(get(REST_URL + "/restaurants/between?startDate=2019-03-21&endDate=2019-03-21")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(asListOfTo(List.of(USER_VOTE2)), VoteTo.class));
    }

    @Test
    public void testGetVotesBetweenDatesEmpty() throws Exception {
        mockMvc.perform(get(REST_URL + "/restaurants/between?startDate=&endDate=")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(asListOfTo(List.of(USER_VOTE2, USER_VOTE1)), VoteTo.class));
    }

    @Test
    public void testGetRestaurantsElected() throws Exception {
        voteService.addToday(new Vote(TODAY_USER_VOTE.getDate(), TODAY_USER_VOTE.getTime()), RESTAURANT_ATEOTU_ID, USER_ID);
        Vote vote = voteService.getToday(USER_ID);
        mockMvc.perform(get(REST_URL + "/restaurants/today")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(asRestaurantsToWithVote(LocalDate.now(), TODAY_MENU_ITEMS, vote), RestaurantsTo.class));
    }

    @Test
    public void testGetRestaurantsNotElected() throws Exception {
        mockMvc.perform(get(REST_URL + "/restaurants/today")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(asRestaurantsToWithVote(LocalDate.now(), TODAY_MENU_ITEMS, null), RestaurantsTo.class));
    }

    @Test
    public void testMakeVote() throws Exception {
        mockMvc.perform(post(REST_URL + "/restaurants/today?id=" + RESTAURANT_ATEOTU_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(asTo(voteService.getToday(USER_ID)), VoteTo.class, "dateTime"));
    }

    @Test
    public void testMakeVoteNotWithinTodayRestaurants() throws Exception {
        restaurantService.deleteMenuItem(TODAY_RESTAURANTS_MENU_ITEMS_ID);
        restaurantService.deleteMenuItem(TODAY_RESTAURANTS_MENU_ITEMS_ID + 1);
        mockMvc.perform(post(REST_URL + "/restaurants/today?id=" + BURGER_KING_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}