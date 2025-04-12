package com.example.medical_clinic.repository;

import com.example.medical_clinic.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final List<User> users;

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    public User addUser(User user) {
        users.add(user);
        return user;
    }

    public Boolean removeUser(String username) {
        return users.removeIf(user -> user.getUsername().equalsIgnoreCase(username));
    }

    public User updateUser(User existingUser, User updatedUser) {
        existingUser.update(updatedUser);
        return existingUser;
    }
}
