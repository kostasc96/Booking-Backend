package gr.dit.tenants.entities.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { DatePeriodValidator.class })
@Documented
public @interface DatePeriod {

    String message() default "The end date should be after the starting date." ;

    String startingDateField();
    String endingDateField();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}