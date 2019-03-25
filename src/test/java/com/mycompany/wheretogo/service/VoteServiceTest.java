package com.mycompany.wheretogo.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.mycompany.wheretogo.UserTestData.USER_ID;
import static com.mycompany.wheretogo.VoteTestData.USER_VOTE1;
import static com.mycompany.wheretogo.VoteTestData.USER_VOTE2;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    protected VoteService voteService;

    @Test
    public void getAll() throws Exception {
        assertThat(voteService.getAll(USER_ID))
                .usingElementComparatorIgnoringFields("user")
                .isEqualTo(List.of(USER_VOTE2, USER_VOTE1));
    }
}
