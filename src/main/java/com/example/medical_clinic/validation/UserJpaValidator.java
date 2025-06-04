package com.example.medical_clinic.validation;

import com.example.medical_clinic.exception.UserAlreadyExistsException;
import com.example.medical_clinic.model.User;
import com.example.medical_clinic.repository.UserJpaRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserJpaValidator {
    public static void validateUserCreation(UserJpaRepository userRepository, User user) {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException("User with given username already exists");
        }
    }

    public static void validateUserUpdate(UserJpaRepository userRepository, User user, String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent() && !optionalUser.get().getUsername().equals(user.getUsername())) {
            throw new UserAlreadyExistsException("User with given username already exists");
        }
    }
}
