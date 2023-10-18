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
    
    /**
     * Adjusts an Employees punch time either forward or backward
     * @param s The Shift that the Employee is punching in on
     */
    
    public void adjust(Shift s) {
        LocalDateTime ost = originalTimestamp;
        LocalTime punchTime = ost.toLocalTime();
        LocalTime shiftStartTime = s.getShiftStart();
        LocalTime shiftStopTime = s.getShiftStop();
        LocalTime lunchStartTime = s.getLunchStart();
        LocalTime lunchStopTime = s.getLunchStop();
        int roundInterval = s.getRoundInterval();
        int gracePeriod = s.getGracePeriod();
        int dockPenalty = s.getDockPenalty();
        int minutesOver = punchTime.getMinute() % roundInterval;
        
        boolean isWeekday = (ost.getDayOfWeek() != DayOfWeek.SATURDAY && ost.getDayOfWeek() != DayOfWeek.SUNDAY);
        
        if (punchType != EventType.TIME_OUT && isWeekday) {
            if (punchTime.isAfter(shiftStartTime.minusMinutes(gracePeriod)) && punchTime.isBefore(shiftStartTime)) {
                
                if (punchType == EventType.CLOCK_IN) {
                    setAdjustedTimestamp(ost.with(shiftStartTime), PunchAdjustmentType.SHIFT_START);
                }
            } else if (punchTime.isBefore(shiftStopTime.plusMinutes(roundInterval)) && punchTime.isAfter(shiftStopTime)) {
                setAdjustedTimestamp(ost.with(shiftStopTime), PunchAdjustmentType.SHIFT_STOP);
            } else if (punchTime.isBefore(shiftStopTime) && punchTime.isAfter(shiftStopTime.minusMinutes(gracePeriod))) {
                setAdjustedTimestamp(ost.with(shiftStopTime), PunchAdjustmentType.SHIFT_STOP);
            }
            else if (punchTime.isAfter(lunchStartTime) && punchTime.isBefore(lunchStopTime)) {
                if (punchType == EventType.CLOCK_OUT) {
                    setAdjustedTimestamp(ost.with(lunchStartTime), PunchAdjustmentType.LUNCH_START);
                } else {
                    setAdjustedTimestamp(ost.with(lunchStopTime), PunchAdjustmentType.LUNCH_STOP);

                }
            } else if (punchTime.isAfter(shiftStartTime.plusMinutes(gracePeriod)) && punchTime.isBefore(shiftStartTime.plusMinutes(gracePeriod).plusMinutes(dockPenalty))) {
                setAdjustedTimestamp(ost.with(shiftStartTime.withMinute(shiftStartTime.getMinute() + dockPenalty)), PunchAdjustmentType.SHIFT_DOCK);
            } else if (punchTime.isBefore(shiftStopTime.minusMinutes(gracePeriod)) && punchTime.isAfter(shiftStopTime.minusMinutes(gracePeriod).minusMinutes(dockPenalty))) {
                setAdjustedTimestamp(ost.with(shiftStopTime).withMinute(shiftStopTime.getMinute() - dockPenalty), PunchAdjustmentType.SHIFT_DOCK);
            } else if (punchTime.isAfter(shiftStartTime.plusMinutes(gracePeriod)) || punchTime.isBefore(shiftStopTime.minusMinutes(gracePeriod))) {
                if (punchType == EventType.CLOCK_IN) {
                    setAdjustedTimestamp(ost.with(shiftStartTime), PunchAdjustmentType.SHIFT_START);
                } else {
                    if (punchTime.getMinute() % roundInterval == 0) {
                        setAdjustedTimestamp(ost.with(shiftStopTime.withHour(ost.getHour())), PunchAdjustmentType.NONE);

                    } else {
                        round(roundInterval, minutesOver);
                    }
                }
            }
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
