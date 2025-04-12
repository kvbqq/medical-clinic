package com.example.medical_clinic.controller;

import com.example.medical_clinic.mapper.UserMapper;
import com.example.medical_clinic.model.CreateUserCommand;
import com.example.medical_clinic.model.UserDto;
import com.example.medical_clinic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/{username}")
    public UserDto getUser(@PathVariable String username) {
        return userMapper.toDto(userService.getUser(username));
    }

    @PostMapping
    public UserDto createUser(@RequestBody CreateUserCommand command) {
        return userMapper.toDto(userService.createUser(userMapper.toUserEntity(command)));
    }

    @DeleteMapping("/{username}")
    public void removeUser(@PathVariable String username) {
        userService.removeUser(username);
    }

    @PutMapping("/{username}")
    public UserDto updateUser(@PathVariable String username, @RequestBody CreateUserCommand command) {
        return userMapper.toDto(userService.updateUser(username, userMapper.toUserEntity(command)));
    }
}
