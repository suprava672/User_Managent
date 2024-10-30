package com.example.UserManagement.service;

import com.example.UserManagement.entity.User;
import com.example.UserManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public User createUser(User user) {
        return   userRepository.save(user);

    }

    @Override
    public User getUserById(Long userid) {
        return userRepository.findById(userid).orElseThrow(()->new RuntimeException("user is not present"));
    }


    @Override
    public List<User> getAllUser() {
       return userRepository.findAll();

    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);

    }

    @Override
    public User updateUser(Long userId,User user) {
        User user1 = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user  not found"));
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());

        return userRepository.save(user);
    }

    @Override
    public Map<String, List<User>> groupingByName() {
        List<User> users = userRepository.findAll();
        return users.stream().collect(Collectors.groupingBy(User::getName));
    }

}
