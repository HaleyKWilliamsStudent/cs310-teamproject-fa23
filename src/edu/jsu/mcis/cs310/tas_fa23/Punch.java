package edu.jsu.mcis.cs310.tas_fa23;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Punch {
    private final int id;
    private final int terminalId;
    private final String badgeId;
    private final LocalDateTime timestamp;
    private final int eventTypeId;
    
    public Punch(int id, int terminalId, String badgeId, Timestamp timestamp, int eventTypeId) {
        this.id = id;
        this.terminalId = terminalId;
        this.badgeId = badgeId;
        this.timestamp = timestamp.toLocalDateTime();
        this.eventTypeId = eventTypeId;
    }
    
    public int getId() {
        return id;
    }
    
    public int getTerminalId() {
        return terminalId;
    }
    
    public String getBadgeId() {
        return badgeId;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public int getEventTypeId() {
        return eventTypeId;
    }
    
    private EventType getEventType(int id) {
        EventType eventType = null;
        
        switch (eventTypeId) {
            case 0 ->  {
                eventType = EventType.CLOCK_OUT;
            }
            case 1 ->  {
                eventType = EventType.CLOCK_IN;
            }
            case 2 ->  {
                eventType = EventType.TIME_OUT;
            }
        }
        
        return eventType;
    }
    
    public String printOriginal() {
        StringBuilder sb = new StringBuilder();
        sb.append('#').append(badgeId).append(' ');
        sb.append(getEventType(eventTypeId)).append(": ");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        
        sb.append(timestamp.format(formatter).toUpperCase());
        
        return sb.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('#').append(badgeId).append(' ');
        sb.append(getEventType(eventTypeId)).append(": ");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy hh:mm:ss");
        
        sb.append(timestamp.format(formatter).toUpperCase());
        
        return sb.toString();
    }
}
