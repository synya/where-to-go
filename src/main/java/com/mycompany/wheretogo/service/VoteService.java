package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import com.mycompany.wheretogo.util.exception.OutOfDateTimeException;

import java.util.List;

public interface VoteService {
    Vote add(Vote vote, Integer restaurantId, Integer userId) throws OutOfDateTimeException;

    Vote get(Integer id, Integer userId) throws NotFoundException;

    void update(Vote vote, Integer restaurantId, Integer userId) throws OutOfDateTimeException, NotFoundException;

    List<Vote> getAll(Integer userId);
}
