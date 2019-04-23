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
import java.util.EnumSet;

import static com.mycompany.wheretogo.UserTestData.*;

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
    public void testAddUser() throws Exception {
        User user = getNew();
        User addedUser = userService.add(user);
        user.setId(addedUser.getId());
        assertMatch(user, addedUser);
        assertMatch(userService.getAll(), ADMIN, user, USER);
    }

    @Test(expected = DataAccessException.class)
    public void testAddUserWithDuplicateEmail() throws Exception {
        userService.add(getWithDuplicateEmail());
    }

    @Test
    public void testGetUser() throws Exception {
        assertMatch(userService.get(USER_ID), USER);
    }

    @Test(expected = NotFoundException.class)
    public void testGetUserNotFound() throws Exception {
        userService.get(ENTITY_NOT_FOUND_ID);
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        assertMatch(userService.getByEmail("admin@gmail.com"), ADMIN);
    }

    @Test
    public void testUpdateUser() throws Exception {
        User updatedUser = userService.get(USER_ID);
        updatedUser.setName("UpdatedName");
        updatedUser.setPassword("UpdatedPassword");
        updatedUser.setRoles(EnumSet.of(Role.ROLE_USER, Role.ROLE_ADMIN));
        userService.update(updatedUser);
        assertMatch(userService.get(USER_ID), updatedUser);
    }

    @Test
    public void testDeleteUser() throws Exception {
        userService.delete(USER_ID);
        assertMatch(userService.getAll(), ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteUserNotFound() throws Exception {
        userService.delete(ENTITY_NOT_FOUND_ID);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        assertMatch(userService.getAll(), ADMIN, USER);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> userService.add(new User(null, "  ", "mail@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.add(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.add(new User(null, "User", "mail@yandex.ru", "", Role.ROLE_USER)), ConstraintViolationException.class);
    }
}