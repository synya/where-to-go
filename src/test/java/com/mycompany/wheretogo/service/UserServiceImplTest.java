package com.mycompany.wheretogo.service;


import com.mycompany.wheretogo.model.Role;
import com.mycompany.wheretogo.model.User;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import static com.mycompany.wheretogo.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceImplTest extends AbstractServiceTest {
    @Autowired
    protected UserService userService;

    @Test
    public void create() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", false, LocalDateTime.now(), Collections.singleton(Role.ROLE_USER));
        User created = userService.create(newUser);
        newUser.setId(created.getId());
        assertThat(newUser)
                .isEqualToIgnoringGivenFields(created, "registered");
        assertThat(userService.getAll())
                .usingElementComparatorIgnoringFields("registered")
                .isEqualTo(List.of(ADMIN, newUser, USER));
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMailCreate() throws Exception {
        userService.create(new User(null, "Duplicate", "user@gmail.com", "newPass", Role.ROLE_USER));
    }

    @Test
    public void delete() throws Exception {
        userService.delete(USER_ID);
        assertThat(userService.getAll())
                .usingElementComparatorIgnoringFields("registered")
                .isEqualTo(List.of(ADMIN));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        userService.delete(1);
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
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setPassword("UpdatedPassword");
        updated.setRoles(EnumSet.of(Role.ROLE_USER, Role.ROLE_ADMIN));
        userService.update(updated);
        assertThat(userService.get(USER_ID))
                .isEqualToIgnoringGivenFields(updated, "registered");
    }

    @Test
    public void getAll() throws Exception {
        assertThat(userService.getAll())
                .usingElementComparatorIgnoringFields("registered")
                .isEqualTo(List.of(ADMIN, USER));
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> userService.create(new User(null, "  ", "mail@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "mail@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
    }
}