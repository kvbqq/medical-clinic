package com.example.medical_clinic.controller;

import com.example.medical_clinic.model.CreateInstitutionCommand;
import com.example.medical_clinic.model.Institution;
import com.example.medical_clinic.service.InstitutionService;
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
public class InstitutionControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    InstitutionService institutionService;

    @Test
    void shouldGetInstitutions() throws Exception {
        List<Institution> institutions = List.of(
                new Institution(1L, "test1", "warszawa", "00-000", "kolorowa", "24", null),
                new Institution(2L, "test2", "warszawa", "11-111", "czarna", "23", null),
                new Institution(3L, "test3", "warszawa", "22-222", "biała", "7", null)
        );

        when(institutionService.getInstitutions(any())).thenReturn(institutions);

        mockMvc.perform(MockMvcRequestBuilders.get("/institutions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("test1"))
                .andExpect(jsonPath("$[0].city").value("warszawa"))
                .andExpect(jsonPath("$[0].postalCode").value("00-000"))
                .andExpect(jsonPath("$[0].street").value("kolorowa"))
                .andExpect(jsonPath("$[0].buildingNumber").value("24"))
                .andExpect(jsonPath("$[1].name").value("test2"))
                .andExpect(jsonPath("$[2].name").value("test3"));
    }

    @Test
    void shouldGetInstitution() throws Exception {
        Institution institution = new Institution(1L, "test", "warszawa", "00-000", "kolorowa", "24", null);

        when(institutionService.getInstitution("test")).thenReturn(institution);

        mockMvc.perform(MockMvcRequestBuilders.get("/institutions/{name}", "test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.city").value("warszawa"))
                .andExpect(jsonPath("$.postalCode").value("00-000"))
                .andExpect(jsonPath("$.street").value("kolorowa"))
                .andExpect(jsonPath("$.buildingNumber").value("24"));
    }

    @Test
    void shouldCreateInstitution() throws Exception {
        Institution institution = new Institution(1L, "test", "warszawa", "00-000", "kolorowa", "24", null);
        CreateInstitutionCommand command = new CreateInstitutionCommand("test", "warszawa", "00-000", "kolorowa", "24", null);

        when(institutionService.addInstitution(any())).thenReturn(institution);

        mockMvc.perform(MockMvcRequestBuilders.post("/institutions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.city").value("warszawa"))
                .andExpect(jsonPath("$.postalCode").value("00-000"))
                .andExpect(jsonPath("$.street").value("kolorowa"))
                .andExpect(jsonPath("$.buildingNumber").value("24"));
    }

    @Test
    void shouldDeleteInstitution() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/institutions/{name}", "test"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateInstitution() throws Exception {
        Institution institution = new Institution(1L, "test", "warszawa", "00-000", "kolorowa", "24", null);
        CreateInstitutionCommand command = new CreateInstitutionCommand("test", "warszawa", "00-000", "kolorowa", "24", null);

        when(institutionService.modifyInstitution(eq("test"), any())).thenReturn(institution);

        mockMvc.perform(MockMvcRequestBuilders.put("/institutions/{name}", "test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.city").value("warszawa"))
                .andExpect(jsonPath("$.postalCode").value("00-000"))
                .andExpect(jsonPath("$.street").value("kolorowa"))
                .andExpect(jsonPath("$.buildingNumber").value("24"));
    }
}
