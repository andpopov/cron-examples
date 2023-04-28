package simple;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class Main {
    public static void main(String[] args) throws SchedulerException {
//        StdSchedulerFactory factory = new StdSchedulerFactory();
//        Scheduler scheduler = factory.getScheduler();

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        JobDetail jobDetail
            = JobBuilder
            .newJob()
            .ofType(Hello.class)
            .withIdentity("simple.Hello-job-name")
            .build();
        SimpleTrigger simpleTrigger
            = TriggerBuilder
            .newTrigger()
            .withIdentity("simple.Hello-trigger-name")
            .forJob(jobDetail)
            .withSchedule(SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(1)
                .withRepeatCount(5))
            .build();
        scheduler.start();
        scheduler.scheduleJob(jobDetail ,simpleTrigger);

        final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(() -> {
            try {
                System.out.println("clear");
                scheduler.clear();
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }, 3, TimeUnit.SECONDS);

        executorService.schedule(() -> {
            System.out.println("add new task");
            try {
                JobDetail jobDetail2
                    = JobBuilder
                    .newJob()
                    .ofType(Hello.class)
                    .withIdentity("simple.Hello-job-name 2")
                    .build();
                SimpleTrigger simpleTrigger2
                    = TriggerBuilder
                    .newTrigger()
                    .withIdentity("simple.Hello-trigger-name 2")
                    .forJob(jobDetail2)
                    .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(5))
                    .build();
                scheduler.scheduleJob(jobDetail2 ,simpleTrigger2);
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }, 5, TimeUnit.SECONDS);
    }

    private static CronScheduleBuilder cronSchedule(String desc, String cronExpression) {
        System.out.println(desc + "->(" + cronExpression + ")");
        return CronScheduleBuilder.cronSchedule(cronExpression);
    }
}