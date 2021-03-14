package xyz.jerez.spring.quartz;

import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.jerez.spring.quartz.springjob.AutowireBeanJob2;

/**
 * @author liqilin
 * @since 2021/3/1 15:47
 */
@SpringBootTest
public class SchedulerTempTest {

    @Autowired
    private Scheduler scheduler;

    @Test
    void startJob() throws SchedulerException, InterruptedException {
        final String name = "test3";
        addJob(name, name);
        while (true) {

        }
    }

    @Test
    void deleteJob() throws SchedulerException {
        JobKey jobKey = new JobKey("test2");
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.deleteJob(jobKey);
    }


    void addJob(String jobName, String triggerName) {
        JobDetail jobDetail = JobBuilder.newJob(AutowireBeanJob2.class)
                .withIdentity(jobName).build();
        // 3、构建Trigger实例,每隔1s执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName)
                //立即生效
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .build();
        //4、执行
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
