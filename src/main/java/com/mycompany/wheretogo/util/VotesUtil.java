package com.mycompany.wheretogo.util;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.to.VoteTo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class VotesUtil {
    private VotesUtil() {
    }

    public static List<VoteTo> toVoteTos(List<Vote> votes) {
        return votes.stream()
                .map(VotesUtil::toVoteTo)
                .collect(Collectors.toList());
    }

    public static VoteTo toVoteTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getRestaurant().getName(), LocalDateTime.of(vote.getDate(), vote.getTime()));
    }

}
