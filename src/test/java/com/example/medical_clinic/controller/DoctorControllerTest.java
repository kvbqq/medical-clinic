package com.example.medical_clinic.controller;

import com.example.medical_clinic.model.CreateDoctorCommand;
import com.example.medical_clinic.model.Doctor;
import com.example.medical_clinic.model.Institution;
import com.example.medical_clinic.model.User;
import com.example.medical_clinic.service.DoctorService;
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
public class DoctorControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    DoctorService doctorService;

    @Test
    void shouldGetDoctors() throws Exception {
        User user = new User(1L, "user1", "pass1");
        List<Doctor> doctors = List.of(
                new Doctor(1L, "1@test.com", "first1", "last1", "chirurg", user, null),
                new Doctor(2L, "2@test.com", "first2", "last2", "laryngolog", user, null),
                new Doctor(3L, "3@test.com", "first3", "last3", "kardiolog", user, null)
        );

        when(doctorService.getDoctors(any())).thenReturn(doctors);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].email").value("1@test.com"))
                .andExpect(jsonPath("$[0].firstName").value("first1"))
                .andExpect(jsonPath("$[0].lastName").value("last1"))
                .andExpect(jsonPath("$[0].specialization").value("chirurg"))
                .andExpect(jsonPath("$[1].email").value("2@test.com"))
                .andExpect(jsonPath("$[2].email").value("3@test.com"));
    }

    @Test
    void shouldGetDoctor() throws Exception {
        User user = new User(1L, "user1", "pass1");
        Doctor doctor = new Doctor(1L, "1@test.com", "first1", "last1", "chirurg", user, null);

        when(doctorService.getDoctorByEmail("1@test.com")).thenReturn(doctor);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/{email}", "1@test.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("1@test.com"))
                .andExpect(jsonPath("$.firstName").value("first1"))
                .andExpect(jsonPath("$.lastName").value("last1"))
                .andExpect(jsonPath("$.specialization").value("chirurg"));
    }

    @Test
    void shouldCreateDoctor() throws Exception {
        User user = new User(1L, "user1", "pass1");
        Doctor doctor = new Doctor(1L, "1@test.com", "first1", "last1", "chirurg", user, null);
        CreateDoctorCommand command = new CreateDoctorCommand("1@test.com", "first1", "last1", "chirurg", user);

        when(doctorService.createDoctor(any())).thenReturn(doctor);

        mockMvc.perform(MockMvcRequestBuilders.post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("1@test.com"))
                .andExpect(jsonPath("$.firstName").value("first1"))
                .andExpect(jsonPath("$.lastName").value("last1"))
                .andExpect(jsonPath("$.specialization").value("chirurg"));
    }

    @Test
    void shouldDeleteDoctor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/doctors/{email}", "1@test.com"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateDoctor() throws Exception {
        User user = new User(1L, "user1", "pass1");
        Doctor doctor = new Doctor(1L, "1@test.com", "first1", "last1", "chirurg", user, null);
        CreateDoctorCommand command = new CreateDoctorCommand("1@test.com", "first1", "last1", "chirurg", user);

        when(doctorService.updateDoctor(eq("1@test.com"), any())).thenReturn(doctor);

        mockMvc.perform(MockMvcRequestBuilders.put("/doctors/{email}", "1@test.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("1@test.com"))
                .andExpect(jsonPath("$.firstName").value("first1"))
                .andExpect(jsonPath("$.lastName").value("last1"))
                .andExpect(jsonPath("$.specialization").value("chirurg"));
    }

    @Test
    void shouldAssignDoctorToInstitution() throws Exception {
        User user = new User(1L, "1", "1");
        Institution institution = new Institution(1L, "test", "warszawa", "00-000", "kolorowa", "24", null);
        Doctor doctor = new Doctor(1L, "1@test.com", "1", "1", "chirurg", user, List.of(institution));

        when(doctorService.assignToInstitution("1@test.com", "test")).thenReturn(doctor);

        mockMvc.perform(MockMvcRequestBuilders.patch("/doctors/{email}/assign/{name}", "1@test.com", "test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.institutions").isArray())
                .andExpect(jsonPath("$.institutions[0].name").value("test"))
                .andExpect(jsonPath("$.institutions[0].city").value("warszawa"))
                .andExpect(jsonPath("$.institutions[0].postalCode").value("00-000"))
                .andExpect(jsonPath("$.institutions[0].street").value("kolorowa"))
                .andExpect(jsonPath("$.institutions[0].buildingNumber").value("24"));
    }
}
