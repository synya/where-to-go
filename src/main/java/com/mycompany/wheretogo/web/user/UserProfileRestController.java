package com.mycompany.wheretogo.web.user;

import com.mycompany.wheretogo.AuthorizedUser;
import com.mycompany.wheretogo.service.UserService;
import com.mycompany.wheretogo.to.UserTo;
import com.mycompany.wheretogo.web.AbstractRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.mycompany.wheretogo.util.UserUtil.asTo;
import static com.mycompany.wheretogo.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = UserProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserProfileRestController extends AbstractRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = REST_BASE_URL + "/profile";

    @Autowired
    private UserService userService;

    @GetMapping
    public UserTo get(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return asTo(userService.get(authorizedUser.getId()));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthorizedUser authorizedUser, @Valid @RequestBody UserTo userTo) {
        assureIdConsistent(userTo, authorizedUser.getId());
        userService.update(userTo);
        log.info("updated user with id = {}", authorizedUser.getId());
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        userService.delete(authorizedUser.getId());
        log.info("deleted user with id = {}", authorizedUser.getId());
    }

}
