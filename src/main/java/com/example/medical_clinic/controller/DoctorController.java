package com.example.medical_clinic.controller;

import com.example.medical_clinic.mapper.DoctorMapper;
import com.example.medical_clinic.model.CreateDoctorCommand;
import com.example.medical_clinic.model.DoctorDto;
import com.example.medical_clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @GetMapping
    public List<DoctorDto> getDoctors() {
        return doctorService.getDoctors().stream()
                .map(doctorMapper::toDto)
                .toList();
    }

    @GetMapping("/{email}")
    public DoctorDto getDoctorByEmail(@PathVariable("email") String email) {
        return doctorMapper.toDto(doctorService.getDoctorByEmail(email));
    }

    @PostMapping
    public DoctorDto createDoctor(@RequestBody CreateDoctorCommand command) {
        return doctorMapper.toDto(doctorService.createDoctor(doctorMapper.toDoctorEntity(command)));
    }

    @DeleteMapping("/{email}")
    public void removeDoctor(@PathVariable("email") String email) {
        doctorService.removeDoctor(email);
    }

    @PutMapping("/{email}")
    public DoctorDto updateDoctor(@PathVariable("email") String email, @RequestBody CreateDoctorCommand command) {
        return doctorMapper.toDto(doctorService.updateDoctor(email, doctorMapper.toDoctorEntity(command)));
    }

    @PatchMapping("/{email}/assign/{name}")
    public DoctorDto assignToInstitution(@PathVariable("email") String email, @PathVariable("name") String name) {
        return doctorMapper.toDto(doctorService.assignToInstitution(email, name));
    }
}
