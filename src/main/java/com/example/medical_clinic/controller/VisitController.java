package com.example.medical_clinic.controller;

import com.example.medical_clinic.mapper.VisitMapper;
import com.example.medical_clinic.model.CreateVisitCommand;
import com.example.medical_clinic.model.VisitDto;
import com.example.medical_clinic.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visits")
public class VisitController {
    private final VisitService visitService;
    private final VisitMapper visitMapper;

    @GetMapping
    public List<VisitDto> getVisits(Pageable pageable) {
        return visitService.getVisits(pageable).stream()
                .map(visitMapper::toDto)
                .toList();
    }

    @GetMapping("/patient/{email}")
    public List<VisitDto> getVisitsByPatient(@PathVariable String email, Pageable pageable) {
        return visitService.getVisitsByPatient(email, pageable).stream()
                .map(visitMapper::toDto)
                .toList();
    }

    @GetMapping("/doctor/{email}")
    public List<VisitDto> getVisitsByDoctor(@PathVariable String email, Pageable pageable) {
        return visitService.getVisitsByDoctor(email, pageable).stream()
                .map(visitMapper::toDto)
                .toList();
    }

    @PostMapping
    public VisitDto createVisit(@RequestBody CreateVisitCommand command) {
        return visitMapper.toDto(visitService.createVisit(visitMapper.toVisitEntity(command)));
    }

    @PatchMapping("/{id}/assign/{email}")
    public VisitDto assignPatient(@PathVariable Long id, @PathVariable String email) {
        return visitMapper.toDto(visitService.assignPatient(id, email));
    }
}
