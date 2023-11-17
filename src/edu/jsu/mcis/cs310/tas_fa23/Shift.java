package edu.jsu.mcis.cs310.tas_fa23;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;

/**
 *
 * @author Grant
 */
public class Shift {

    private Integer id = null;
    private final String description;
    private final DailySchedule defaultSchedule;
    private final HashMap<Integer, DailySchedule> dailySchedules = new HashMap<>();

    public Shift(Integer id, String desc, DailySchedule defaultSchedule) {
        this.id = id;
        this.description = desc;
        this.defaultSchedule = defaultSchedule;

        dailySchedules.put(1, defaultSchedule);
        dailySchedules.put(2, defaultSchedule);
        dailySchedules.put(3, defaultSchedule);
        dailySchedules.put(4, defaultSchedule);
        dailySchedules.put(5, defaultSchedule);
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    /**
     *
     * @return Returns an int containing the number of minutes in a lunch break
     */
    public int getLunchDuration() {
        return defaultSchedule.getLunchDuration();
    }

    /**
     *
     * @return Returns an int containing the number of minutes in a shift
     */
    public int getShiftDuration() {
        return defaultSchedule.getShiftDuration();
    }

    public Integer getDailyScheduleID() {
        return defaultSchedule.getId();
    }

    public LocalTime getShiftStart() {
        return defaultSchedule.getShiftStart();
    }

    public LocalTime getShiftStop() {
        return defaultSchedule.getShiftStop();
    }

    public Integer getRoundInterval() {
        return defaultSchedule.getRoundInterval();
    }

    public Integer getGracePeriod() {
        return defaultSchedule.getGracePeriod();
    }

    public Integer getDockPenalty() {
        return defaultSchedule.getDockPenalty();
    }

    public LocalTime getLunchStart() {
        return defaultSchedule.getLunchStart();
    }

    public LocalTime getLunchStop() {
        return defaultSchedule.getLunchStop();
    }

    public Integer getLunchThreshold() {
        return defaultSchedule.getLunchThreshold();
    }

    public int getScheduledMinutes() {
        int scheduled = 0;

        for (DailySchedule schedule : dailySchedules.values()) {
            scheduled += schedule.getShiftDuration() - schedule.getLunchDuration();
        }

        return scheduled;
    }

    public DailySchedule getDefaultSchedule() {
        return defaultSchedule;
    }

    public DailySchedule getDailySchedule(DayOfWeek day) {
        DailySchedule schedule;
        if (dailySchedules.get(day.getValue()) != null) {
            schedule = dailySchedules.get(day.getValue());
        } else {
            schedule = getDefaultSchedule();
        }
        return schedule;
    }

    public void overrideSchedule(ScheduleOverride override, DailySchedule dailySchedule) {
        dailySchedules.put(override.getDay(), dailySchedule);
    }

    @Override
    /**
     * returns a formatted version of all fields belonging to a shift
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(description).append(": ");
        s.append(getShiftStart()).append(" - ").append(getShiftStop());
        s.append(" (").append(getShiftDuration()).append(" minutes); Lunch: ");
        s.append(getLunchStart()).append(" - ").append(getLunchStop()).append(" (");
        s.append(getLunchDuration()).append(" minutes)");
        return s.toString();
    }

}
