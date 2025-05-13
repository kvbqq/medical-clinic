package com.example.medical_clinic.service;

import com.example.medical_clinic.model.User;
import com.example.medical_clinic.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    UserJpaRepository userRepository;
    UserJpaService userService;

    @BeforeEach
    void setup() {
        userRepository = Mockito.mock(UserJpaRepository.class);
        userService = new UserJpaService(userRepository);
    }

    @Test
    void getUsers_usersExist_usersReturned() {
        // given
        User user1 = new User(1L, "user1", "pass1");
        User user2 = new User(2L, "user2", "pass2");

        PageImpl<User> page = new PageImpl<>(List.of(user1, user2));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);

        // when
        List<User> result = userService.getUsers(PageRequest.of(0, 2));

        // then
        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals("user1", result.get(0).getUsername()),
                () -> assertEquals("user2", result.get(1).getUsername())
        );
    }

    @Test
    void getUser_userExists_userReturned() {
        // given
        User user = new User(1L, "user1", "pass1");
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));

        // when
        User result = userService.getUser("user1");

        // then
        assertEquals("user1", result.getUsername());
    }

    @Test
    void createUser_userCanBeCreated_userCreated() {
        // given


        // when


        // then

    }

    @Test
    void removeUser_userExists_userRemoved() {
        // given
        User user = new User(1L, "user1", "pass1");
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));

        // when
        userService.removeUser("user1");

        // then
        Mockito.verify(userRepository).delete(user);
    }

    @Test
    void updateUser_userExists_userUpdated() {
        // given


        // when


        // then

    }
}
