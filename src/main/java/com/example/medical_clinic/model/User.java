package com.example.medical_clinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @OneToOne(mappedBy = "user")
    private Patient patient;

    public void update(User updatedUser) {
        this.username = updatedUser.getUsername();
        this.password = updatedUser.getPassword();
    }
}
