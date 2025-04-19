package com.example.medical_clinic.controller;

import com.example.medical_clinic.mapper.PatientMapStructMapper;
import com.example.medical_clinic.model.ApiError;
import com.example.medical_clinic.model.CreatePatientCommand;
import com.example.medical_clinic.model.PatientDto;
import com.example.medical_clinic.service.PatientJpaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
    private final PatientJpaService patientService;
    private final PatientMapStructMapper patientMapper;

    @Operation(summary = "Get all Patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patients found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class))})
    })
    @GetMapping
    public List<PatientDto> getAllPatients() {
        return patientService.getAllPatients().stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get Patient by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class))}),
            @ApiResponse(responseCode = "404", description = "Patient with given email does not exist", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @GetMapping("/{email}")
    public PatientDto getPatient(@PathVariable String email) {
        return patientMapper.toDto(patientService.getPatient(email));
    }

    @Operation(summary = "Create new Patient")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient created successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Patient data", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @PostMapping
    public PatientDto createPatient(@RequestBody CreatePatientCommand command) {
        return patientMapper.toDto(patientService.createPatient(patientMapper.toPatientEntity(command)));
    }

    @Operation(summary = "Delete Patient by email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient deleted successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Patient with given email does not exist", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @DeleteMapping("/{email}")
    public void removePatient(@PathVariable String email) {
        patientService.removePatient(email);
    }

    @Operation(summary = "Update Patient data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient updated successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class))}),
            @ApiResponse(responseCode = "404", description = "Patient with given email does not exist", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @PutMapping("/{email}")
    public PatientDto updatePatient(@PathVariable String email, @RequestBody CreatePatientCommand command) {
        return patientMapper.toDto(patientService.updatePatient(email, patientMapper.toPatientEntity(command)));
    }
}
