package gr.dit.tenants.entities.validation;

import lombok.SneakyThrows;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Date;


public class DatePeriodValidator implements ConstraintValidator<DatePeriod, Object> {

    private String startDateFieldName;
    private String endDateFieldName;

    @Override
    public void initialize(DatePeriod constraintAnnotation) {
        startDateFieldName = constraintAnnotation.startingDateField();
        endDateFieldName = constraintAnnotation.endingDateField();

    }

    @SneakyThrows
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) {
            return true;
        }

        Class<?> clazz = obj.getClass();
        Field startDateField = clazz.getDeclaredField(startDateFieldName);
        startDateField.setAccessible(true);
        Field endDateField = clazz.getDeclaredField(endDateFieldName);
        endDateField.setAccessible(true);

        Date startDate = (Date) startDateField.get(obj);
        Date endDate = (Date) endDateField.get(obj);

        if(startDate == null || endDate == null)
            return true;

        return startDate.compareTo(endDate) <= 0;
    }
}