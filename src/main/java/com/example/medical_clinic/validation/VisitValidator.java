package com.example.medical_clinic.validation;

import com.example.medical_clinic.exception.InvalidVisitTimeException;
import com.example.medical_clinic.exception.VisitTimeSlotNotAvailableException;
import com.example.medical_clinic.model.Visit;
import com.example.medical_clinic.repository.VisitRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VisitValidator {
    public static void validateVisitCreation(Visit visit, VisitRepository visitRepository) {
        validateVisitTime(visit);
        validateOverlappingTime(visit, visitRepository);
    }

    private static void validateVisitTime(Visit visit) {
        if (visit.getStartVisitDate().getMinute() % 15 != 0 || visit.getEndVisitDate().getMinute() % 15 != 0) {
            throw new InvalidVisitTimeException("Start and end times must be at 15-minute intervals");
        }

        if (!visit.getStartVisitDate().isBefore(visit.getEndVisitDate())) {
            throw new InvalidVisitTimeException("Start time must be before end time");
        }

        if (visit.getStartVisitDate().isBefore(LocalDateTime.now())) {
            throw new InvalidVisitTimeException("Visit cannot start in the past");
        }
    }

    private static void validateOverlappingTime(Visit visit, VisitRepository visitRepository) {
        List<Visit> overlappingVisits = visitRepository.findOverlappingVisits(visit.getDoctor().getId(), visit.getStartVisitDate(), visit.getEndVisitDate());
        if (!overlappingVisits.isEmpty()) {
            throw new VisitTimeSlotNotAvailableException("Visit creation is not possible due to overlapping hours");
        }
    }
}
