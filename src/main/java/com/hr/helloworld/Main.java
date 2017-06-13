package com.hr.helloworld;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException, SchedulerException {

        //生成一个 helloworld（要干什么）
        JobDetail jobDetail = newJob(MyJob.class).withIdentity("job1", "group1").build();

        //设置一个触发器，或定时器（在什么时候干）
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();

        // 用sechduler 进行设置 job调度（建立时间表：在什么时候干什么）
        Scheduler scheduler = null;
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(jobDetail, trigger);

        // 启动任务调度
        scheduler.start();


        Thread.sleep(1000);
        scheduler.shutdown();

    }
}
