package com.mycompany.wheretogo;

import com.mycompany.wheretogo.model.Role;
import com.mycompany.wheretogo.model.User;
import com.mycompany.wheretogo.web.json.JsonUtil;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.mycompany.wheretogo.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@gmail.com", "userPassword", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "adminPassword", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, LocalDateTime.now(), Collections.singleton(Role.ROLE_USER));
    }

    public static User getWithDuplicateEmail() {
        return new User(null, "Duplicate", "user@gmail.com", "newPass", Role.ROLE_USER);
    }

    public static void assertMatch(User actual, User expected) {
        TestUtil.assertMatch(actual, expected, "registered", "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        TestUtil.assertMatch(actual, expected, "registered", "password");
    }

    public static ResultMatcher fromJsonAndAssert(User... expected) {
        return TestUtil.fromJsonAndAssert(List.of(expected), User.class, "registered", "password");
    }

    public static ResultMatcher fromJsonAndAssert(User expected) {
        return TestUtil.fromJsonAndAssert(expected, User.class, "registered", "password");
    }

    public static String toJsonWithPassword(User user, String pass) {
        return JsonUtil.writeAdditionProps(user, "password", pass);
    }
}
