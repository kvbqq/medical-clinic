package com.example.medical_clinic.controller;

import com.example.medical_clinic.model.CreatePatientCommand;
import com.example.medical_clinic.model.Patient;
import com.example.medical_clinic.model.User;
import com.example.medical_clinic.service.PatientJpaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    PatientJpaService patientService;

    @Test
    void shouldGetPatients() throws Exception {
        List<Patient> patients = List.of(
                new Patient(1L, "p1@test.com", "ID1", "Name1", "Last1", "123456", LocalDate.of(2000, 1, 1), new User(1L, "user1", "pass1")),
                new Patient(2L, "p2@test.com", "ID2", "Name2", "Last2", "654321", LocalDate.of(1990, 1, 1), new User(2L, "user2", "pass2"))
        );

        when(patientService.getPatients(any())).thenReturn(patients);

        mockMvc.perform(MockMvcRequestBuilders.get("/patients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].email").value("p1@test.com"))
                .andExpect(jsonPath("$[0].firstName").value("Name1"))
                .andExpect(jsonPath("$[0].lastName").value("Last1"))
                .andExpect(jsonPath("$[0].phoneNumber").value("123456"))
                .andExpect(jsonPath("$[0].birthday").value("2000-01-01"))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].email").value("p2@test.com"));
    }

    @Test
    void shouldGetPatient() throws Exception {
        Patient patient = new Patient(1L, "p1@test.com", "ID1", "Name1", "Last1", "123456", LocalDate.of(2000, 1, 1), new User(1L, "user1", "pass1"));

        when(patientService.getPatient("1@test.com")).thenReturn(patient);

        mockMvc.perform(MockMvcRequestBuilders.get("/patients/{email}", "1@test.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("p1@test.com"))
                .andExpect(jsonPath("$.firstName").value("Name1"))
                .andExpect(jsonPath("$.lastName").value("Last1"))
                .andExpect(jsonPath("$.phoneNumber").value("123456"))
                .andExpect(jsonPath("$.birthday").value("2000-01-01"))
                .andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    void shouldCreatePatient() throws Exception {
        Patient patient = new Patient(1L, "p1@test.com", "ID1", "Name1", "Last1", "123456", LocalDate.of(2000, 1, 1), new User(1L, "user1", "pass1"));
        CreatePatientCommand command = new CreatePatientCommand("p1@test.com", "ID1", "Name1", "Last1", "123456", LocalDate.of(2000, 1, 1), new User(1L, "user1", "pass1"));

        when(patientService.createPatient(any())).thenReturn(patient);

        mockMvc.perform(MockMvcRequestBuilders.post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("p1@test.com"))
                .andExpect(jsonPath("$.firstName").value("Name1"))
                .andExpect(jsonPath("$.lastName").value("Last1"))
                .andExpect(jsonPath("$.phoneNumber").value("123456"))
                .andExpect(jsonPath("$.birthday").value("2000-01-01"))
                .andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    void shouldDeletePatient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/{email}", "p1@test.com"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdatePatient() throws Exception {
        User user = new User(1L, "user1", "pass1");
        Patient patient = new Patient(1L, "p1@test.com", "ID1", "Name1", "Last1", "123456", LocalDate.of(2000, 1, 1), user);
        CreatePatientCommand command = new CreatePatientCommand("p1@test.com", "ID1", "Name1", "Last1", "123456", LocalDate.of(2000, 1, 1), user);

        when(patientService.updatePatient(eq("p1@test.com"), any())).thenReturn(patient);

        mockMvc.perform(MockMvcRequestBuilders.put("/patients/{email}", "p1@test.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("p1@test.com"))
                .andExpect(jsonPath("$.firstName").value("Name1"))
                .andExpect(jsonPath("$.lastName").value("Last1"))
                .andExpect(jsonPath("$.phoneNumber").value("123456"))
                .andExpect(jsonPath("$.birthday").value("2000-01-01"))
                .andExpect(jsonPath("$.username").value("user1"));
    }
}
