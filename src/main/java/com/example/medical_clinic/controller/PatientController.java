package com.example.medical_clinic.controller;

import com.example.medical_clinic.mapper.PatientMapStructMapper;
import com.example.medical_clinic.model.ChangePassword;
import com.example.medical_clinic.model.CreatePatientCommand;
import com.example.medical_clinic.model.PatientDto;
import com.example.medical_clinic.service.PatientService;
import com.example.medical_clinic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;
    private final PatientMapStructMapper patientMapper;

    @GetMapping
    public List<PatientDto> getAllPatients() {
        return patientService.getAllPatients().stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{email}")
    public PatientDto getPatient(@PathVariable String email) {
        return patientMapper.toDto(patientService.getPatient(email));
    }

    @PostMapping
    public PatientDto createPatient(@RequestBody CreatePatientCommand command) {
        return patientMapper.toDto(patientService.createPatient(patientMapper.toPatientEntity(command)));
    }

    @DeleteMapping("/{email}")
    public void removePatient(@PathVariable String email) {
        patientService.removePatient(email);
    }

    @PutMapping("/{email}")
    public PatientDto updatePatient(@PathVariable String email, @RequestBody CreatePatientCommand command) {
        return patientMapper.toDto(patientService.updatePatient(email, patientMapper.toPatientEntity(command)));
    }

    @PatchMapping("/{email}/password")
    public PatientDto changePatientPassword(@PathVariable String email, @RequestBody ChangePassword changePassword) {
        return patientMapper.toDto(patientService.changePatientPassword(email, changePassword));
    }
}
