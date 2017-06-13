package com.hr.job1;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Arrays;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class Main {


    /**
     * This exapmle can send data to job
     *
     * @param args
     */
    public static void main(String[] args) throws SchedulerException {

        String[] params = {"data1","data2"};
        List<String> listData = Arrays.asList(params);

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("listData", listData);

        // define the job and tie it to our DumbJob class
        JobDetail job = newJob(DumbJob.class)
                .withIdentity("myJob", "group1") // name "myJob", group "group1"
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.141f)
                .usingJobData(jobDataMap)
                .build();

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
        scheduler.scheduleJob(job, trigger);

        // 启动任务调度
        scheduler.start();

        //scheduler.shutdown();
    }
}
