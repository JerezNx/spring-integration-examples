package xyz.jerez.spring.quartz;

import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import xyz.jerez.spring.quartz.job.AutowireDataJob;
import xyz.jerez.spring.quartz.job.PrintDataJob;
import xyz.jerez.spring.quartz.job.PrintWordsJob;

import java.util.concurrent.TimeUnit;

/**
 * 不依赖spring的方式，使用quartz
 *
 * @author liqilin
 * @since 2020/11/10 11:21
 */
public class SchedulerTests {

    @Test
    void test1() throws SchedulerException, InterruptedException {
        // 1、创建调度器Scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
        JobDetail jobDetail = JobBuilder.newJob(PrintWordsJob.class)
                .withIdentity("job1", "group1").build();
        // 3、构建Trigger实例,每隔1s执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
                //立即生效
                .startNow()
                .build();
        //4、执行
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("--------scheduler start ! ------------");
        scheduler.start();

        //睡眠
        TimeUnit.SECONDS.sleep(5);
        scheduler.shutdown();
        System.out.println("--------scheduler shutdown ! ------------");
    }

    @Test
    void test2() throws SchedulerException, InterruptedException {
        // 1、创建调度器Scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("name", "lql");
        JobDetail jobDetail = JobBuilder.newJob(PrintDataJob.class)
                .withIdentity("job1", "group1")
                .usingJobData(jobDataMap)
                .usingJobData("age", 24)
                .build();
        // 3、构建Trigger实例,每隔1s执行一次
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "triggerGroup1")
                .usingJobData("name", "test")
                .startNow()//立即生效
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)//每隔1s执行一次
                        .repeatForever()).build();//一直执行
        //4、执行
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("--------scheduler start ! ------------");
        scheduler.start();

        //睡眠
        TimeUnit.MINUTES.sleep(1);
        scheduler.shutdown();
        System.out.println("--------scheduler shutdown ! ------------");
    }

    @Test
    void test3() throws SchedulerException, InterruptedException {
        // 1、创建调度器Scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", "lql");
        JobDetail jobDetail = JobBuilder.newJob(AutowireDataJob.class)
                .withIdentity("job1", "group1")
                .usingJobData(jobDataMap)
                .usingJobData("jobAge", 24)
                .build();
        // 3、构建Trigger实例,每隔1s执行一次
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "triggerGroup1")
                .usingJobData("triggerName", "test")
                .startNow()
//                每隔5秒
                .withSchedule(CronScheduleBuilder.cronSchedule("00/5 * * ? * * *")).build();
        //4、执行
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("--------scheduler start ! ------------");
        scheduler.start();

        //睡眠
        TimeUnit.MINUTES.sleep(1);
        scheduler.shutdown();
        System.out.println("--------scheduler shutdown ! ------------");
    }
}
