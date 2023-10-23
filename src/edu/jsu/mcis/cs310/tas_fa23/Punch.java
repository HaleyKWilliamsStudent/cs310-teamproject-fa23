package edu.jsu.mcis.cs310.tas_fa23;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Model class for representing a Punch
 */

public class Punch {
    private Integer id = null;
    private final Integer terminalid;
    private final Badge badge;
    private final LocalDateTime originalTimestamp;
    private LocalDateTime adjustedTimestamp;
    private PunchAdjustmentType adjustmenttype;
    private final EventType punchType;
    
    /**
     * Constructs a new Punch object when an Employee makes a new punch.
     * 
     * @param terminalid Identifier for the terminal where the punch occurred.
     * @param badge Badge object representing the employee's badge.
     * @param punchType The type of punch (e.g., CLOCK_IN, CLOCK_OUT, TIME_OUT).
     */
    
    public Punch(int terminalid, Badge badge, EventType punchType) {
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchType = punchType;
        this.originalTimestamp = LocalDateTime.now();
    }
    
    /**
     * Constructs a Punch object based on existing data in the database.
     * 
     * @param id Identifier for this punch.
     * @param terminalid Identifier for the terminal where the punch occurred.
     * @param badge Badge object representing the employee's badge.
     * @param originalTimestamp Time when the punch occurred.
     * @param punchType The type of punch (e.g., CLOCK_IN, CLOCK_OUT, TIME_OUT).
     */
    
    public Punch(int id, int terminalid, Badge badge, LocalDateTime originalTimestamp, EventType punchType) {
        this.id = id;
        this.terminalid = terminalid;
        this.badge = badge;
        this.originalTimestamp = originalTimestamp;
        this.punchType = punchType;
    }
    
    /**
     * Retrieves the identifier for this punch.
     * 
     * @return Identifier for this punch as an integer.
     */
    
    public int getId() {
        return id;
    }
    
    /**
     * Gets the identifier for the terminal used in this punch event.
     * 
     * @return The terminal identifier as an integer.
     */
    
    public int getTerminalid() {
        return terminalid;
    }
    
    /**
     * Gets the badge associated with the employee making this punch.
     * 
     * @return A Badge object containing the employee's badge details.
     */
    
    public Badge getBadge() {
        return badge;
    }
    
    /**
     * Gets the original timestamp for this punch event.
     * 
     * @return A LocalDateTime object representing the original timestamp.
     */
    
    public LocalDateTime getOriginaltimestamp() {
        return originalTimestamp;
    }
    
    /**
     * Gets the adjusted timestamp for this punch event. This can be used 
     * for corrections or adjustments to the original timestamp.
     * 
     * @return A LocalDateTime object representing the adjusted timestamp.
     */
    
    public LocalDateTime getAdjustedtimestamp() {
        return adjustedTimestamp;
    }
    
    /**
     * Gets the type of this punch event.
     * 
     * @return An EventType representing the punch type (CLOCK_IN, CLOCK_OUT, TIME_OUT, etc.).
     */
    
    public EventType getPunchtype() {
        return punchType;
    }
    
    public PunchAdjustmentType getAdjustmentType() {
        return adjustmenttype;
    }
    
    /**
     * Adjusts an Employees punch time either forward or backward
     * @param s The Shift that the Employee is punching in on
     */
    
    public void adjust(Shift s) {
        LocalDateTime ots = originalTimestamp;
        LocalTime shiftStartTime = s.getShiftStart();
        LocalTime shiftStopTime = s.getShiftStop();
        LocalTime lunchStartTime = s.getLunchStart();
        LocalTime lunchStopTime = s.getLunchStop();
        int roundInterval = s.getRoundInterval();
        int gracePeriod = s.getGracePeriod();
        int dockPenalty = s.getDockPenalty();
        int minutesOver = ots.getMinute() % roundInterval;
        
        boolean isWeekday = (ots.getDayOfWeek() != DayOfWeek.SATURDAY && ots.getDayOfWeek() != DayOfWeek.SUNDAY);
        boolean isNotTimeout = punchType != EventType.TIME_OUT;
        
        LocalDateTime shiftStart = ots.with(shiftStartTime);
        LocalDateTime shiftStartGraceBefore = shiftStart.minusMinutes(gracePeriod);
        LocalDateTime shiftStartGraceAfter = shiftStart.plusMinutes(gracePeriod);
        LocalDateTime shiftStop = ots.with(shiftStopTime);
        LocalDateTime shiftStopGrace = shiftStop.minusMinutes(gracePeriod);
        
        LocalDateTime shiftStopInterval = shiftStop.plusMinutes(roundInterval);
        
        LocalDateTime lunchStart = ots.with(lunchStartTime);
        LocalDateTime lunchStop = ots.with(lunchStopTime);
        
        LocalDateTime shiftStartDock = shiftStart.withMinute(shiftStart.getMinute()).plusMinutes(dockPenalty);
        LocalDateTime shiftStopDock = shiftStop.withMinute(shiftStop.getMinute()).minusMinutes(dockPenalty);
        
        // Check if the punch is a time out punch and make sure it was made on a week day
        if (isNotTimeout && isWeekday) {
            // Is the clock in punch made within the grace period before the start of the shift
            if (ots.isAfter(shiftStartGraceBefore) && ots.isBefore(shiftStart)) {
                if (punchType == EventType.CLOCK_IN) {
                    setAdjustedTimestamp(shiftStart, PunchAdjustmentType.SHIFT_START);
                }
            // Is the punch made within the interval after the end of the shift
            } else if (ots.isBefore(shiftStopInterval) && ots.isAfter(shiftStop)) {
                setAdjustedTimestamp(shiftStop, PunchAdjustmentType.SHIFT_STOP);
            // Is the punch made before the end of the shift and after the grace period before the shift end
            } else if (ots.isBefore(shiftStop) && ots.isAfter(shiftStopGrace)) {
                setAdjustedTimestamp(shiftStop, PunchAdjustmentType.SHIFT_STOP);
            }
            // Is the punch made after the start of lunch and before the end of lunch
            else if (ots.isAfter(lunchStart) && ots.isBefore(lunchStop)) {
                if (punchType == EventType.CLOCK_OUT) {
                    setAdjustedTimestamp(lunchStart, PunchAdjustmentType.LUNCH_START);
                } else {
                    setAdjustedTimestamp(lunchStop, PunchAdjustmentType.LUNCH_STOP);
                }
            // Is the punch made within the dock penalty interval after the grace period at shift start
            } else if (ots.isAfter(shiftStartGraceAfter) && ots.isBefore(shiftStartGraceAfter.plusMinutes(dockPenalty))) {
                setAdjustedTimestamp(shiftStartDock, PunchAdjustmentType.SHIFT_DOCK);
            // Is the punch made within the dock penalty interval before the grace period at shift end
            } else if (ots.isBefore(shiftStopGrace) && ots.isAfter(shiftStopGrace.minusMinutes(dockPenalty))) {
                setAdjustedTimestamp(shiftStopDock, PunchAdjustmentType.SHIFT_DOCK);
            // Is the punch made outside of the dock and grace periods for both shift start and end
            } else if (ots.isAfter(shiftStartGraceAfter) || ots.isBefore(shiftStopGrace)) {
                if (punchType == EventType.CLOCK_IN) {
                    setAdjustedTimestamp(shiftStart, PunchAdjustmentType.SHIFT_START);
                } else {
                    if (ots.getMinute() % roundInterval == 0) {
                        setAdjustedTimestamp(ots.with(shiftStop.withHour(ots.getHour())), PunchAdjustmentType.NONE);

                    } else {
                        round(roundInterval, minutesOver);
                    }
                }
            }
            // Is the punch made on a weekend or is a time-out punch
        } else {
            round(roundInterval, minutesOver);
        }
    }

    private void round(int roundInterval, int minutesOver) {
        int minutesToNext = roundInterval - minutesOver;
        
        if (minutesOver < roundInterval / 2) {
            setAdjustedTimestamp(originalTimestamp.minusMinutes(minutesOver), PunchAdjustmentType.INTERVAL_ROUND);
        } else {
            setAdjustedTimestamp(originalTimestamp.plusMinutes(minutesToNext), PunchAdjustmentType.INTERVAL_ROUND);
        }
    }

    
    /**
     * Creates a formatted string to represent this punch event.
     * 
     * @return A string formatted as "#{badgeId} {punchType}: {Day MM/dd/yyyy} {HH:mm:ss}".
     */
         
    public String printOriginal() {
        StringBuilder sb = new StringBuilder();
        sb.append('#').append(getBadge().getId()).append(' ');
        sb.append(getPunchtype()).append(": ");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        
        sb.append(originalTimestamp.format(formatter).toUpperCase());
        
        return sb.toString();
    }
    
    public String printAdjusted() {
        StringBuilder sb = new StringBuilder();
        sb.append('#').append(getBadge().getId()).append(' ');
        sb.append(getPunchtype()).append(": ");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        
        sb.append(adjustedTimestamp.format(formatter).toUpperCase()).append(" ");
        sb.append("(").append(adjustmenttype).append(")");
        
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return printOriginal();
    }
    
    // Helper function to simplify setting adjustedTimestamp and adjustmenttype
    private void setAdjustedTimestamp(LocalDateTime time, PunchAdjustmentType type) {
        adjustedTimestamp = time.withSecond(0).withNano(0);
        adjustmenttype = type;
    }
}
