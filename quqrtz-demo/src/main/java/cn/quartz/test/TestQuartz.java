package cn.quartz.test;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class TestQuartz {
    public static void main(String[] args) {
        try {
            Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("trigger", "group")
                    .withSchedule(SimpleScheduleBuilder
                            .simpleSchedule()
                            .withIntervalInMilliseconds(1)
                            .repeatForever())
                    .build();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
