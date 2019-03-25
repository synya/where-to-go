package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Vote;

import java.util.List;

public interface VoteService {
    List<Vote> getAll(int userId);
}
