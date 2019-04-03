package com.mycompany.wheretogo.util;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.to.VoteTo;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {
    private VoteUtil() {
    }

    public static List<VoteTo> getVotesWithDateTime(List<Vote> votes) {
        return votes.stream()
                .map(VoteUtil::getVoteWithDateTime)
                .collect(Collectors.toList());
    }

    public static VoteTo getVoteWithDateTime(@Nullable Vote vote) {
        return vote == null ? new VoteTo() :
                new VoteTo(vote.getId(), vote.getRestaurant().getName(), LocalDateTime.of(vote.getDate(), vote.getTime()));
    }

}
