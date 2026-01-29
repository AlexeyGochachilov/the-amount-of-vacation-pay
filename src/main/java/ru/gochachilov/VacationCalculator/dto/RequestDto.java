package ru.gochachilov.VacationCalculator.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Builder
public record RequestDto(
        BigDecimal avgMonthAmount,
        Integer vacationDays,
        Optional<LocalDate> firstVacationDay) {
}
