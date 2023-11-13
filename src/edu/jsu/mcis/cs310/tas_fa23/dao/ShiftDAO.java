package edu.jsu.mcis.cs310.tas_fa23.dao;

import edu.jsu.mcis.cs310.tas_fa23.Shift;
import java.sql.*;
import java.time.LocalTime;
import java.util.HashMap;
import edu.jsu.mcis.cs310.tas_fa23.Badge;
import edu.jsu.mcis.cs310.tas_fa23.DailySchedule;

/**
 *
 * @author Grant
 */
public class ShiftDAO {
    private static final String QUERY_FIND_SHIFT = "SELECT * FROM shift WHERE id = ?";
    private static final String QUERY_BADGE = "SELECT shiftid FROM employee WHERE badgeid = ?";
    private static final String QUERY_FIND_DAILY_SCHEDULE = "SELECT * FROM dailyschedule WHERE id = ?";
    
    private final DAOFactory daoFactory;
    
    ShiftDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /**
     * 
     * @param id
     * @return returns a shift using an id as an argument
     */
    public Shift find(int id) {
        DailySchedule dailySchedule = null;
        Shift shift = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        String desc = null;
        int dailyScheduleID = 0;
        
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND_SHIFT);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        
                        desc = rs.getString("description");
                        dailyScheduleID = rs.getInt("dailyscheduleid");

                    }

                }
                
                ps = conn.prepareStatement(QUERY_FIND_DAILY_SCHEDULE);
                ps.setInt(1, dailyScheduleID);
                
                hasresults = ps.execute();
                
                if(hasresults) {
                    
                    rs = ps.getResultSet();
                    
                    while (rs.next()) {
                        
                        LocalTime shiftstart = rs.getTime("shiftstart").toLocalTime();
                        LocalTime shiftstop = rs.getTime("shiftstop").toLocalTime();
                        Integer roundinterval = rs.getInt("roundinterval");
                        Integer graceperiod = rs.getInt("graceperiod");
                        Integer dockpenalty = rs.getInt("dockpenalty");
                        LocalTime lunchstart = rs.getTime("lunchstart").toLocalTime();
                        LocalTime lunchstop = rs.getTime("lunchstop").toLocalTime();
                        Integer lunchthreshold = rs.getInt("lunchthreshold");
                        
                        HashMap<String, Object> dailyScheduleMap = new HashMap<>();
                        
                        dailyScheduleMap.put("shiftstart", shiftstart);
                        dailyScheduleMap.put("shiftstop", shiftstop);
                        dailyScheduleMap.put("roundinterval", roundinterval);
                        dailyScheduleMap.put("graceperiod", graceperiod);
                        dailyScheduleMap.put("dockpenalty", dockpenalty);
                        dailyScheduleMap.put("lunchstart", lunchstart);
                        dailyScheduleMap.put("lunchstop", lunchstop);
                        dailyScheduleMap.put("lunchsthreshold", lunchthreshold);
                        
                        dailySchedule = new DailySchedule(dailyScheduleMap);
                        
                        shift = new Shift(id, desc, dailySchedule);
                    }
                }
            }

        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }

        return shift;
    }
    
    /**
     * 
     * @param badge
     * @return returns a shift using a badge as an argument
     */
    public Shift find(Badge badge) {
        Shift shift = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_BADGE);
                ps.setString(1, badge.getId());

                rs = ps.executeQuery();
                
                if(rs.next()) {
                    int shiftid = rs.getInt("shiftid");
                    shift = find(shiftid);
                }

            }

        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }

        return shift;
    }
}
