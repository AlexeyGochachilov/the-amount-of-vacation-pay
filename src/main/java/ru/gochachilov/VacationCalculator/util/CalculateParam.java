package ru.gochachilov.VacationCalculator.util;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;


public class CalculateParam {

    private static CalculateParam INSTANCE;
    private final LocalDate isNow = LocalDate.now();

    @Getter
    private final BigDecimal avgDay = BigDecimal.valueOf(29.3);

    @Getter
    private final BigDecimal incomeTax = BigDecimal.valueOf(0.13);

    @Getter
    @Setter
    private Set<LocalDate> vocationDays = Set.of(
            LocalDate.of(isNow.getYear(), 1,1),
            LocalDate.of(isNow.getYear(), 1,2),
            LocalDate.of(isNow.getYear(), 1,3),
            LocalDate.of(isNow.getYear(), 1,4),
            LocalDate.of(isNow.getYear(), 1,5),
            LocalDate.of(isNow.getYear(), 1,6),
            LocalDate.of(isNow.getYear(), 1,7),
            LocalDate.of(isNow.getYear(), 1,8),
            LocalDate.of(isNow.getYear(), 1,9),
            LocalDate.of(isNow.getYear(), 2,23),
            LocalDate.of(isNow.getYear(), 3,9),
            LocalDate.of(isNow.getYear(), 5,1),
            LocalDate.of(isNow.getYear(), 5,11),
            LocalDate.of(isNow.getYear(), 6,12),
            LocalDate.of(isNow.getYear(), 11,4),
            LocalDate.of(isNow.getYear(), 12,31)
    );

    private CalculateParam() {
    }

    public synchronized static CalculateParam getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CalculateParam();
        }
        return INSTANCE;
    }
}
