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
import edu.jsu.mcis.cs310.tas_fa23.PunchAdjustmentType;

/**
 *
 * Utility class for DAOs. This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 *
 */
public final class DAOUtility {

    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist) {
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

        String json = Jsoner.serialize(jsonData);

        return json;
    }
    
    public static int calculateTotalMinutes(ArrayList<Punch> punchlist, Shift shift) {
        int totalMinutes = 0;
        int dayTotal = 0;
        
        LocalDateTime clockin;
        LocalDateTime clockout;
        
        boolean isNewDay;
        boolean clockedOut = false;
        
        for(int i = 1; i < punchlist.size(); i++) {
            if(ChronoUnit.HOURS.between(punchlist.get(i-1).getAdjustedtimestamp(), punchlist.get(i).getAdjustedtimestamp()) > 12) {
                isNewDay = true;
                totalMinutes += dayTotal;
                dayTotal = 0;
            } else {
                isNewDay = false;
            }
            
            if(punchlist.get(i).getPunchtype() == EventType.CLOCK_OUT) {
                clockin = punchlist.get(i-1).getAdjustedtimestamp();
                clockout = punchlist.get(i).getAdjustedtimestamp();
                dayTotal += ChronoUnit.MINUTES.between(clockin, clockout);
                System.out.println("Day total: " + dayTotal);
                if(punchlist.get(i).getAdjustmentType() == PunchAdjustmentType.LUNCH_START) {
                    clockedOut = true;
                }
            } else {
                continue;
            }
            
            if(!isNewDay && dayTotal > shift.getLunchThreshold() && clockedOut) {
                dayTotal -= shift.getLunchDuration();
                System.out.println("subtract 30 = " + dayTotal);
            } 
            
            if(isNewDay) {
                totalMinutes += dayTotal;
                System.out.println("Total v1: " + totalMinutes);
            } else if(i == punchlist.size() - 1) {
                totalMinutes += dayTotal;
                System.out.println("Total v2: " + totalMinutes);
            } 
            clockedOut = false;
        }
        
        return totalMinutes;
    }
}
