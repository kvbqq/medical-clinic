package com.example.medical_clinic.service;

import com.example.medical_clinic.exception.DoctorNotFoundException;
import com.example.medical_clinic.exception.InstitutionNotFoundException;
import com.example.medical_clinic.model.Doctor;
import com.example.medical_clinic.model.Institution;
import com.example.medical_clinic.model.User;
import com.example.medical_clinic.repository.DoctorRepository;
import com.example.medical_clinic.repository.InstitutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DoctorServiceTest {
    DoctorService doctorService;
    DoctorRepository doctorRepository;
    InstitutionRepository institutionRepository;

    @BeforeEach
    void setup() {
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.institutionRepository = Mockito.mock(InstitutionRepository.class);
        this.doctorService = new DoctorService(doctorRepository, institutionRepository);
    }

    @Test
    void getDoctors_doctorsExist_doctorsReturned() {
        // given
        User user1 = new User(1L, "1", "1");
        Doctor doctor1 = new Doctor(1L, "1@test.com", "1", "1", "chirurg", user1, null);
        User user2 = new User(2L, "2", "2");
        Doctor doctor2 = new Doctor(2L, "2@test.com", "2", "2", "chirurg", user2, null);
        User user3 = new User(3L, "3", "3");
        Doctor doctor3 = new Doctor(3L, "3@test.com", "3", "3", "chirurg", user3, null);

        PageImpl<Doctor> page = new PageImpl<>(List.of(doctor1, doctor2, doctor3));
        when(doctorRepository.findAll(any(Pageable.class))).thenReturn(page);

        // when
        List<Doctor> result = doctorService.getDoctors(PageRequest.of(1, 2));

        // then
        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(1L, result.get(0).getId()),
                () -> assertEquals("1@test.com", result.get(0).getEmail()),
                () -> assertEquals("1", result.get(0).getFirstName()),
                () -> assertEquals("1", result.get(0).getLastName()),
                () -> assertEquals("chirurg", result.get(0).getSpecialization()),
                () -> assertEquals(2L, result.get(1).getId()),
                () -> assertEquals("2@test.com", result.get(1).getEmail()),
                () -> assertEquals(3L, result.get(2).getId()),
                () -> assertEquals("3@test.com", result.get(2).getEmail())
        );
    }

    @Test
    void getDoctorByEmail_doctorExists_doctorReturned() {
        // given
        User user = new User(1L, "1", "1");
        Doctor doctor = new Doctor(1L, "1@test.com", "1", "1", "chirurg", user, null);

        when(doctorRepository.findByEmail("1@test.com")).thenReturn(Optional.of(doctor));

        // when
        Doctor result = doctorService.getDoctorByEmail("1@test.com");

        //then
        assertEquals("1@test.com", result.getEmail());
    }

    @Test
    void getDoctorByEmail_doctorDoesNotExist_throwsException() {
        // given
        when(doctorRepository.findByEmail("nonexistent@test.com")).thenReturn(Optional.empty());

        // when & then
        assertThrows(DoctorNotFoundException.class,
                () -> doctorService.getDoctorByEmail("nonexistent@test.com"));
    }

    @Test
    void createDoctor_doctorCanBeCreated_doctorCreated() {
        // given
        User user = new User(1L, "1", "1");
        Doctor doctor = new Doctor(1L, "1@test.com", "1", "1", "chirurg", user, null);

        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        // when
        Doctor result = doctorService.createDoctor(doctor);

        // then
        Mockito.verify(doctorRepository).save(doctor);
        assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("1@test.com", result.getEmail()),
                () -> assertEquals("1", result.getFirstName()),
                () -> assertEquals("1", result.getLastName()),
                () -> assertEquals("chirurg", result.getSpecialization())
        );
    }

    @Test
    void removeDoctor_doctorExists_doctorRemoved() {
        // given
        User user = new User(1L, "1", "1");
        Doctor doctor = new Doctor(1L, "1@test.com", "1", "1", "chirurg", user, null);

        when(doctorRepository.findByEmail("1@test.com")).thenReturn(Optional.of(doctor));

        // when
        doctorService.removeDoctor("1@test.com");

        // then
        Mockito.verify(doctorRepository).delete(doctor);
    }

    @Test
    void removeDoctor_doctorDoesNotExist_throwsException() {
        // given
        when(doctorRepository.findByEmail("nonexistent@test.com")).thenReturn(Optional.empty());

        // when & then
        assertThrows(DoctorNotFoundException.class,
                () -> doctorService.removeDoctor("nonexistent@test.com"));
    }

    @Test
    void updateDoctor_doctorExists_doctorUpdated() {
        // given
        User user = new User(1L, "1", "1");
        Doctor existingDoctor = new Doctor(1L, "1@test.com", "1", "1", "chirurg", user, new ArrayList<>());
        Doctor doctor = new Doctor(1L, "2@test.com", "2", "2", "onkolog", user, new ArrayList<>());

        when(doctorRepository.findByEmail("1@test.com")).thenReturn(Optional.of(existingDoctor));
        when(doctorRepository.save(existingDoctor)).thenReturn(existingDoctor);

        // when
        Doctor result = doctorService.updateDoctor("1@test.com", doctor);

        // then
        Mockito.verify(doctorRepository).save(existingDoctor);
        assertAll(
                () -> assertEquals(doctor.getEmail(), result.getEmail()),
                () -> assertEquals(doctor.getFirstName(), result.getFirstName()),
                () -> assertEquals(doctor.getLastName(), result.getLastName()),
                () -> assertEquals(doctor.getSpecialization(), result.getSpecialization())
        );
    }

    @Test
    void updateDoctor_doctorDoesNotExist_throwsException() {
        // given
        when(doctorRepository.findByEmail("nonexistent@test.com")).thenReturn(Optional.empty());

        Doctor doctor = new Doctor(1L, "updated@test.com", "Updated", "Doctor", "Onkolog", new User(1L, "user1", "pass1"), new ArrayList<>());

        // when & then
        assertThrows(DoctorNotFoundException.class,
                () -> doctorService.updateDoctor("nonexistent@test.com", doctor));
    }

    @Test
    void assignToInstitution_doctorAndInstitutionExist_institutionAssignedToDoctor() {
        // given
        User user = new User(1L, "1", "1");
        Doctor doctor = new Doctor(1L, "1@test.com", "1", "1", "chirurg", user, new ArrayList<>());
        Institution institution = new Institution(1L, "test", "warszawa", "00-000", "kolorowa", "24", null);

        when(doctorRepository.findByEmail("1@test.com")).thenReturn(Optional.of(doctor));
        when(institutionRepository.findByName("test")).thenReturn(Optional.of(institution));
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        // when
        Doctor result = doctorService.assignToInstitution("1@test.com", "test");

        // then
        Mockito.verify(doctorRepository).save(doctor);
        assertEquals(doctor.getInstitutions(), result.getInstitutions());
    }

    @Test
    void assignToInstitution_doctorDoesNotExist_throwsException() {
        // given
        when(doctorRepository.findByEmail("nonexistent@test.com")).thenReturn(Optional.empty());

        // when & then
        assertThrows(DoctorNotFoundException.class,
                () -> doctorService.assignToInstitution("nonexistent@test.com", "SomeInstitution"));
    }

    @Test
    void assignToInstitution_institutionDoesNotExist_throwsException() {
        // given
        Doctor doctor = new Doctor(1L, "1@test.com", "First", "Last", "chirurg", new User(1L, "user1", "pass1"), new ArrayList<>());

        when(doctorRepository.findByEmail("1@test.com")).thenReturn(Optional.of(doctor));
        when(institutionRepository.findByName("NonexistentInstitution")).thenReturn(Optional.empty());

        // when & then
        assertThrows(InstitutionNotFoundException.class,
                () -> doctorService.assignToInstitution("1@test.com", "NonexistentInstitution"));
    }
}
