package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import com.mycompany.wheretogo.util.exception.OutOfDateTimeException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static com.mycompany.wheretogo.RestaurantTestData.*;
import static com.mycompany.wheretogo.UserTestData.ADMIN_ID;
import static com.mycompany.wheretogo.UserTestData.USER_ID;
import static com.mycompany.wheretogo.VoteTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    protected VoteService voteService;

    @Test
    public void add() throws Exception {
        Vote newVote = getNewVote();
        Integer addedId = voteService.add(getNewVote(), RESTAURANT_ATEOTU_ID, USER_ID).getId();
        Vote addedVote = voteService.get(addedId, USER_ID);
        newVote.setId(addedVote.getId());
        newVote.setRestaurant(RESTAURANT_ATEOTU);
        assertThat(newVote).isEqualTo(addedVote);
        assertThat(voteService.getAll(USER_ID))
                .usingElementComparatorIgnoringFields("user")
                .isEqualTo(List.of(newVote, USER_VOTE2, USER_VOTE1));
    }

    @Test
    public void update() throws Exception {
        Vote newVote = getNewVote();
        Integer addedId = voteService.add(getNewVote(), RESTAURANT_ATEOTU_ID, USER_ID).getId();
        voteService.update(getUpdatedVote(), BURGER_KING_ID, USER_ID);
        Vote updatedVote = voteService.get(addedId, USER_ID);
        newVote.setId(updatedVote.getId());
        assertThat(newVote).isEqualTo(updatedVote);
    }

    @Test(expected = OutOfDateTimeException.class)
    public void updateOutdated() throws Exception {
        voteService.add(getNewVote(), RESTAURANT_ATEOTU_ID, USER_ID);
        voteService.update(getOutdatedVote(), BURGER_KING_ID, USER_ID);
    }

    @Test(expected = OutOfDateTimeException.class)
    public void addOutdated() throws Exception {
        Vote newVote = getNewVote();
        newVote.setDateTime(LocalDateTime.of(2019, Month.MARCH, 1, 0, 0, 0));
        voteService.add(newVote, BURGER_KING_ID, USER_ID);
    }

    @Test
    public void get() throws Exception {
        assertThat(voteService.get(VOTE_START_ID, USER_ID))
                .isEqualTo(USER_VOTE1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        voteService.get(VOTE_START_ID, ADMIN_ID);
    }

    @Test
    public void getAll() throws Exception {
        assertThat(voteService.getAll(USER_ID))
                .usingElementComparatorIgnoringFields("user")
                .isEqualTo(List.of(USER_VOTE2, USER_VOTE1));
    }
}
