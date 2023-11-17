package edu.jsu.mcis.cs310.tas_fa23;

import java.time.LocalDate;

/**
 *
 * @author Grant
 */
public class ScheduleOverride {
    
    private final LocalDate start;
    private final LocalDate end;
    private final String badgeid;
    private final int day;
    private final int dailyscheduleid;
    
    public ScheduleOverride(LocalDate start, LocalDate end, String badgeid, int day, int dailyscheduleid) {
        this.start = start;
        this.end = end;
        this.badgeid = badgeid;
        this.day = day;
        this.dailyscheduleid = dailyscheduleid;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public String getBadgeid() {
        return badgeid;
    }

    public int getDay() {
        return day;
    }

    public int getDailyscheduleid() {
        return dailyscheduleid;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Start: ").append(start).append(", ").append("End: ").append(end).append(", ");
        sb.append("Badge Id: ").append(badgeid).append(", ").append("Day: ").append(day);
        sb.append(", ").append("Daily Schedule Id: ").append(dailyscheduleid);
        
        return sb.toString();
    }
}
