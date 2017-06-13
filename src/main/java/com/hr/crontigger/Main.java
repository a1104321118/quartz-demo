package com.hr.crontigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by Administrator on 2017/6/12 0012.
 */
public class Main {

    public static void main(String[] args) {

        JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity("myJob","group1").build();

        // 每周三 8-17 点，每2分钟，并且秒为0的时候，执行一次
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/2 8-17 * WED ?");

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger3", "group1")
                .withSchedule(cronScheduleBuilder.withMisfireHandlingInstructionDoNothing())//这里设置任务丢失策略
                .forJob("myJob", "group1")
                .build();

        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
