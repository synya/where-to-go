package com.mycompany.wheretogo.util;

import com.mycompany.wheretogo.model.User;
import com.mycompany.wheretogo.to.UserTo;

public class UserUtil {
    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }
}
