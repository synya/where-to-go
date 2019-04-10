package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import com.mycompany.wheretogo.util.exception.VotingRulesException;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {
    Vote addToday(Vote vote, Integer restaurantId, Integer userId) throws VotingRulesException;

    Vote getToday(Integer userId) throws NotFoundException;

    List<Vote> getAll(Integer userId);

    List<Vote> getAllBetweenDates(LocalDate startDate, LocalDate endDate, Integer userId);

    void updateToday(Vote vote, Integer restaurantId, Integer userId) throws VotingRulesException, NotFoundException;
}
