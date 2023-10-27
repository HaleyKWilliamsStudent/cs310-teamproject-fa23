package edu.jsu.mcis.cs310.tas_fa23.dao;

import java.time.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_fa23.Punch;
import edu.jsu.mcis.cs310.tas_fa23.Shift;
import edu.jsu.mcis.cs310.tas_fa23.EventType;

/**
 *
 * Utility class for DAOs. This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 *
 */
public final class DAOUtility {

    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist){
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();
                
        for (Punch punch : dailypunchlist) {
            
            HashMap<String, String> punchData = new HashMap<>();
                        
            punchData.put("id", String.valueOf(punch.getId()));
        
            punchData.put("terminalid", String.valueOf(punch.getTerminalid()));
        
            punchData.put("badgeid", punch.getBadge().getId());
        
            punchData.put("punchtype", punch.getPunchtype().toString());
        
            punchData.put("adjustmenttype", punch.getAdjustmentType().toString());
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
            
            String adjustedtimestamp = punch.getAdjustedtimestamp().format(formatter).toUpperCase();
            
            punchData.put("adjustedtimestamp", adjustedtimestamp);
        
            String originaltimestamp = punch.getOriginaltimestamp().format(formatter).toUpperCase();
            
            punchData.put("originaltimestamp", originaltimestamp);
            
            jsonData.add(punchData);
        }
                 
        String json = Jsoner.serialize (jsonData);
        
        return json;
    }
    
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {
        int totalMinutes = 0;
        
        LocalDateTime clockin;
        LocalDateTime clockout;
        
        // Create pairs of clock in and clock out punches
        for(int i = 1; i < dailypunchlist.size(); i+=2) {
            if(dailypunchlist.get(i).getPunchtype() == EventType.CLOCK_OUT) {
                clockin = dailypunchlist.get(i-1).getAdjustedtimestamp();
                clockout = dailypunchlist.get(i).getAdjustedtimestamp();
                totalMinutes += ChronoUnit.MINUTES.between(clockin, clockout);
            }
        }
        
        if(dailypunchlist.size() < 3 && totalMinutes > shift.getLunchThreshold()) {
            totalMinutes -= shift.getLunchDuration();
        }
  
        return totalMinutes;
    }
}
