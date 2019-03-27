package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.to.VoteTo;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import com.mycompany.wheretogo.util.exception.OutOfDateTimeException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        Integer addedId = voteService.add(getNewVoteTo(), USER_ID).getId();
        Vote addedVote = voteService.get(addedId, USER_ID);
        Vote vote = getNewVote();
        vote.setId(addedVote.getId());
        vote.setRestaurant(RESTAURANT_ATEOTU);
        assertThat(vote).isEqualToIgnoringGivenFields(addedVote, "user");
        assertThat(voteService.getAll(USER_ID))
                .usingElementComparatorIgnoringFields("user")
                .isEqualTo(List.of(new VoteTo(vote.getId(), vote.getRestaurant().getId(), vote.getRestaurant().getName(), vote.getDateTime()),
                        USER_VOTE_TO2, USER_VOTE_TO1));
    }

    @Test
    public void update() throws Exception {
        Integer addedId = voteService.add(getNewVoteTo(), USER_ID).getId();
        voteService.update(getUpdatedVoteTo(addedId), USER_ID);
        Vote updatedVote = voteService.get(addedId, USER_ID);
        Vote vote = getUpdatedVote();
        vote.setId(updatedVote.getId());
        assertThat(vote).isEqualToIgnoringGivenFields(updatedVote, "user");
    }

    @Test(expected = OutOfDateTimeException.class)
    public void updateOutdated() throws Exception {
        voteService.add(getNewVoteTo(), USER_ID);
        voteService.update(getTodayTooLateVoteTo(), USER_ID);
    }

    @Test(expected = OutOfDateTimeException.class)
    public void addOutdated() throws Exception {
        voteService.add(getOutdatedVoteTo(), USER_ID);
    }

    @Test
    public void get() throws Exception {
        assertThat(voteService.get(VOTE_START_ID, USER_ID)).isEqualToIgnoringGivenFields(USER_VOTE1, "user");
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        voteService.get(VOTE_START_ID, ADMIN_ID);
    }

    @Test
    public void getAll() throws Exception {
        assertThat(voteService.getAll(USER_ID))
                .usingElementComparatorIgnoringFields("user")
                .isEqualTo(List.of(USER_VOTE_TO2, USER_VOTE_TO1));
    }
}
