package com.example.medical_clinic.validation;

import com.example.medical_clinic.exception.UserAlreadyExistsException;
import com.example.medical_clinic.model.User;
import com.example.medical_clinic.repository.UserRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserValidator {
    public static void validateUserCreation(UserRepository userRepository, User user) {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException("User with given username already exists");
        }
    }

    public static void validateUserUpdate(UserRepository userRepository, User user, String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent() && !optionalUser.get().getUsername().equals(user.getUsername())) {
            throw new UserAlreadyExistsException("User with given username already exists");
        }
    }
}
