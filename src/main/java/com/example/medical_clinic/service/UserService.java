package com.example.medical_clinic.service;

import com.example.medical_clinic.exception.UserNotFoundException;
import com.example.medical_clinic.model.User;
import com.example.medical_clinic.repository.UserRepository;
import com.example.medical_clinic.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with given username does not exist"));
    }

    public User createUser(User user) {
        UserValidator.validateUserCreation(userRepository, user);
        return userRepository.addUser(user);
    }

    public void removeUser(String username) {
        if (!userRepository.removeUser(username)) {
            throw new UserNotFoundException("User with given username does not exist");
        }
    }

    public User updateUser(String username, User updatedUser) {
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with given usernem does not exist"));
        UserValidator.validateUserUpdate(userRepository, updatedUser, username);
        return userRepository.updateUser(existingUser, updatedUser);
    }
}
