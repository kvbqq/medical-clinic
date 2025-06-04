package com.example.medical_clinic.service;

import com.example.medical_clinic.exception.InstitutionNotFoundException;
import com.example.medical_clinic.model.Institution;
import com.example.medical_clinic.repository.InstitutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class InstitutionServiceTest {
    InstitutionService institutionService;
    InstitutionRepository institutionRepository;

    @BeforeEach
    void setup() {
        this.institutionRepository = Mockito.mock(InstitutionRepository.class);
        this.institutionService = new InstitutionService(institutionRepository);
    }

    @Test
    void getInstitutions_institutionsExist_institutionsReturned() {
        // given
        Institution institution1 = new Institution(1L, "test1", "warszawa", "00-000", "kolorowa", "24", null);
        Institution institution2 = new Institution(2L, "test2", "warszawa", "11-111", "czarna", "23", null);
        Institution institution3 = new Institution(3L, "test3", "warszawa", "22-222", "biała", "7", null);

        PageImpl<Institution> page = new PageImpl<>(List.of(institution1, institution2, institution3));
        when(institutionRepository.findAll(any(Pageable.class))).thenReturn(page);

        // when
        List<Institution> result = institutionService.getInstitutions(PageRequest.of(1, 2));

        // then
        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(1L, result.get(0).getId()),
                () -> assertEquals("test1", result.get(0).getName()),
                () -> assertEquals(2L, result.get(1).getId()),
                () -> assertEquals("test2", result.get(1).getName()),
                () -> assertEquals(3L, result.get(2).getId()),
                () -> assertEquals("test3", result.get(2).getName())
        );
    }

    @Test
    void getInstitution_institutionExists_institutionReturned() {
        // given
        Institution institution = new Institution(1L, "test1", "warszawa", "00-000", "kolorowa", "24", null);

        when(institutionRepository.findByName("test1")).thenReturn(Optional.of(institution));

        // when
        Institution result = institutionService.getInstitution("test1");

        // then
        assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("test1", result.getName()),
                () -> assertEquals("warszawa", result.getCity()),
                () -> assertEquals("00-000", result.getPostalCode()),
                () -> assertEquals("kolorowa", result.getStreet()),
                () -> assertEquals("24", result.getBuildingNumber())
        );
    }

    @Test
    void getInstitution_institutionDoesNotExist_throwsException() {
        // given
        when(institutionRepository.findByName("nonexistent")).thenReturn(Optional.empty());

        // when & then
        assertThrows(InstitutionNotFoundException.class,
                () -> institutionService.getInstitution("nonexistent"));
    }

    @Test
    void addInstitution_institutionCanBeAdded_institutionCreated() {
        // given
        Institution institution = new Institution(1L, "test1", "warszawa", "00-000", "kolorowa", "24", null);

        when(institutionRepository.save(any(Institution.class))).thenReturn(institution);

        // when
        Institution result = institutionService.addInstitution(institution);

        // then
        Mockito.verify(institutionRepository).save(institution);
        assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("test1", result.getName()),
                () -> assertEquals("warszawa", result.getCity()),
                () -> assertEquals("00-000", result.getPostalCode()),
                () -> assertEquals("kolorowa", result.getStreet()),
                () -> assertEquals("24", result.getBuildingNumber())
        );
    }

    @Test
    void removeInstitution_institutionExist_institutionRemoved() {
        // given
        Institution institution = new Institution(1L, "test1", "warszawa", "00-000", "kolorowa", "24", null);

        when(institutionRepository.findByName("test1")).thenReturn(Optional.of(institution));

        // when
        institutionService.removeInstitution("test1");

        // then
        Mockito.verify(institutionRepository).delete(institution);
    }

    @Test
    void removeInstitution_institutionDoesNotExist_throwsException() {
        // given
        when(institutionRepository.findByName("nonexistent")).thenReturn(Optional.empty());

        // when & then
        assertThrows(InstitutionNotFoundException.class,
                () -> institutionService.removeInstitution("nonexistent"));
    }

    @Test
    void modifyInstitution_institutionExist_institutionModified() {
        // given
        Institution existingInstitution = new Institution(1L, "test1", "warszawa", "00-000", "kolorowa", "24", null);
        Institution institution = new Institution(1L, "test2", "poznań", "11-111", "czarna", "3", null);

        when(institutionRepository.findByName("test1")).thenReturn(Optional.of(existingInstitution));
        when(institutionRepository.save(existingInstitution)).thenReturn(existingInstitution);

        // when
        Institution result = institutionService.modifyInstitution("test1", institution);

        // then
        Mockito.verify(institutionRepository).save(existingInstitution);
        assertAll(
                () -> assertEquals("test2", result.getName()),
                () -> assertEquals("poznań", result.getCity()),
                () -> assertEquals("11-111", result.getPostalCode()),
                () -> assertEquals("czarna", result.getStreet()),
                () -> assertEquals("3", result.getBuildingNumber())
        );
    }

    @Test
    void modifyInstitution_institutionDoesNotExist_throwsException() {
        // given
        when(institutionRepository.findByName("nonexistent")).thenReturn(Optional.empty());

        Institution institution = new Institution(1L, "test", "city", "00-000", "street", "1", null);

        // when & then
        assertThrows(InstitutionNotFoundException.class,
                () -> institutionService.modifyInstitution("nonexistent", institution));
    }
}
