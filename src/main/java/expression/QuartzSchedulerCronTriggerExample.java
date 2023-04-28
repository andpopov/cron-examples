package expression;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;


public class QuartzSchedulerCronTriggerExample implements ILatch {
    private CountDownLatch latch = new CountDownLatch(1);
    public static void main(String[] args) throws Exception {
        QuartzSchedulerCronTriggerExample quartzSchedulerExample = new QuartzSchedulerCronTriggerExample();
        quartzSchedulerExample.fireJob();
    }

    public void fireJob() throws SchedulerException, InterruptedException {
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        Scheduler scheduler = schedFact.getScheduler();
        scheduler.start();

        // define the job and tie it to our HelloJob class
        JobBuilder jobBuilder = JobBuilder.newJob(MyJob.class);
        JobDataMap data = new JobDataMap();
        data.put("latch", this);

        JobDetail jobDetail = jobBuilder.usingJobData("example", "com.javacodegeeks.quartz.QuartzSchedulerListenerExample")
            .usingJobData(data)
            .withIdentity("myJob", "group1")
            .build();

        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        int min = rightNow.get(Calendar.MINUTE);

        System.out.println("Current time: " + new Date());

        // Tell quartz to schedule the job using our trigger
        // Fire at current time + 1 min every day
        scheduler.scheduleJob(jobDetail, CronExpressionsExample.fireAfterAMinEveryDayStartingAt(10, 20, 20, hour, min));
        latch.await();
        System.out.println("All triggers executed. Shutdown scheduler");
        scheduler.shutdown();
    }

    public void countDown() {
        latch.countDown();
    }
}