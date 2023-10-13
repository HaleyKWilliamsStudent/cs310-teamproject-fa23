
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
    private LocalTime lunchduration;
    private LocalTime shiftduration;
    
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
    }
    
    public long getLunchDuration(LocalTime lunchstart, LocalTime lunchstop) {
        long minutes = ChronoUnit.MINUTES.between(this.lunchstart, this.lunchstop);
        return minutes;
    }
    
    public long getShiftDuration(LocalTime shiftstart, LocalTime shiftstop) {
        long minutes = ChronoUnit.MINUTES.between(this.shiftstart, this.shiftstop);
        return minutes;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public LocalTime getShiftStart() {
        return this.shiftstart;
    }
    
    public LocalTime getShiftStop() {
        return this.shiftstop;
    }
    
    public Integer getRoundInterval() {
        return this.roundinterval;
    }
    
    public Integer getGracePeriod() {
        return this.graceperiod;
    }
    
    public Integer getDockPenalty() {
        return this.dockpenalty;
    }
    
    public LocalTime getLunchStart() {
        return this.lunchstart;
    }
    
    public LocalTime getLunchStop() {
        return this.lunchstop;
    }
    
    public Integer getLunchThreshold() {
        return this.lunchthreshold;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(description).append(": ");
        s.append(shiftstart).append(" - ").append(shiftstop);
        s.append(" (").append(getShiftDuration(shiftstart, shiftstop)).append(" minutes); Lunch: ");
        s.append(lunchstart).append(" - ").append(lunchstop).append(" (");
        s.append(getLunchDuration(lunchstart, lunchstop)).append(" minutes)");
        return s.toString();
    }
    
}
