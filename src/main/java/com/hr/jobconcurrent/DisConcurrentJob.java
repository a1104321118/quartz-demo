package com.hr.jobconcurrent;

import org.quartz.*;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
@DisallowConcurrentExecution //不允许一个 jobDetail 并发执行（即，当调用时间到了，但是上一次还没执行完）
@PersistJobDataAfterExecution //在执行完的时候保存 jobData，一般和 上面这个注解合用，防止并发
public class DisConcurrentJob implements Job{

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("job run at" + new Date());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
