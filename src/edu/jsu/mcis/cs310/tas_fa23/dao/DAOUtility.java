package edu.jsu.mcis.cs310.tas_fa23.dao;

import java.time.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_fa23.Punch;

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
            
            System.out.println(punch);
            
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
}