package com.mycompany.wheretogo.service;

import com.mycompany.wheretogo.model.User;
import com.mycompany.wheretogo.to.UserTo;
import com.mycompany.wheretogo.util.exception.NotFoundException;

import java.util.List;

public interface UserService {
    User add(User user);

    User get(Integer id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    List<User> getAll();

    void update(User user);

    void update(UserTo userTo);

    void delete(int id) throws NotFoundException;
}