package com.example.medical_clinic.service;

import com.example.medical_clinic.exception.DoctorNotFoundException;
import com.example.medical_clinic.exception.PatientNotFoundException;
import com.example.medical_clinic.exception.VisitNotFoundException;
import com.example.medical_clinic.exception.VisitTimeSlotNotAvailableException;
import com.example.medical_clinic.model.Doctor;
import com.example.medical_clinic.model.Patient;
import com.example.medical_clinic.model.Visit;
import com.example.medical_clinic.repository.DoctorRepository;
import com.example.medical_clinic.repository.PatientRepository;
import com.example.medical_clinic.repository.VisitRepository;
import com.example.medical_clinic.validation.VisitValidator;
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

    public List<Visit> getVisitsByPatient(String email, Pageable pageable) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with given email does not exist"));
        return visitRepository.findByPatient(patient, pageable).getContent();
    }

    public List<Visit> getVisitsByDoctor(String email, Pageable pageable) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with given email does not exist"));
        return visitRepository.findByDoctor(doctor, pageable).getContent();
    }

    public Visit createVisit(Visit visit) {
        VisitValidator.validateVisitCreation(visit, visitRepository);
        return visitRepository.save(visit);
    }

    public Visit assignPatient(Long visitId, String email) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new VisitNotFoundException("Visit with given id does not exist"));

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with given email does not exist"));

        if (!visit.isAvailable()) {
            throw new VisitTimeSlotNotAvailableException("Visit is not available");
        }

        visit.setPatient(patient);
        return visitRepository.save(visit);
    }
}
