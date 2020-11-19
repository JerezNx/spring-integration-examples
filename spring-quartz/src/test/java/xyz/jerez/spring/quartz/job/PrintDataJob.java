package xyz.jerez.spring.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author liqilin
 * @since 2020/11/10 11:33
 */
@Slf4j
public class PrintDataJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
       log.info("job detail data:{}",jobDataMap.getString("name"));
        JobDataMap jobDataMap1 = jobExecutionContext.getTrigger().getJobDataMap();
       log.info("trigger detail data:{}",jobDataMap1.getString("name"));
    }
}
