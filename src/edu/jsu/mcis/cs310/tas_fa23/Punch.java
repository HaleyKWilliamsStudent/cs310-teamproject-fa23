package edu.jsu.mcis.cs310.tas_fa23;

import java.time.LocalDateTime;
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
    
    @Override
    public String toString() {
        return printOriginal();
    }
}
