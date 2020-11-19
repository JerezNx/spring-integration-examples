package xyz.jerez.spring.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

/**
 * @author liqilin
 * @since 2020/11/11 13:56
 */
public class MyJobListener extends JobListenerSupport {

    @Override
    public String getName() {
        return "MyJobListener";
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("listener ...");
    }
}
