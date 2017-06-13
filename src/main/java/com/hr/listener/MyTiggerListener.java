package com.hr.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * Created by Administrator on 2017/6/12 0012.
 */
public class MyTiggerListener implements TriggerListener {

    public String getName() {
        return null;
    }

    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {

    }

    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        return false;
    }

    public void triggerMisfired(Trigger trigger) {

    }

    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {

    }
}
