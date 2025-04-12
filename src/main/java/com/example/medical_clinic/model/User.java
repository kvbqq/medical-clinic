package com.example.medical_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;
    private String password;

    public void update(User updatedUser) {
        this.username = updatedUser.getUsername();
        this.password = updatedUser.getPassword();
    }
}
