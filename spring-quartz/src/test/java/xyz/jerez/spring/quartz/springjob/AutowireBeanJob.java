package xyz.jerez.spring.quartz.springjob;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author liqilin
 * @since 2020/11/10 13:42
 */
@Slf4j
public class AutowireBeanJob extends QuartzJobBean {

    /**
     * 集成QuartzJobBean后，可以直接注入spring的bean
     */
    @Autowired
    private ApplicationContext context;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("context:{}",context);
    }

}
