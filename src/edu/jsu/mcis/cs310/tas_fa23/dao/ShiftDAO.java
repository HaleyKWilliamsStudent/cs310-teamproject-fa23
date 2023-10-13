package edu.jsu.mcis.cs310.tas_fa23.dao;

import edu.jsu.mcis.cs310.tas_fa23.Shift;
import java.sql.*;
import java.time.LocalTime;
import java.util.HashMap;
import edu.jsu.mcis.cs310.tas_fa23.Badge;

/**
 *
 * @author Grant
 */
public class ShiftDAO {
    private static final String QUERY_FIND = "SELECT * FROM shift WHERE id = ?";
    private static final String QUERY_BADGE = "SELECT shiftid FROM employee WHERE badgeid = ?";
    
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
        Shift shift = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        
                        String description = rs.getString("description");
                        LocalTime shiftstart = rs.getTime("shiftstart").toLocalTime();
                        LocalTime shiftstop = rs.getTime("shiftstop").toLocalTime();
                        Integer roundinterval = rs.getInt("roundinterval");
                        Integer graceperiod = rs.getInt("graceperiod");
                        Integer dockpenalty = rs.getInt("dockpenalty");
                        LocalTime lunchstart = rs.getTime("lunchstart").toLocalTime();
                        LocalTime lunchstop = rs.getTime("lunchstop").toLocalTime();
                        Integer lunchthreshold = rs.getInt("lunchthreshold");
                        
                        HashMap<String, Object> shiftMap = new HashMap<>();
                        
                        shiftMap.put("id", id);
                        shiftMap.put("description", description);
                        shiftMap.put("shiftstart", shiftstart);
                        shiftMap.put("shiftstop", shiftstop);
                        shiftMap.put("roundinterval", roundinterval);
                        shiftMap.put("graceperiod", graceperiod);
                        shiftMap.put("dockpenalty", dockpenalty);
                        shiftMap.put("lunchstart", lunchstart);
                        shiftMap.put("lunchstop", lunchstop);
                        shiftMap.put("lunchthreshold", lunchthreshold);
                        
                        shift = new Shift(shiftMap);

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
