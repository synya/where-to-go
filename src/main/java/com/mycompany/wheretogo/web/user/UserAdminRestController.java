package com.mycompany.wheretogo.web.user;

import com.mycompany.wheretogo.model.User;
import com.mycompany.wheretogo.service.UserService;
import com.mycompany.wheretogo.web.AbstractRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.mycompany.wheretogo.util.ValidationUtil.assureIdConsistent;
import static com.mycompany.wheretogo.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = UserAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserAdminRestController extends AbstractRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = REST_BASE_URL + "/management/users";

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        return userService.get(id);
    }

    @GetMapping("/by")
    public User getByMail(@RequestParam String email) {
        return userService.getByEmail(email);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        checkNew(user);
        User created = userService.add(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        log.info("added new user with id = {}", created.getId());
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @PathVariable int id) {
        assureIdConsistent(user, id);
        userService.update(user);
        log.info("updated user with id = {}", id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        userService.delete(id);
        log.info("deleted user with id = {}", id);
    }
}
