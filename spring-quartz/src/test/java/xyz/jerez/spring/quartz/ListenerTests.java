package xyz.jerez.spring.quartz;

import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.jerez.spring.quartz.listener.MyJobListener;
import xyz.jerez.spring.quartz.springjob.AutowireBeanJob;

import java.util.concurrent.TimeUnit;

/**
 * @author liqilin
 * @since 2020/11/11 13:59
 */
@SpringBootTest
public class ListenerTests {

    private String JOB_NAME = "job1";
    private String JOB_GROUP_NAME = "group1";

    private String TRIGGER_NAME = "trigger1";
    private String TRIGGER_GROUP_NAME = "triggerGroup1";

    @Autowired
    private Scheduler scheduler;

    void addListener() throws SchedulerException {
        scheduler.getListenerManager().addJobListener(new MyJobListener(), GroupMatcher.groupEquals(JOB_GROUP_NAME));
    }

    @Test
    void test() throws SchedulerException, InterruptedException {
        addListener();
        doJob();
        TimeUnit.SECONDS.sleep(10);
    }

    private void doJob() throws SchedulerException, InterruptedException {
        JobDetail jobDetail = JobBuilder.newJob(AutowireBeanJob.class)
                .withIdentity(JOB_NAME, JOB_GROUP_NAME).build();
        // 3、构建Trigger实例,每隔1s执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(TRIGGER_NAME, TRIGGER_GROUP_NAME)
                //立即生效
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();
        //4、执行
        scheduler.scheduleJob(jobDetail, trigger);
    }

}
