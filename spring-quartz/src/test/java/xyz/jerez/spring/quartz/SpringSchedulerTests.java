package xyz.jerez.spring.quartz;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.jerez.spring.quartz.springjob.AutowireBeanJob;

import java.util.concurrent.TimeUnit;

/**
 * @author liqilin
 * @since 2020/11/10 13:33
 */
@Slf4j
@SpringBootTest
public class SpringSchedulerTests {

    @Autowired
    private Scheduler scheduler;

    private String JOB_NAME = "job1";
    private String JOB_GROUP = "group1";

    private String TRIGGER_NAME = "trigger1";
    private String TRIGGER_GROUP_NAME = "triggerGroup1";

    @Test
    void test() throws SchedulerException, InterruptedException {
        JobDetail jobDetail = JobBuilder.newJob(AutowireBeanJob.class)
                .withIdentity(JOB_NAME, JOB_GROUP).build();
        // 3、构建Trigger实例,每隔1s执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(TRIGGER_NAME, TRIGGER_GROUP_NAME)
                //立即生效
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .build();
        //4、执行
        scheduler.scheduleJob(jobDetail, trigger);
//        spring.quartz.auto-startup默认为true，即自动启动
//        scheduler.start();
        TimeUnit.SECONDS.sleep(1);
//        spring托管生命周期，无需自己关闭
//        scheduler.shutdown();
    }

    /**
     * 获取任务信息
     */
    @Test
    void getJobInfo() throws SchedulerException {
        JobKey jobKey = new JobKey(JOB_NAME, JOB_GROUP);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        log.info("jobDetail:{}", jobDetail);

        TriggerKey triggerKey = new TriggerKey(TRIGGER_NAME, TRIGGER_GROUP_NAME);
        Trigger trigger = scheduler.getTrigger(triggerKey);
        log.info("trigger:{}", trigger);
    }

    /**
     * 修改任务的调度
     */
    @Test
    void rescheduleJob() throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(TRIGGER_NAME, TRIGGER_GROUP_NAME);
        Trigger oldTrigger = scheduler.getTrigger(triggerKey);

        Trigger newTrigger = TriggerBuilder.newTrigger()
                .withIdentity(TRIGGER_NAME, TRIGGER_GROUP_NAME)
                .startNow()//立即生效
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever()).build();
        scheduler.rescheduleJob(triggerKey, newTrigger);
    }

    /**
     * 暂停某个任务
     */
    @Test
    void pauseJob() throws SchedulerException {
        JobKey jobKey = new JobKey(JOB_NAME, JOB_GROUP);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.pauseJob(jobKey);
//        scheduler.pauseAll();
    }

    /**
     * 恢复某个任务
     */
    @Test
    void resumeJob() throws SchedulerException {
        JobKey jobKey = new JobKey(JOB_NAME, JOB_GROUP);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.resumeJob(jobKey);
//        scheduler.resumeAll();
    }

    @Test
    void deleteJob() throws SchedulerException {
        JobKey jobKey = new JobKey(JOB_NAME, JOB_GROUP);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.deleteJob(jobKey);
    }

}
