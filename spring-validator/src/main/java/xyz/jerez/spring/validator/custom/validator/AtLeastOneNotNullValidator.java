package xyz.jerez.spring.validator.custom.validator;


import xyz.jerez.spring.validator.custom.constraints.AtLeastOneNotNull;
import xyz.jerez.spring.validator.util.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author liqilin
 * @since 2020/12/18 15:02
 */
public class AtLeastOneNotNullValidator implements ConstraintValidator<AtLeastOneNotNull, Object> {

    private String[] fields;

    @Override
    public void initialize(AtLeastOneNotNull constraintAnnotation) {
        fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (fields != null) {
            for (String field : fields) {
                try {
                    final Object property = BeanUtils.getProperty(value, field);
                    if (property != null) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }
}
