package xyz.jerez.spring.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import xyz.jerez.spring.aop.annotation.Log;
import xyz.jerez.spring.aop.annotation.Logs;

import java.util.Arrays;

/**
 * @author liqilin
 * @since 2020/10/28 15:31
 */
@Aspect
@Component
@Slf4j
public class LogsAspect {

    @Pointcut("@annotation(xyz.jerez.spring.aop.annotation.Logs)")
    public void cut() {
    }

    @AfterReturning(value = "cut()", returning = "rvt")
    public void afterReturning(JoinPoint point, Object rvt) {
        Object[] args = point.getArgs();
        log.info("Args:{}", Arrays.asList(args).toString());
        log.info("rvt:{}", rvt);
        try {
            Logs logs = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(Logs.class);
            Arrays.stream(logs.value()).map(Log::value).forEach(log::info);
        } catch (Throwable throwable) {
            log.error("", throwable);
        }
    }
}
