package com.foneloanMini.service;

import com.foneloanMini.entity.User;
import com.foneloanMini.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create a new user (of any type)
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Retrieve a user by email (works for Customer, Support Agent, or Manager)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findById(email);
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update user details
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // Delete user by email
    public void deleteUser(String email) {
        userRepository.deleteById(email);
    }
}
