package com.hr.job1;

import org.quartz.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class DumbJob implements Job{

    public void execute(JobExecutionContext context) throws JobExecutionException {


        JobKey key = context.getJobDetail().getKey();

        //JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        JobDataMap dataMap = context.getMergedJobDataMap();

        String jobSays = dataMap.getString("jobSays");
        float myFloatValue = dataMap.getFloat("myFloatValue");

        System.out.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);

        List<String> listData = (List<String>) dataMap.get("listData");
        for (String data : listData){
            System.out.println("listData: " + data);
        }


    }
}
