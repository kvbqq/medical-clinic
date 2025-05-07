package com.example.medical_clinic.service;

import com.example.medical_clinic.exception.DoctorNotFoundException;
import com.example.medical_clinic.exception.InstitutionNotFoundException;
import com.example.medical_clinic.model.Doctor;
import com.example.medical_clinic.model.Institution;
import com.example.medical_clinic.repository.DoctorRepository;
import com.example.medical_clinic.repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final InstitutionRepository institutionRepository;

    public List<Doctor> getDoctors(Pageable pageable) {
        return doctorRepository.findAll(pageable).getContent();
    }

    public Doctor getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with given email does not exist"));
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void removeDoctor(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with given email does not exist"));
        doctorRepository.delete(doctor);
    }

    public Doctor updateDoctor(String email, Doctor doctor) {
        Doctor existingDoctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with given email does not exist"));
        existingDoctor.update(doctor);
        return doctorRepository.save(existingDoctor);
    }

    public Doctor assignToInstitution(String email, String name) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with given email does not exist"));
        Institution institution = institutionRepository.findByName(name)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution with given name does not exist"));
        doctor.assignInstitution(institution);
        return doctorRepository.save(doctor);
    }
}
