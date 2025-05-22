package com.example.medical_clinic.controller;

import com.example.medical_clinic.mapper.VisitMapper;
import com.example.medical_clinic.model.VisitDto;
import com.example.medical_clinic.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
