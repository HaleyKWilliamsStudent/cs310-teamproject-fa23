package edu.jsu.mcis.cs310.tas_fa23.dao;

import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_fa23.DailySchedule;
import edu.jsu.mcis.cs310.tas_fa23.EventType;
import edu.jsu.mcis.cs310.tas_fa23.Punch;
import edu.jsu.mcis.cs310.tas_fa23.Shift;
import edu.jsu.mcis.cs310.tas_fa23.PunchAdjustmentType;
import java.math.BigDecimal;
import java.math.RoundingMode;

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

    public static int calculateTotalMinutes(ArrayList<Punch> punchList, Shift shift) {
        int totalMinutes = 0;
        int dailyMinutes = 0;

        LocalDate currentDay = null;
        Punch clockIn = null;

        boolean clockedOut = false;
        DailySchedule schedule = null;

        for (Punch punch : punchList) {
            DayOfWeek day = punch.getAdjustedtimestamp().getDayOfWeek();
            schedule = shift.getDailySchedule(day);

            if (!punch.getAdjustedtimestamp().toLocalDate().equals(currentDay)) {
                totalMinutes += dailyMinutes;
                currentDay = punch.getOriginaltimestamp().toLocalDate();
                dailyMinutes = 0;
                clockIn = null;
                clockedOut = false;
            }

            switch (punch.getPunchtype()) {
                case CLOCK_IN:
                    clockIn = punch;
                    break;
                case CLOCK_OUT:
                    if (clockIn != null) {
                        int minutesWorked = (int) ChronoUnit.MINUTES.between(clockIn.getAdjustedtimestamp(), punch.getAdjustedtimestamp());
                        if (minutesWorked > schedule.getLunchThreshold() && !clockedOut) {
                            minutesWorked -= schedule.getLunchDuration();
                        }
                        dailyMinutes += minutesWorked;
                        
                        if (punch.getAdjustmentType() == PunchAdjustmentType.LUNCH_START) {
                            clockedOut = true;
                        }
                    }
                    clockIn = null;
                    break;
                case TIME_OUT:
                    clockIn = null;
                    break;
            }
        }

        totalMinutes += dailyMinutes;

        return totalMinutes;
    }

    public static BigDecimal calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s) {
        BigDecimal minutesWorked = BigDecimal.valueOf(calculateTotalMinutes(punchlist, s));

        System.err.println("Minutes Worked: " + minutesWorked);

        BigDecimal scheduledMinutes = BigDecimal.valueOf(s.getScheduledMinutes());

        System.err.println("Scheduled Minutes: " + scheduledMinutes);

        BigDecimal percentage = minutesWorked.divide(scheduledMinutes, 5, RoundingMode.HALF_UP);

        return new BigDecimal("1").subtract(percentage).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
    }

    public static String getPunchListPlusTotalsAsJSON(ArrayList<Punch> punchlist, Shift shift) {
        HashMap<String, Object> punchesAndTotals = new HashMap<>();

        punchesAndTotals.put("absenteeism", calculateAbsenteeism(punchlist, shift));
        punchesAndTotals.put("totalminutes", calculateTotalMinutes(punchlist, shift));
        punchesAndTotals.put("punchlist", getPunchListAsJSON(punchlist));

        String json = Jsoner.serialize(punchesAndTotals);

        return json;
    }

}
