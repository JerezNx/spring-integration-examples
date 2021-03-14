package xyz.jerez.spring.validator.custom.constraints;


import xyz.jerez.spring.validator.custom.validator.AtLeastOneNotNullValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 属性中至少有一个不为Null
 *
 * @author liqilin
 * @since 2020/12/18 15:07
 */
@Documented
@Constraint(validatedBy = {AtLeastOneNotNullValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
@Repeatable(AtLeastOneNotNull.List.class)
public @interface AtLeastOneNotNull {

    String message() default "至少有一个不为空";

    String[] fields() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({TYPE})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        AtLeastOneNotNull[] value();
    }
}
