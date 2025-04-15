package com.example.medical_clinic.controller;

import com.example.medical_clinic.mapper.PatientMapStructMapper;
import com.example.medical_clinic.model.ChangePassword;
import com.example.medical_clinic.model.CreatePatientCommand;
import com.example.medical_clinic.model.PatientDto;
import com.example.medical_clinic.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Patient", description = "Patients operations")
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;
    private final PatientMapStructMapper patientMapper;

    @Operation(summary = "Get all Patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patients found")
    })
    @GetMapping
    public List<PatientDto> getAllPatients() {
        return patientService.getAllPatients().stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get Patient by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient found"),
            @ApiResponse(responseCode = "404", description = "Patient with given email does not exist")
    })
    @GetMapping("/{email}")
    public PatientDto getPatient(@PathVariable String email) {
        return patientMapper.toDto(patientService.getPatient(email));
    }

    @Operation(summary = "Create new Patient")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Patient data")
    })
    @PostMapping
    public PatientDto createPatient(@RequestBody CreatePatientCommand command) {
        return patientMapper.toDto(patientService.createPatient(patientMapper.toPatientEntity(command)));
    }

    @Operation(summary = "Delete Patient by email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Patient with given email does not exist")
    })
    @DeleteMapping("/{email}")
    public void removePatient(@PathVariable String email) {
        patientService.removePatient(email);
    }

    @Operation(summary = "Update Patient data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient with given email does not exist")
    })
    @PutMapping("/{email}")
    public PatientDto updatePatient(@PathVariable String email, @RequestBody CreatePatientCommand command) {
        return patientMapper.toDto(patientService.updatePatient(email, patientMapper.toPatientEntity(command)));
    }

    @Operation(summary = "Change Patient's password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "404", description = "Patient with given email does not exist")
    })
    @PatchMapping("/{email}/password")
    public PatientDto changePatientPassword(@PathVariable String email, @RequestBody ChangePassword changePassword) {
        return patientMapper.toDto(patientService.changePatientPassword(email, changePassword));
    }
}
