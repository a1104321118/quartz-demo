package com.hr.jobstore;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13 0013.
 */
public class Main {

    public static void main(String[] args) {
        //startSchedule();
        resumeJob();
    }

    /**
     * 开始一个simpleSchedule()调度
     */
    public static void startSchedule() {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("count",0);
            // 1、创建一个JobDetail实例，指定Quartz
            JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                    .withIdentity("job1_6", "jGroup1").storeDurably(true) //防止job执行完之后被删除
                    .usingJobData(jobDataMap)
                    .build();

            SimpleScheduleBuilder builder = SimpleScheduleBuilder
                    .repeatSecondlyForTotalCount(10);

            //CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule("0/2 * * * * ?");

            // 2、创建Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1_6", "tGroup1").startNow()
                    .withSchedule(builder)
                    .build();

            // 3、创建Scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            // 4、调度执行
            scheduler.scheduleJob(jobDetail, trigger);
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //关闭调度器
            scheduler.shutdown();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从数据库中找到已经存在的job，并重新开户调度
     */
    public static void resumeJob() {
        try {

            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobKey jobKey = new JobKey("job1_6", "jGroup1");
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            //SELECT TRIGGER_NAME, TRIGGER_GROUP FROM TRIGGERS WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?
            // 重新恢复在jGroup1组中，名为job1_1的 job的触发器运行
            if(triggers.size() > 0){
                for (Trigger tg : triggers) {
                    // 根据类型判断
                    if ((tg instanceof CronTrigger) || (tg instanceof SimpleTrigger)) {
                        // 恢复job运行
                        scheduler.resumeJob(jobKey);
                    }
                }
                scheduler.start();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

