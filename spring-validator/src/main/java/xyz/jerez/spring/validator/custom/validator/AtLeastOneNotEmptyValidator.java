package xyz.jerez.spring.validator.custom.validator;


import xyz.jerez.spring.validator.custom.constraints.AtLeastOneNotEmpty;
import xyz.jerez.spring.validator.util.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.Map;

/**
 * @author liqilin
 * @since 2020/12/18 15:02
 */
public class AtLeastOneNotEmptyValidator implements ConstraintValidator<AtLeastOneNotEmpty, Object> {

    private String[] fields;

    @Override
    public void initialize(AtLeastOneNotEmpty constraintAnnotation) {
        fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (fields != null) {
            for (String field : fields) {
                try {
                    final Object property = BeanUtils.getProperty(value, field);
                    if (property.getClass().isArray()) {
                        Object[] array = (Object[]) property;
                        if (array.length > 0) {
                            return true;
                        }
                    }
                    if (property instanceof CharSequence) {
                        CharSequence charSequence = (CharSequence) property;
                        if (charSequence.length() > 0) {
                            return true;
                        }
                    }
                    if (property instanceof Collection) {
                        Collection collection = (Collection) property;
                        if (collection.size() > 0) {
                            return true;
                        }
                    }
                    if (property instanceof Map) {
                        Map map = (Map) property;
                        if (map.size() > 0) {
                            return true;
                        }
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
