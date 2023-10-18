package edu.jsu.mcis.cs310.tas_fa23.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;

/**
 *
 * Utility class for DAOs. This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 *
 */
public final class DAOUtility {
public static String getPunchListAsJSON(ArrayList<Punch> punchList) {

        String dateFormat = "E MM/dd/yyyy HH:mm:ss";

        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();

        for (int i = 0; i < punchList.size(); i++) {
            HashMap<String, String> punchData = new HashMap<>();
            Punch punch = punchList.get(i);

            punchData.put("badgeid", punch.getBadge().getId());
            punchData.put("terminalid", String.valueOf(punch.getTerminalId()));
            punchData.put("id", String.valueOf(punch.getId()));
            punchData.put("punchtype", punch.getPunchType().toString());
            punchData.put("adjustmenttype", punch.getAdjustmentType().toString());

            String date = DateTimeFormatter.ofPattern(dateFormat).format(punch.getOriginalTimestamp()).toUpperCase();
            punchData.put("originaltimestamp", date);

            String adjustedTime = DateTimeFormatter.ofPattern(dateFormat).format(punch.getAdjustedTimestamp()).toUpperCase();
            punchData.put("adjustedtimestamp", adjustedTime);

            jsonData.add(punchData);
        }
        return Jsoner.serialize(jsonData);
    }
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {
        int totalMinutes = 0;
        // ** UTILITY METHOD WILL GO HERE *** - William H.
        return totalMinutes;
    }
}
