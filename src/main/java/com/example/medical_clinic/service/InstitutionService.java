package com.example.medical_clinic.service;

import com.example.medical_clinic.exception.InstitutionNotFoundException;
import com.example.medical_clinic.model.Institution;
import com.example.medical_clinic.repository.InstitutionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitutionService {
    private final InstitutionRepository institutionRepository;

    public List<Institution> getInstitutions(Pageable pageable) {
        return institutionRepository.findAll(pageable).getContent();
    }

    public Institution getInstitution(String name) {
        return institutionRepository.findByName(name)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution with given name does not exist"));
    }

    @Transactional
    public Institution addInstitution(Institution institution) {
        return institutionRepository.save(institution);
    }

    @Transactional
    public void removeInstitution(String name) {
        Institution institution = institutionRepository.findByName(name)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution with given name does not exist"));
        institutionRepository.delete(institution);
    }

    @Transactional
    public Institution modifyInstitution(String name, Institution newInstitution) {
        Institution institution = institutionRepository.findByName(name)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution with given name does not exist"));
        institution.update(newInstitution);
        return institutionRepository.save(institution);
    }
}
