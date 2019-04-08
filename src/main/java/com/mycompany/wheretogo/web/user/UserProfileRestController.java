package com.mycompany.wheretogo.web.user;

import com.mycompany.wheretogo.model.User;
import com.mycompany.wheretogo.service.UserService;
import com.mycompany.wheretogo.web.AbstractRestController;
import com.mycompany.wheretogo.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.mycompany.wheretogo.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = UserProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserProfileRestController extends AbstractRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = REST_BASE_URL + "/profile";

    @Autowired
    private UserService userService;

    @GetMapping
    public User get() {
        return userService.get(SecurityUtil.authUserId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user) {
        assureIdConsistent(user, SecurityUtil.authUserId());
        userService.update(user);
        log.info("updated user with id = {}", SecurityUtil.authUserId());
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        userService.delete(SecurityUtil.authUserId());
        log.info("deleted user with id = {}", SecurityUtil.authUserId());
    }

}
