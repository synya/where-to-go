package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.repository.RestaurantRepository;
import com.mycompany.wheretogo.repository.UserRepository;
import com.mycompany.wheretogo.repository.VoteRepository;
import com.mycompany.wheretogo.util.exception.OutOfDateTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {
    private final UserRepository userRepository;

    private final RestaurantRepository restaurantRepository;

    private final VoteRepository voteRepository;

    @Autowired
    public VoteServiceImpl(UserRepository userRepository, VoteRepository voteRepository, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    @Transactional
    public Vote add(Integer restaurantId, LocalDateTime localDateTime, Integer userId) {
        Assert.notNull(restaurantId, "restaurantId must not be null");
        Assert.notNull(localDateTime, "localDateTime must not be null");
        Assert.notNull(userId, "userId must not be null");
        if (!localDateTime.toLocalDate().equals(LocalDate.now())) {
            throw new OutOfDateTimeException("Operation is not allowed due to date/time restrictions");
        }
        Vote vote = new Vote(restaurantRepository.getOne(restaurantId), localDateTime);
        vote.setUser(userRepository.getOne(userId));
        return voteRepository.save(vote);
    }

    @Override
    public List<Vote> getAll(Integer userId) {
        Assert.notNull(userId, "userId must not be null");
        return voteRepository.findAll(userId);
    }
}
