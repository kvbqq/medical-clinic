package com.example.medical_clinic.controller;

import com.example.medical_clinic.mapper.PatientMapper;
import com.example.medical_clinic.model.ChangePassword;
import com.example.medical_clinic.model.CreatePatientCommand;
import com.example.medical_clinic.model.PatientDto;
import com.example.medical_clinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<PatientDto> getAllPatients() {
        return patientService.getAllPatients().stream()
                .map(PatientMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{email}")
    public PatientDto getPatient(@PathVariable String email) {
        return PatientMapper.toDto(patientService.getPatient(email));
    }

    @PostMapping
    public PatientDto createPatient(@RequestBody CreatePatientCommand command) {
        return PatientMapper.toDto(patientService.createPatient(PatientMapper.toPatientEntity(command)));
    }

    @DeleteMapping("/{email}")
    public void removePatient(@PathVariable String email) {
        patientService.removePatient(email);
    }

    @PutMapping("/{email}")
    public PatientDto updatePatient(@PathVariable String email, @RequestBody CreatePatientCommand command) {
        return PatientMapper.toDto(patientService.updatePatient(email, PatientMapper.toPatientEntity(command)));
    }

    @PatchMapping("/{email}/password")
    public PatientDto changePatientPassword(@PathVariable String email, @RequestBody ChangePassword changePassword) {
        return PatientMapper.toDto(patientService.changePatientPassword(email, changePassword));
    }
}
