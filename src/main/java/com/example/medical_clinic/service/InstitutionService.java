package com.example.medical_clinic.service;

import com.example.medical_clinic.exception.InstitutionNotFoundException;
import com.example.medical_clinic.model.Institution;
import com.example.medical_clinic.repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
//import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitutionService {
    private final InstitutionRepository institutionRepository;

//    public List<Institution> getInstitutions() {
//        return institutionRepository.findAll();
//    }

    public Page<Institution> getInstitutions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return institutionRepository.findAll(pageable);
    }

    public Institution getInstitution(String name) {
        return institutionRepository.findByName(name)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution with given name does not exist"));
    }

    public Institution addInstitution(Institution institution) {
        return institutionRepository.save(institution);
    }

    public void removeInstitution(String name) {
        Institution institution = institutionRepository.findByName(name)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution with given name does not exist"));
        institutionRepository.delete(institution);
    }

    public Institution modifyInstitution(String name, Institution newInstitution) {
        Institution institution = institutionRepository.findByName(name)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution with given name does not exist"));
        institution.update(newInstitution);
        return institutionRepository.save(institution);
    }
}
