package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import com.mycompany.wheretogo.util.exception.OutOfDateTimeException;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {
    Vote addToday(Vote vote, Integer restaurantId, Integer userId) throws OutOfDateTimeException;

    void updateToday(Vote vote, Integer restaurantId, Integer userId) throws OutOfDateTimeException, NotFoundException;

    Vote getToday(Integer userId);

    List<Vote> getAll(Integer userId);

    List<Vote> getAllBetweenDates(LocalDate startDate, LocalDate endDate, Integer userId);
}
