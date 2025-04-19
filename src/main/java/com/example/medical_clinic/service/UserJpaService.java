package com.example.medical_clinic.service;

import com.example.medical_clinic.exception.UserNotFoundException;
import com.example.medical_clinic.model.User;
import com.example.medical_clinic.repository.UserJpaRepository;
import com.example.medical_clinic.validation.UserJpaValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserJpaService {
    private final UserJpaRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with given username does not exist"));
    }

    public User createUser(User user) {
        UserJpaValidator.validateUserCreation(userRepository, user);
        return userRepository.save(user);
    }

    public void removeUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with given username does not exist"));
        userRepository.delete(user);
    }

    public User updateUser(String username, User updatedUser) {
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with given usernem does not exist"));
        UserJpaValidator.validateUserUpdate(userRepository, updatedUser, username);
        existingUser.update(updatedUser);
        return userRepository.save(existingUser);
    }
}
