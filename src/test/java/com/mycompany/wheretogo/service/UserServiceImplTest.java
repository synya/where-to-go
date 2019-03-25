package com.mycompany.wheretogo.service;


import com.mycompany.wheretogo.model.Role;
import com.mycompany.wheretogo.model.User;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.mycompany.wheretogo.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceImplTest extends AbstractServiceTest {
    @Autowired
    protected UserService userService;

    @Test
    public void add() throws Exception {
        User newUser = getNew();
        User added = userService.add(newUser);
        newUser.setId(added.getId());
        assertThat(newUser)
                .isEqualToIgnoringGivenFields(added, "registered");
        assertThat(userService.getAll())
                .usingElementComparatorIgnoringFields("registered")
                .isEqualTo(List.of(ADMIN, newUser, USER));
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMailCreate() throws Exception {
        userService.add(getWithDuplicateEmail());
    }

    @Test
    public void get() throws Exception {
        User user = userService.get(USER_ID);
        assertThat(user)
                .isEqualToIgnoringGivenFields(USER, "registered");
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        userService.get(1);
    }

    @Test
    public void getByEmail() throws Exception {
        User admin = userService.getByEmail("admin@gmail.com");
        assertThat(admin)
                .isEqualToIgnoringGivenFields(ADMIN, "registered");
    }

    @Test
    public void update() throws Exception {
        userService.update(getUpdated());
        assertThat(userService.get(USER_ID))
                .isEqualToIgnoringGivenFields(getUpdated(), "registered");
    }

    @Test
    public void getAll() throws Exception {
        assertThat(userService.getAll())
                .usingElementComparatorIgnoringFields("registered")
                .isEqualTo(List.of(ADMIN, USER));
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> userService.add(new User(null, "  ", "mail@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.add(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.add(new User(null, "User", "mail@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
    }
}