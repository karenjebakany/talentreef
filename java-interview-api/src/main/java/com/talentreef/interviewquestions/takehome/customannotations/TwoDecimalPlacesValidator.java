package com.talentreef.interviewquestions.takehome.customannotations;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class TwoDecimalPlacesValidator implements ConstraintValidator<TwoDecimalPlaces, BigDecimal> {

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) return true; // Let @NotNull handle null check
        return value.scale() == 2 && value.compareTo(BigDecimal.ONE) >= 0 && value.compareTo(new BigDecimal("20000")) <= 0;
    }
}
