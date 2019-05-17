package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.repository.RestaurantRepository;
import com.mycompany.wheretogo.repository.UserRepository;
import com.mycompany.wheretogo.repository.VoteRepository;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import com.mycompany.wheretogo.util.exception.VotingRulesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mycompany.wheretogo.util.MenuItemsUtil.asSetOfRestaurantId;
import static com.mycompany.wheretogo.util.ValidationUtil.checkNotFound;

@Service
public class VoteService {
    private static final LocalTime ALLOWED_UPDATE_TIME_THRESHOLD = LocalTime.of(11, 0);

    private final UserRepository userRepository;

    private final RestaurantRepository restaurantRepository;

    private final VoteRepository voteRepository;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    public VoteService(UserRepository userRepository, VoteRepository voteRepository, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public Vote addToday(int restaurantId, int userId) throws VotingRulesException {
        if (!asSetOfRestaurantId(menuItemService.getAllToday()).contains(restaurantId)) {
            throw new VotingRulesException("Operation is not allowed - the applied restaurantId is not in today list of restaurants");
        }
        Vote vote = new Vote(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        vote.setUser(userRepository.getOne(userId));
        return voteRepository.save(vote);
    }

    public Vote getToday(int userId) {
        return checkNotFound(voteRepository.findByIdAndDate(userId, LocalDate.now()).orElse(null), ". No today vote was made");
    }

    public List<Vote> getAll(int userId) {
        return voteRepository.findAll(userId);
    }

    public List<Vote> getAllBetweenDates(LocalDate startDate, LocalDate endDate, int userId) {
        Assert.notNull(startDate, "startDate must not be null");
        Assert.notNull(endDate, "endDate must not be null");
        return voteRepository.findAllBetweenDates(startDate, endDate, userId);
    }

    @Transactional
    public void updateToday(Vote vote, int restaurantId, int userId) throws VotingRulesException, NotFoundException {
        Assert.notNull(vote, "vote must not be null");
        Vote previousVote = getToday(userId);
        checkNotFound(previousVote, "Not found today vote, nothing to update");
        if (vote.getTime().isAfter(ALLOWED_UPDATE_TIME_THRESHOLD)) {
            throw new VotingRulesException("Operation is not allowed - it's too late to change the vote");
        }
        vote.setId(previousVote.getId());
        vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        vote.setUser(userRepository.getOne(userId));
        voteRepository.save(vote);
    }
}
