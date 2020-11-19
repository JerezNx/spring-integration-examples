package xyz.jerez.spring.quartz.job;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author liqilin
 * @since 2020/11/10 13:14
 */
@Setter
@Slf4j
public class AutowireDataJob implements Job {

    private String jobName;
    private int jobAge;
    private String triggerName;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("jobName:{}", jobName);
        log.info("jobAge:{}", jobAge);
        log.info("triggerName:{}", triggerName);
    }
}
