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
 * Utility class for DAOs. This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 *
 */
public final class DAOUtility {

    private static Object jsonData;

    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist) {
        ArrayList<HashMap<String, String>> jsonData;

        /* HashMap */
        HashMap<String, String> punchData = new HashMap<>();

        /* Punch Data */
        punchData.put("id", String.valueof(punch.getId()));

        punchData.put("terminalid", String.valueof(punch.getTerminalid()));

        punchData.put("badgeid", String.valueof(punch.getBadgeid()));

        punchData.put("punchtype", punchType.toString());

        punchData.put("adjustmenttype", adjustmenttype.toString());

        punchData.put("adjustedtimestamp", adjustmentTimestamp.printAdjusted());

        punchData.put("originaltimestamp", originalTimestamp.printOriginal());

        /* Add HashMap to ArrayList */
        jsonData.add(punchData);

        String json = Jsoner.serialize(jsonData);
    }
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {
        int totalMinutes = 0;
        /* *** This is the utility method for the backlog item "Add Utility Method to Calculate Total Minutes" in Canvas.
        i.e., my code goes here. - William H. */
        return int totalMinutes;
    }
}
