package com.mycompany.wheretogo.web.user;

import com.mycompany.wheretogo.AuthorizedUser;
import com.mycompany.wheretogo.model.Vote;
import com.mycompany.wheretogo.service.MenuItemService;
import com.mycompany.wheretogo.service.RestaurantService;
import com.mycompany.wheretogo.service.VoteService;
import com.mycompany.wheretogo.to.RestaurantsTo;
import com.mycompany.wheretogo.to.VoteTo;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import com.mycompany.wheretogo.web.AbstractRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import static com.mycompany.wheretogo.util.VoteUtil.asListOfTo;
import static com.mycompany.wheretogo.util.VoteUtil.asTo;

@RestController
@RequestMapping(value = UserVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteRestController extends AbstractRestController {
    static final String REST_URL = REST_BASE_URL + "/votes";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private VoteService voteService;

    @GetMapping("/restaurants")
    public List<VoteTo> getVotes(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return asListOfTo(voteService.getAll(authorizedUser.getId()));
    }

    @GetMapping(value = "/restaurants/today")
    @Transactional
    public RestaurantsTo getRestaurants(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        Vote vote;
        try {
            vote = voteService.getToday(authorizedUser.getId());
        } catch (NotFoundException e) {
            vote = null;
        }
        return asRestaurantsToWithVote(LocalDate.now(), menuItemService.getAllToday(), vote);
    }

    @GetMapping("/restaurants/between")
    public List<VoteTo> getVotesBetween(@AuthenticationPrincipal AuthorizedUser authorizedUser,
                                        @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                        @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return asListOfTo(voteService.getAllBetweenDates(adjustStartDate(startDate), adjustEndDate(endDate), authorizedUser.getId()));
    }

    @PostMapping(value = "/restaurants/today")
    @Transactional
    public ResponseEntity<VoteTo> makeVote(@AuthenticationPrincipal AuthorizedUser authorizedUser,
                                           @RequestParam(value = "id") int id) {
        Vote createdVote = voteService.addToday(new Vote(LocalDateTime.now()), id, authorizedUser.getId());
        createdVote.setRestaurant(restaurantService.get(id));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/today/restaurants")
                .build()
                .toUri();
        log.info("user with id = {} made vote for restaurant with id = {}", authorizedUser.getId(), id);
        return ResponseEntity.created(uriOfNewResource).body(asTo(createdVote));
    }

    @PutMapping(value = "/restaurants/today")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthorizedUser authorizedUser,
                       @RequestParam(value = "id") int id) {
        voteService.updateToday(new Vote(LocalDateTime.now()), id, authorizedUser.getId());
        log.info("user with id = {} updated his vote for restaurant with id = {}", authorizedUser.getId(), id);
    }

}
