package com.hr.jobstore;

import org.quartz.*;

/**
 * Created by Administrator on 2017/6/13 0013.
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class MyJob implements Job{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        int count = jobDataMap.getInt("count") + 1;
        jobDataMap.put("count", count);
        System.out.println("job executed! This time is " + count);
    }
}
