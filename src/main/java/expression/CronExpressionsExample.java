package expression;

import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class CronExpressionsExample {
    private static final String GROUP_NAME = "CroneExamples";

    // Fire at current time + 1 min every day
    public static Trigger fireAfterAMinEveryDayStartingAt(int startHr, int startMin, int startSec, int nowHr, int nowMin) {
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("fireAfterAMinStartingAt", GROUP_NAME)
            .startAt(DateBuilder.todayAt(startHr, startMin, startSec))
            .withSchedule(cronSchedule("Fire every day starting at (" + startHr + ":" + startMin + ":" + startSec + "), start the job in a min from now",
                "0 " + (nowMin + 1) + " " + nowHr + " * * ? *"))
            .build();
        return trigger;
    }

    // Fire at 10:15am every day during the year 2015
    public static Trigger fireAt1015MinEveryDayDuring2015() {
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("fireAt1015MinEveryDayDuring2015", GROUP_NAME)
            .withSchedule(cronSchedule("Fire at 10:15am every day during the year 2015",
                "0 0 15 10 * * 20015"))
            .build();
        return trigger;
    }

    // Fire between 10AM and 3PM every day
    public static Trigger fireBetween10To3EveryDay() {
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("fireBetween10To3EveryDay", GROUP_NAME)
            .withSchedule(cronSchedule("Fire between 10AM and 3PM every day",
                "0 0 10-15 * * ?"))
            .build();
        return trigger;
    }

    // Fire between 10AM and 3PM on weekends SAT-SUN
    public static Trigger fireBetween10to3OnWeekends() {
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("fireBetween10to3OnWeekends", GROUP_NAME)
            .withSchedule(cronSchedule("Fire between 10AM and 3PM on weekends SAT-SUN",
                "0 0 10-15 * * SAT-SUN"))
            .build();
        return trigger;
    }

    // Fire only on certain days of month (14,18 and 21)
    public static Trigger fireOnlyOnCertainDaysInMonth() {
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("fireOnlyOnCertainDaysInMonth", GROUP_NAME)
            .withSchedule(cronSchedule("Fire only on certain days of month",
                "0 0 10 14,18,21 * ?"))
            .build();
        return trigger;
    }

    //Fire every 2 minutes starting at 7:46am and ending at 7:58am, every day
    public static Trigger fireAfterTwoMinFrom7_46To7_58() {
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("fireAfterTwoMinFrom7_46To7_58", GROUP_NAME)
            .withSchedule(cronSchedule("Fire only on certain days of month",
                "0 46/2 7 * * ?"))
            .build();
        return trigger;
    }

    private static CronScheduleBuilder cronSchedule(String desc, String cronExpression) {
        System.out.println(desc + "->(" + cronExpression + ")");
        return CronScheduleBuilder.cronSchedule(cronExpression);
    }
}