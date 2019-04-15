package com.mycompany.wheretogo.web.restaurant;

import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.service.RestaurantService;
import com.mycompany.wheretogo.service.VoteService;
import com.mycompany.wheretogo.to.RestaurantsTo;
import com.mycompany.wheretogo.to.VoteTo;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import com.mycompany.wheretogo.web.AbstractRestController;
import com.mycompany.wheretogo.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.mycompany.wheretogo.util.DateUtil.adjustEndDate;
import static com.mycompany.wheretogo.util.DateUtil.adjustStartDate;
import static com.mycompany.wheretogo.util.MenuItemsUtil.asRestaurantsToWithVote;
import static com.mycompany.wheretogo.util.VoteUtil.asTo;
import static com.mycompany.wheretogo.util.VoteUtil.asListOfTo;

@RestController
@RequestMapping(value = RestaurantVotingRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantVotingRestController extends AbstractRestController {
    static final String REST_URL = REST_BASE_URL + "/restaurants";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private VoteService voteService;

    @GetMapping
    public RestaurantsTo getRestaurants() {
        Vote vote;
        try {
            vote = voteService.getToday(SecurityUtil.authUserId());
        } catch (NotFoundException e) {
            vote = null;
        }
        return asRestaurantsToWithVote(LocalDate.now(), restaurantService.getAllTodayMenuItems(), vote);
    }

    @PostMapping(value = "/votes/today")
    @Transactional
    public ResponseEntity<VoteTo> makeVote(@RequestParam(value = "restaurantId") Integer restaurantId) {
        Vote createdVote = voteService.addToday(new Vote(LocalDateTime.now()), restaurantId, SecurityUtil.authUserId());
        createdVote.setRestaurant(restaurantService.get(restaurantId));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/votes/today")
                .build()
                .toUri();
        log.info("user with id = {} made vote for restaurant with id = {}", SecurityUtil.authUserId(), restaurantId);
        return ResponseEntity.created(uriOfNewResource).body(asTo(createdVote));
    }

    @GetMapping("/votes")
    public List<VoteTo> getVotes() {
        return asListOfTo(voteService.getAll(SecurityUtil.authUserId()));
    }

    @GetMapping("/votes/between")
    public List<VoteTo> getVotesBetween(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                                        @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return asListOfTo(voteService.getAllBetweenDates(adjustStartDate(startDate), adjustEndDate(endDate), SecurityUtil.authUserId()));
    }

    @GetMapping("/votes/today")
    public VoteTo getTodayVote() {
        return asTo(voteService.getToday(SecurityUtil.authUserId()));
    }

    @PutMapping(value = "/votes/today")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestParam(value = "restaurantId") Integer restaurantId) {
        voteService.updateToday(new Vote(LocalDateTime.now()), restaurantId, SecurityUtil.authUserId());
        log.info("user with id = {} updated his vote for restaurant with id = {}", SecurityUtil.authUserId(), restaurantId);
    }
}
