package com.hr.listener;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by Administrator on 2017/6/12 0012.
 */
public class Main {

    public static void main(String[] args) {

        JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity("myJob","group1").build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger3", "group1")
                .forJob("myJob", "group1")
                .startNow()
                .build();

        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(job, trigger);

            //在这里添加 listener
            scheduler.getListenerManager().addJobListener(new MyJobListener());
            scheduler.getListenerManager().addTriggerListener(new MyTiggerListener());
            scheduler.getListenerManager().addSchedulerListener(new MySchedualListener());

            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
