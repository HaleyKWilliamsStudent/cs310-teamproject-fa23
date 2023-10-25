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

    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {
        int totalMinutes = 0;
        int dailyTotal = 0; // total minutes accrued in a given work-day
        boolean punchedOutForLunch = false;
        boolean punchedInForLunch = false;

        LocalDate currentDate = dailypunchlist.get(0).getAdjustedtimestamp().toLocalDate(); // The current date of the punches we are working with
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

        for (int i = 0; i < dailypunchlist.size(); i++) {
            if (dailypunchlist.size() - 1 > i) { // True if there is a punch after this one
                Punch punch = dailypunchlist.get(i);
                Punch nextPunch = dailypunchlist.get(i + 1);

                if (punch.getPunchtype().equals(EventType.CLOCK_IN) && nextPunch.getPunchtype().equals(EventType.CLOCK_OUT)) {
                    if (!currentDate.equals(nextPunch.getAdjustedtimestamp().toLocalDate())) {
                        currentDate = dailypunchlist.get(i).getAdjustedtimestamp().toLocalDate();
                    }
                    // Checks if a given employee clocked OUT for lunch
                    if (!punchedOutForLunch && punch.getAdjustmentType() == PunchAdjustmentType.LUNCH_START) {
                        punchedOutForLunch = true;
                    }
                    // Checks if a given employee clocked IN for lunch
                    if (!punchedInForLunch && punch.getAdjustmentType() == PunchAdjustmentType.LUNCH_STOP) {
                        punchedInForLunch = true;
                    }
                    // Finds the difference in time and adds it to the totalMinutes variable
                    Duration diff = Duration.between(punch.getAdjustedtimestamp(), nextPunch.getAdjustedtimestamp());
                    dailyTotal += diff.toMinutes();
                } else {
                    if (!nextPunch.getPunchtype().equals(EventType.TIME_OUT)) { // The next punch is NOT a TIME_OUT
                        totalMinutes += dailyTotal;
                    }
                    dailyTotal = 0;
                    punchedOutForLunch = false;
                    punchedInForLunch = false;
                }
            }
        }
        return totalMinutes;
    }
}
