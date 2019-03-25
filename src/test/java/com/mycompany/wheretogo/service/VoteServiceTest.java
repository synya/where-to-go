package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.util.exception.OutOfDateTimeException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static com.mycompany.wheretogo.UserTestData.USER_ID;
import static com.mycompany.wheretogo.VoteTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    protected VoteService voteService;

    @Test
    public void add() throws Exception {
        Vote newVote = getNewVote();
        Vote addedVote = voteService.add(newVote.getRestaurant().getId(), newVote.getDateTime(), USER_ID);
        newVote.setId(addedVote.getId());
        assertThat(voteService.getAll(USER_ID))
                .usingElementComparatorIgnoringFields("user")
                .isEqualTo(List.of(newVote, USER_VOTE2, USER_VOTE1));
    }

    @Test(expected = OutOfDateTimeException.class)
    public void addOutdated() throws Exception {
        Vote newVote = getNewVote();
        newVote.setDateTime(LocalDateTime.of(2019, Month.MARCH, 1, 0, 0, 0));
        voteService.add(newVote.getRestaurant().getId(), newVote.getDateTime(), USER_ID);
    }

    @Test
    public void getAll() throws Exception {
        assertThat(voteService.getAll(USER_ID))
                .usingElementComparatorIgnoringFields("user")
                .isEqualTo(List.of(USER_VOTE2, USER_VOTE1));
    }
}
