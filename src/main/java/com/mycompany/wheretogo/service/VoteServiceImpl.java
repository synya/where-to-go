package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.repository.UserRepository;
import com.mycompany.wheretogo.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {
    private final UserRepository userRepository;

    private final VoteRepository voteRepository;

    @Autowired
    public VoteServiceImpl(UserRepository userRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public List<Vote> getAll(int userId) {
        return voteRepository.findAll(userId);
    }
}
