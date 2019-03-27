package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.to.VoteTo;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import com.mycompany.wheretogo.util.exception.OutOfDateTimeException;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {
    Vote add(VoteTo voteTo, Integer userId) throws OutOfDateTimeException;

    Vote get(Integer id, Integer userId) throws NotFoundException;

    void update(VoteTo voteTo, Integer userId) throws OutOfDateTimeException, NotFoundException;

    List<VoteTo> getAll(Integer userId);

    List<VoteTo> getAllBetween(LocalDate startDate, LocalDate endDate, Integer userId);
}
