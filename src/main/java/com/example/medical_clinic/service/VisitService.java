package com.example.medical_clinic.service;

import com.example.medical_clinic.model.Visit;
import com.example.medical_clinic.repository.DoctorRepository;
import com.example.medical_clinic.repository.PatientRepository;
import com.example.medical_clinic.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public List<Visit> getVisits(Pageable pageable) {
        return visitRepository.findAll(pageable).getContent();
    }

}
