package xyz.jerez.spring.validator.util;

import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 静态校验
 *
 * @author liqilin
 * @since 2020/12/16 9:48
 */
public class ValidateUtil {

    /**
     * 验证某个bean的参数
     *
     * @param object 被校验的参数
     * @param groups 当前分组
     * @throws IllegalArgumentException 如果参数校验不成功则抛出此异常
     */
    public static <T> void validate(T object, Class<?>... groups) {
        validate(object, constraintViolation -> {
            throw new IllegalArgumentException(constraintViolation.getMessage());
        }, groups);
    }

    /**
     * 验证某个bean的参数
     *
     * @param object 被校验的参数
     * @param action 校验不通过时的处理方法
     * @param groups 当前分组
     */
    public static <T> void validate(T object, Consumer<ConstraintViolation<T>> action, Class<?>... groups) {
        //获得验证器
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        //执行验证
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object, groups);
        //如果有验证信息，则将第一个取出来包装成异常返回
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            constraintViolations.forEach(action);
        }
    }
}
