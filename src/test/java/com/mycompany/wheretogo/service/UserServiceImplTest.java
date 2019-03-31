package com.mycompany.wheretogo.service;


import com.mycompany.wheretogo.model.Role;
import com.mycompany.wheretogo.model.User;
import com.mycompany.wheretogo.util.JpaUtil;
import com.mycompany.wheretogo.util.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.mycompany.wheretogo.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceImplTest extends AbstractServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JpaUtil jpaUtil;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void add() throws Exception {
        User user = getNew();
        User addedUser = userService.add(user);
        user.setId(addedUser.getId());
        assertThat(user).isEqualToIgnoringGivenFields(addedUser, "registered");
        assertThat(userService.getAll())
                .usingElementComparatorIgnoringFields("registered")
                .isEqualTo(List.of(ADMIN, user, USER));
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMailCreate() throws Exception {
        userService.add(getWithDuplicateEmail());
    }

    @Test
    public void get() throws Exception {
        assertThat(userService.get(USER_ID)).isEqualToIgnoringGivenFields(USER, "registered");
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        userService.get(1);
    }

    @Test
    public void getByEmail() throws Exception {
        assertThat(userService.getByEmail("admin@gmail.com")).isEqualToIgnoringGivenFields(ADMIN, "registered");
    }

    @Test
    public void update() throws Exception {
        userService.update(getUpdated());
        assertThat(userService.get(USER_ID)).isEqualToIgnoringGivenFields(getUpdated(), "registered");
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