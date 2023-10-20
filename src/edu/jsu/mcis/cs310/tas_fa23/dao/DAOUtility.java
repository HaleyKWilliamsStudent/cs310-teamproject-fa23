package edu.jsu.mcis.cs310.tas_fa23.dao;

import java.time.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;

/**
 * 
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 * 
 */
public final class DAOUtility {
    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist){
        ArrayList<HashMap<string, string="">> jsonData;
        
        /* HashMap*/
        HashMap<string, string=""> punchData= new HashMap<>;
        
        HashMap<string, string=""> punchData= new HashMap<>;
        
        HashMap<string, string=""> punchData= new HashMap<>;
        
        HashMap<string, string=""> punchData= new HashMap<>;
        
        HashMap<string, string""> punchData= new HashMap<>;
        
        /* Punch Data*/
        punchData.put("id", String.valueof(punch.getId()));
        
        punchData.put("terminalid", String.valueof(punch.getTerminalid()));
        
        punchData.put("badgeid", String.valueof(punch.getBadgeid()));
        
        punchData.put("punchtype", punchType.toString());
        
        punchData.put("adjustmenttype", adjustmenttype.toString());
        
        punchData.put("adjustedtimestamp", adjustmentTimestamp.printAdjusted());
        
        punchData.put("originaltimestamp", originalTimestamp.printOriginal());
        
        /* Add HashMap to ArrayList*/
        jsonData.add(punchData);
        jsonData.add(punchData);
        jsonData.add(punchData);
        jsonData.add(punchData);
        jsonData.add(punchData);
        jsonData.add(punchData);
        jsonData.add(punchData);
        
        String json = Jsoner.serialize (jsonData);
    }

}