package edu.jsu.mcis.cs310.tas_fa23;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Punch {
    private Integer id = null;
    private final Integer terminalid;
    private final Badge badge;
    private LocalDateTime originalTimestamp;
    private final EventType punchType;
    
    public Punch(int terminalid, Badge badge, EventType punchType) {
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchType = punchType;
        
        this.originalTimestamp = LocalDateTime.now();
    }
    
    public Punch(int id, int terminalid, Badge badge, LocalDateTime originalTimestamp, EventType punchType) {
        this.id = id;
        this.terminalid = terminalid;
        this.badge = badge;
        this.originalTimestamp = originalTimestamp;
        this.punchType = punchType;
    }
    
    public int getId() {
        return id;
    }
    
    public int getTerminalid() {
        return terminalid;
    }
    
    public Badge getBadge() {
        return badge;
    }
    
    public LocalDateTime getOriginaltimestamp() {
        return originalTimestamp;
    }
    
    public EventType getPunchtype() {
        return punchType;
    }
         
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
