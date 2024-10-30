package com.example.UserManagement.service;

import com.example.UserManagement.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public User createUser(User user);

    User getUserById(Long userid);

    public List<User> getAllUser();

    void deleteUserById(Long userId);

    Map<String, List<User>> groupingByName();

    User updateUser(Long userId, User user);
}
