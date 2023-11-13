package edu.jsu.mcis.cs310.tas_fa23;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

/**
 *
 * @author Grant
 */
public class DailySchedule {
    private Integer id = null;
    private final LocalTime shiftstart;
    private final LocalTime shiftstop;
    private final Integer roundinterval;
    private final Integer graceperiod;
    private final Integer dockpenalty;
    private final LocalTime lunchstart;
    private final LocalTime lunchstop;
    private final Integer lunchthreshold;
    private final int lunchduration;
    private final int shiftduration;
    
    /**
     * 
     * @param dailyScheduleMap A map to initialize the shift object
     */
    public DailySchedule(HashMap<String, Object> dailyScheduleMap) {
        this.id = (Integer)dailyScheduleMap.get("id");
        this.shiftstart = (LocalTime)dailyScheduleMap.get("shiftstart");
        this.shiftstop = (LocalTime)dailyScheduleMap.get("shiftstop");
        this.roundinterval = (Integer)dailyScheduleMap.get("roundinterval");
        this.graceperiod = (Integer)dailyScheduleMap.get("graceperiod");
        this.dockpenalty = (Integer)dailyScheduleMap.get("dockpenalty");
        this.lunchstart = (LocalTime)dailyScheduleMap.get("lunchstart");
        this.lunchstop = (LocalTime)dailyScheduleMap.get("lunchstop");
        this.lunchthreshold = (Integer)dailyScheduleMap.get("lunchthreshold");
        this.shiftduration = (int)ChronoUnit.MINUTES.between(shiftstart, shiftstop);
        this.lunchduration = (int)ChronoUnit.MINUTES.between(lunchstart, lunchstop);
    }
    
    /**
     * 
     * @return Returns an int containing the number of minutes in a lunch break
     */
    public int getLunchDuration() {
        return lunchduration;
    }
    
    /**
     * 
     * @return Returns an int containing the number of minutes in a shift
     */
    public int getShiftDuration() {
        return shiftduration;
    }
    
    public Integer getId() {
        return id;
    }
    
    public LocalTime getShiftStart() {
        return shiftstart;
    }
    
    public LocalTime getShiftStop() {
        return shiftstop;
    }
    
    public Integer getRoundInterval() {
        return roundinterval;
    }
    
    public Integer getGracePeriod() {
        return graceperiod;
    }
    
    public Integer getDockPenalty() {
        return dockpenalty;
    }
    
    public LocalTime getLunchStart() {
        return lunchstart;
    }
    
    public LocalTime getLunchStop() {
        return lunchstop;
    }
    
    public Integer getLunchThreshold() {
        return lunchthreshold;
    }
}
