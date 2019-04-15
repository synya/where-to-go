package com.mycompany.wheretogo.util;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.to.VoteTo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {
    private VoteUtil() {
    }

    public static List<VoteTo> asListOfTo(List<Vote> votes) {
        return votes.stream()
                .map(VoteUtil::asTo)
                .collect(Collectors.toList());
    }

    public static VoteTo asTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getRestaurant().getName(), LocalDateTime.of(vote.getDate(), vote.getTime()));
    }

}
