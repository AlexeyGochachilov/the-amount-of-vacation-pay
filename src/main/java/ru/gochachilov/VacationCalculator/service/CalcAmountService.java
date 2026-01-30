package ru.gochachilov.VacationCalculator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gochachilov.VacationCalculator.dto.RequestDto;
import ru.gochachilov.VacationCalculator.dto.ResponseDto;
import ru.gochachilov.VacationCalculator.util.CalculateParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class CalcAmountService {
    private Set<LocalDate> vacDaysBase;

    public ResponseDto calcVacAmount(RequestDto requestDtoToCalc){

        log.info("Service level: method calcVacAmount is start");
        if(requestDtoToCalc.firstVacationDay().isEmpty()){

            log.info("Service level: method calcVacAmount - calculation with two parameters finished!");
            return new ResponseDto(
                    simpleCalc(requestDtoToCalc.avgMonthAmount(),
                    requestDtoToCalc.vacationDays())
            );
        } else {

            log.info("Service level: method calcVacAmount - calculation with three parameters finished!");
            return new ResponseDto(
                    withHolidaysCalc(requestDtoToCalc.avgMonthAmount(),
                    requestDtoToCalc.vacationDays(),
                    requestDtoToCalc.firstVacationDay().get())
            );
        }
    }

    private BigDecimal simpleCalc(BigDecimal avgMonthAmount, Integer numberVacDays){
        BigDecimal avgDay = avgDayAmount(avgMonthAmount);
        return BigDecimal
                .valueOf(numberVacDays)
                .multiply(avgDay.subtract(incomeTaxPerDay(avgDay)))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal withHolidaysCalc(BigDecimal avgMonthAmount,
                                        Integer vacationDays,
                                        LocalDate firstVacationDay) {

        loadVacDaysBase(firstVacationDay, vacationDays)
                .removeAll(CalculateParam.getInstance().getVocationDays());
        var totalVacDays = vacDaysBase.size();

        return simpleCalc(avgMonthAmount, totalVacDays);
    }

    private BigDecimal avgDayAmount(BigDecimal avgMonthAmount){
        return avgMonthAmount.divide(CalculateParam.getInstance().getAvgDay(),2, RoundingMode.HALF_UP);
    }

    private BigDecimal incomeTaxPerDay(BigDecimal avgDayAmount){
        return avgDayAmount.multiply(CalculateParam.getInstance().getIncomeTax());
    }

    private Set<LocalDate> loadVacDaysBase(LocalDate firstDay, Integer numberOfDays){
        vacDaysBase = new HashSet<>();
        vacDaysBase.add(firstDay);
        for (long i = 1; i < numberOfDays; i++){
            vacDaysBase.add(firstDay.plusDays(i));
        }
        return vacDaysBase;
    }
}
