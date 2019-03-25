package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.util.exception.OutOfDateTimeException;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteService {
    Vote add(Integer restaurantId, LocalDateTime localDateTime, Integer userId) throws OutOfDateTimeException;

    List<Vote> getAll(Integer userId);
}
