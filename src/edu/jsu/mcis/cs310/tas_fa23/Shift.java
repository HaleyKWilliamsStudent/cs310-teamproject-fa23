
package edu.jsu.mcis.cs310.tas_fa23;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;


/**
 *
 * @author Grant
 */
public class Shift {
    private Integer id = null;
    private String description;
    private LocalTime shiftstart;
    private LocalTime shiftstop;
    private Integer roundinterval;
    private Integer graceperiod;
    private Integer dockpenalty;
    private LocalTime lunchstart;
    private LocalTime lunchstop;
    private Integer lunchthreshold;
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
    
}
