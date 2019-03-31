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
import static org.assertj.core.api.Assertions.assertThat;

public class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    private VoteService voteService;

    @Test
    public void add() throws Exception {
        Vote newVote = getNewVote();
        voteService.add(getNewVote(), RESTAURANT_ATEOTU_ID, USER_ID);
        List<Vote> votes = voteService.getAllBetweenDates(LocalDate.of(2019, Month.JANUARY, 1), LocalDate.now(), USER_ID);
        newVote.setId(votes.get(0).getId());
        newVote.setRestaurant(RESTAURANT_ATEOTU);
        assertThat(newVote).isEqualTo(votes.get(0));
        assertThat(votes)
                .usingElementComparatorIgnoringFields("user")
                .isEqualTo(List.of(newVote, USER_VOTE2, USER_VOTE1));
    }

    @Test
    public void update() throws Exception {
        Vote newVote = getNewVote();
        voteService.add(getNewVote(), RESTAURANT_ATEOTU_ID, USER_ID);
        voteService.update(getUpdatedVote(), BURGER_KING_ID, USER_ID);
        Vote updatedVote = voteService.getAllBetweenDates(LocalDate.now(), LocalDate.now(), USER_ID).get(0);
        newVote.setId(updatedVote.getId());
        assertThat(newVote).isEqualTo(updatedVote);
    }

    @Test(expected = OutOfDateTimeException.class)
    public void updateOutdated() throws Exception {
        voteService.add(getNewVote(), RESTAURANT_ATEOTU_ID, USER_ID);
        Vote vote = getNewVote();
        vote.setDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 1)));
        voteService.update(vote, BURGER_KING_ID, USER_ID);
    }

    @Test(expected = OutOfDateTimeException.class)
    public void addOutdated() throws Exception {
        Vote vote = getNewVote();
        vote.setDateTime(LocalDateTime.of(LocalDate.of(2019, Month.MARCH, 19), LocalTime.now()));
        voteService.add(vote, BURGER_KING_ID, USER_ID);
    }

    @Test
    public void getAll() throws Exception {
        assertThat(voteService.getAllBetweenDates(LocalDate.of(2019, Month.JANUARY, 1), LocalDate.now(), USER_ID))
                .usingElementComparatorIgnoringFields("user")
                .isEqualTo(List.of(USER_VOTE2, USER_VOTE1));
    }
}
