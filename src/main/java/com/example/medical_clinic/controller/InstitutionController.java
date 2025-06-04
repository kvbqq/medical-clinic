package com.example.medical_clinic.controller;

import com.example.medical_clinic.mapper.InstitutionMapper;
import com.example.medical_clinic.model.CreateInstitutionCommand;
import com.example.medical_clinic.model.InstitutionDto;
import com.example.medical_clinic.service.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institutions")
public class InstitutionController {
    private final InstitutionService institutionService;
    private final InstitutionMapper institutionMapper;

    @GetMapping
    public List<InstitutionDto> getInstitutions(Pageable pageable) {
        return institutionService.getInstitutions(pageable).stream()
                .map(institutionMapper::toDto)
                .toList();
    }

    @GetMapping("/{name}")
    public InstitutionDto getInstitutionByName(@PathVariable String name) {
        return institutionMapper.toDto(institutionService.getInstitution(name));
    }

    @PostMapping
    public InstitutionDto addInstitution(@RequestBody CreateInstitutionCommand command) {
        return institutionMapper.toDto(institutionService.addInstitution(institutionMapper.toInstitutionEntity(command)));
    }

    @DeleteMapping("/{name}")
    public void removeInstitution(@PathVariable String name) {
        institutionService.removeInstitution(name);
    }

    @PutMapping("/{name}")
    public InstitutionDto modifyInstitution(@PathVariable String name, @RequestBody CreateInstitutionCommand command) {
        return institutionMapper.toDto(institutionService.modifyInstitution(name, institutionMapper.toInstitutionEntity(command)));
    }
}
