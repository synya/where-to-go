package com.mycompany.wheretogo;

import com.mycompany.wheretogo.model.Role;
import com.mycompany.wheretogo.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.EnumSet;

import static com.mycompany.wheretogo.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@gmail.com", "user", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, LocalDateTime.now(), Collections.singleton(Role.ROLE_USER));
    }

    public static User getWithDuplicateEmail() {
        return new User(null, "Duplicate", "user@gmail.com", "newPass", Role.ROLE_USER);
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setPassword("UpdatedPassword");
        updated.setRoles(EnumSet.of(Role.ROLE_USER, Role.ROLE_ADMIN));
        return updated;
    }
}
