package xyz.jerez.spring.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

/**
 * @author liqilin
 * @since 2021/3/13 16:14
 */
@Slf4j
@EnableScheduling
@Configuration
public class Tasks {

    public void doSth(String taskName) {
        doSth(taskName, 2, TimeUnit.SECONDS);
    }

    public void doSth(String taskName, long sleepTime, TimeUnit timeUnit) {
        log.info("start task {}", taskName);
        try {
            timeUnit.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("end task {}", taskName);
    }

    @Scheduled(fixedRate = 5000)
    public void fixedRateTask() {
        doSth("fixedRateTask");
    }

    @Scheduled(fixedDelay = 5000)
    public void fixedDelayTask() {
        doSth("fixedDelayTask");
    }

    @Scheduled(cron = "${task.corn:0/5 * * * * ?}")
    public void cronTask() {
        doSth("cronTask");
    }

    @Scheduled(fixedRate = 5000)
    public void longTimeTask() {
        doSth("longTimeTask", 1, TimeUnit.MINUTES);
    }

}
