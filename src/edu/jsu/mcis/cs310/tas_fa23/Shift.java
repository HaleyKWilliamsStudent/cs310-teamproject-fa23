
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
    private HashMap<Integer, DailySchedule> dailySchedules;
    
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
    
    public DailySchedule getDailySchedule(DayOfWeek day) {
        return dailySchedules.get(day.ordinal());
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
