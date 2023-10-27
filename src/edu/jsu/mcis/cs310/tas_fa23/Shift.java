
package edu.jsu.mcis.cs310.tas_fa23;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;



/**
 * 
 * @author Grant
 */
public class Shift {
    private Integer id = null;
    private final String description;
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
     * @param shiftMap A map to initialize the shift object
     */
    public Shift(HashMap<String, Object> shiftMap) {
        this.id = (Integer)shiftMap.get("id");
        this.description = shiftMap.get("description").toString();
        this.shiftstart = (LocalTime)shiftMap.get("shiftstart");
        this.shiftstop = (LocalTime)shiftMap.get("shiftstop");
        this.roundinterval = (Integer)shiftMap.get("roundinterval");
        this.graceperiod = (Integer)shiftMap.get("graceperiod");
        this.dockpenalty = (Integer)shiftMap.get("dockpenalty");
        this.lunchstart = (LocalTime)shiftMap.get("lunchstart");
        this.lunchstop = (LocalTime)shiftMap.get("lunchstop");
        this.lunchthreshold = (Integer)shiftMap.get("lunchthreshold");
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
    
    public String getDescription() {
        return description;
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
    
    @Override
    /**
     * returns a formatted version of all fields belonging to a shift
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(description).append(": ");
        s.append(shiftstart).append(" - ").append(shiftstop);
        s.append(" (").append(shiftduration).append(" minutes); Lunch: ");
        s.append(lunchstart).append(" - ").append(lunchstop).append(" (");
        s.append(lunchduration).append(" minutes)");
        return s.toString();
    }
    
}
