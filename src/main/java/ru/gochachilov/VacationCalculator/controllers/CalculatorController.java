package ru.gochachilov.VacationCalculator.controllers;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gochachilov.VacationCalculator.dto.RequestDto;
import ru.gochachilov.VacationCalculator.dto.ResponseDto;
import ru.gochachilov.VacationCalculator.service.CalcAmountService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class CalculatorController {

    private final CalcAmountService calcAmountService;

    @GetMapping(path = "/calculate")
    public ResponseEntity<ResponseDto> calcVacationAmount(
            @RequestParam(value = "avgAmount")
            @PositiveOrZero(message = "Average amount can not be negative")
            BigDecimal avgMonthAmount,

            @RequestParam(value= "vacationDays")
            @Positive(message = "Vacation days can not be negative")
            @Min(1)
            @Max(28)
            Integer vacationDays,

            @RequestParam(value = "firstDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate firstDate) {

        log.info("Controller level: method calcVacationAmount - is start!");

        RequestDto requestDto =
                new RequestDto(avgMonthAmount, vacationDays, Optional.ofNullable(firstDate));
        ResponseEntity<ResponseDto> response =
                ResponseEntity.ok().body(calcAmountService.calcVacAmount(requestDto));

        log.info("Controller level: method calcVacationAmount answer - {}", response.getBody());

        return response;
    }

}
