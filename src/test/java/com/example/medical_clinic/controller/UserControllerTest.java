package com.example.medical_clinic.controller;

import com.example.medical_clinic.model.CreateUserCommand;
import com.example.medical_clinic.model.User;
import com.example.medical_clinic.service.UserJpaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    UserJpaService userService;

    @Test
    void shouldGetUsers() throws Exception {
        List<User> users = List.of(
                new User(1L, "user1", "pass1"),
                new User(2L, "user2", "pass2"),
                new User(3L, "user3", "pass3")
        );

        when(userService.getUsers(any())).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[0].password").value("pass1"))
                .andExpect(jsonPath("$[1].username").value("user2"))
                .andExpect(jsonPath("$[1].password").value("pass2"))
                .andExpect(jsonPath("$[2].username").value("user3"))
                .andExpect(jsonPath("$[2].password").value("pass3"));
    }

    @Test
    void shouldGetUser() throws Exception {
        User user = new User(1L, "user1", "pass1");

        when(userService.getUser("user1")).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{username}", "user1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"))
                .andExpect(jsonPath("$.password").value("pass1"));
    }

    @Test
    void shouldCreateUser() throws Exception {
        User user = new User(1L, "user1", "pass1");
        CreateUserCommand command = new CreateUserCommand("user1", "pass1");

        when(userService.createUser(any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"))
                .andExpect(jsonPath("$.password").value("pass1"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{username}", "user1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateUser() throws Exception {
        User user = new User(1L, "user1", "pass1");
        CreateUserCommand command = new CreateUserCommand("user1", "pass1");

        when(userService.updateUser(eq("user1"), any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{username}", "user1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"))
                .andExpect(jsonPath("$.password").value("pass1"));
    }
}
