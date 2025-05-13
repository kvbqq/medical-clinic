package com.example.medical_clinic.service;

import com.example.medical_clinic.model.Patient;
import com.example.medical_clinic.model.User;
import com.example.medical_clinic.repository.PatientJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PatientServiceTest {
    PatientJpaRepository patientRepository;
    PatientJpaService patientService;

    @BeforeEach
    void setup() {
        patientRepository = Mockito.mock(PatientJpaRepository.class);
        patientService = new PatientJpaService(patientRepository);
    }

    @Test
    void getPatients_patientsExist_patientsReturned() {
        // given
        Patient patient1 = new Patient(1L, "p1@test.com", "ID1", "Name1", "Last1", "123456", LocalDate.of(2000, 1, 1), new User(1L, "user1", "pass1"));
        Patient patient2 = new Patient(2L, "p2@test.com", "ID2", "Name2", "Last2", "654321", LocalDate.of(1990, 1, 1), new User(2L, "user2", "pass2"));

        PageImpl<Patient> page = new PageImpl<>(List.of(patient1, patient2));
        when(patientRepository.findAll(any(Pageable.class))).thenReturn(page);

        // when
        List<Patient> result = patientService.getPatients(PageRequest.of(0, 2));

        // then
        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals("p1@test.com", result.get(0).getEmail()),
                () -> assertEquals("p2@test.com", result.get(1).getEmail())
        );
    }

    @Test
    void getPatient_patientExists_patientReturned() {
        // given
        Patient patient = new Patient(1L, "p1@test.com", "ID1", "Name1", "Last1", "123456", LocalDate.of(2000, 1, 1), new User(1L, "user1", "pass1"));
        when(patientRepository.findByEmail("p1@test.com")).thenReturn(Optional.of(patient));

        // when
        Patient result = patientService.getPatient("p1@test.com");

        // then
        assertEquals("p1@test.com", result.getEmail());
    }

    @Test
    void createPatient_patientCanBeCreated_patientCreated() {
        // given


        // when


        // then

    }

    @Test
    void removePatient_patientExists_patientRemoved() {
        // given
        Patient patient = new Patient(1L, "p1@test.com", "ID1", "Name1", "Last1", "123456", LocalDate.of(2000, 1, 1), new User(1L, "user1", "pass1"));
        when(patientRepository.findByEmail("p1@test.com")).thenReturn(Optional.of(patient));

        // when
        patientService.removePatient("p1@test.com");

        // then
        Mockito.verify(patientRepository).delete(patient);
    }

    @Test
    void updatePatient_patientExists_patientUpdated() {
        // given


        // when


        // then

    }
}
