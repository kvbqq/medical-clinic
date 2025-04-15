package com.example.medical_clinic.controller;

import com.example.medical_clinic.mapper.UserMapper;
import com.example.medical_clinic.model.CreateUserCommand;
import com.example.medical_clinic.model.UserDto;
import com.example.medical_clinic.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "Users operations")
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Get all users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users found")
    })
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get User by username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User with given username does not exist")
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/{username}")
    public UserDto getUser(@PathVariable String username) {
        return userMapper.toDto(userService.getUser(username));
    }

    @Operation(summary = "Create new User")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid User data")
    })
    @PostMapping
    public UserDto createUser(@RequestBody CreateUserCommand command) {
        return userMapper.toDto(userService.createUser(userMapper.toUserEntity(command)));
    }

    @Operation(summary = "Delete a user by username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User with given username does not exist")
    })
    @DeleteMapping("/{username}")
    public void removeUser(@PathVariable String username) {
        userService.removeUser(username);
    }

    @Operation(summary = "Update User data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User with given username does not exist")
    })
    @PutMapping("/{username}")
    public UserDto updateUser(@PathVariable String username, @RequestBody CreateUserCommand command) {
        return userMapper.toDto(userService.updateUser(username, userMapper.toUserEntity(command)));
    }
}
