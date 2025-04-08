package com.example.medical_clinic.controller;

import com.example.medical_clinic.model.ChangePassword;
import com.example.medical_clinic.model.Patient;
import com.example.medical_clinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{email}")
    public Patient getPatient(@PathVariable String email) {
        return patientService.getPatient(email);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }

    @DeleteMapping("/{email}")
    public void removePatient(@PathVariable String email) {
        patientService.removePatient(email);
    }

    @PutMapping("/{email}")
    public Patient updatePatient(@PathVariable String email, @RequestBody Patient patient) {
        return patientService.updatePatient(email, patient);
    }

    @PatchMapping("/{email}/password")
    public Patient changePatientPassword(@PathVariable String email, @RequestBody ChangePassword changePassword) {
        return patientService.changePatientPassword(email, changePassword);
    }
}
