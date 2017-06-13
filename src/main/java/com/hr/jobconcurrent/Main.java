package com.hr.jobconcurrent;

import com.hr.job1.DumbJob;
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

    public static void main(String[] args) throws SchedulerException {

        JobDetail job = newJob(DisConcurrentJob.class)
                .withIdentity("myJob", "group1")
                .build();

        // 2S 执行一次,但是执行一次要10S
        // @DisallowConcurrentExecution 注解让下一次任务在上一次任务结束时立即执行（上一次任务超时情况下）
        // 如果不加该注解，那么即使上次任务没结束，但是到了执行时间，就要立即执行
        // 但这是针对用一个 jobDetail 而言的，一个Job 可以 定义多个jobDetail，它们之间还是可以并发执行的
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();

        Scheduler scheduler = null;
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(job, trigger);

        // 启动任务调度
        scheduler.start();
    }
}
