package xyz.jerez.spring.quartz.springjob;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.sql.DataSource;

/**
 * @author liqilin
 * @since 2020/11/10 13:42
 */
@Slf4j
public class AutowireBeanJob2 extends QuartzJobBean {

    /**
     * 集成QuartzJobBean后，可以直接注入spring的bean
     */
    @Autowired
    private DataSource dataSource;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("dataSource:{},this.hashCode:{}", dataSource.getClass(), this.hashCode());
    }

}
