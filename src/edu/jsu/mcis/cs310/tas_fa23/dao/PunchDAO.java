package edu.jsu.mcis.cs310.tas_fa23.dao;

import edu.jsu.mcis.cs310.tas_fa23.Badge;
import edu.jsu.mcis.cs310.tas_fa23.EventType;
import edu.jsu.mcis.cs310.tas_fa23.Punch;
import edu.jsu.mcis.cs310.tas_fa23.Employee;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PunchDAO {

    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
    private static final String QUERY_LIST = "SELECT * FROM event WHERE badgeid = ? AND DATE(timestamp) = ? ORDER BY timestamp ASC";
    private static final String QUERY_CREATE = "INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?, ?, ?, ?)";

    private final DAOFactory daoFactory;

    PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Searches the "event" table in the database using the id to find a punch
     *
     * @param id The id of the punch
     * @return A Punch Object for the given id
     */
    public Punch find(int id) {

        Punch punch = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);

                rs = ps.executeQuery();

                if (rs.next()) {

                    punch = createFromQuery(rs);

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

        return punch;

    }

    /**
     * Retrieves all punches made by a specific employee on a given date from
     * the database.
     *
     * @param badge The Badge object representing the employee whose punches are
     * to be retrieved.
     * @param date The LocalDate representing the day for which punches are to
     * be retrieved.
     * @return A list of Punch objects representing all punches made by the
     * employee on the given date.
     */
    public ArrayList<Punch> list(Badge badge, LocalDate date) {
        ArrayList<Punch> punches = new ArrayList<>();

        LocalDateTime dayStart = date.atStartOfDay();

        Punch punch = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_LIST);
                ps.setString(1, badge.getId());
                ps.setTimestamp(2, Timestamp.valueOf(dayStart));

                rs = ps.executeQuery();

                while (rs.next()) {

                    punch = createFromQuery(rs);

                    punches.add(punch);
                }

                if (!punches.isEmpty()) {
                    Punch lastPunch = punches.get(punches.size() - 1);

                    if (lastPunch.getPunchtype() == EventType.CLOCK_IN) {
                        ps.setString(1, badge.getId());
                        ps.setTimestamp(2, Timestamp.valueOf(dayStart.plusDays(1)));

                        rs = ps.executeQuery();

                        if (rs.next()) {
                            Punch nextPunch = createFromQuery(rs);

                            if (nextPunch.getPunchtype() == EventType.CLOCK_OUT || nextPunch.getPunchtype() == EventType.TIME_OUT) {

                                punches.add(nextPunch);
                            }
                        }
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

        return punches;
    }
    
    public ArrayList<Punch> list(Badge badge, LocalDate begin, LocalDate end) {
        ArrayList<Punch> punches = new ArrayList<>();
        Map<Integer, Punch> addedPunches = new HashMap<>();

        for (LocalDate date = begin; !date.isAfter(end); date = date.plusDays(1)) {
            ArrayList<Punch> dailyPunches = list(badge, date);

            if (!dailyPunches.isEmpty()) {
                for (Punch punch : dailyPunches) {
                    if (!addedPunches.containsKey(punch.getId())) {
                        punches.add(punch);
                        addedPunches.put(punch.getId(), punch);
                    }
                }
            }
        }

        return punches;
    }

    
    public int create(Punch punch) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int result = 0;
        
        EmployeeDAO employeeDao = daoFactory.getEmployeeDAO();
        
        Badge badge = punch.getBadge();
        Employee employee = employeeDao.find(badge);
        
        int terminal = employee.getDepartment().getTerminalid();
        
        if(punch.getTerminalid() != terminal) {
            return result;
        }

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_CREATE, Statement.RETURN_GENERATED_KEYS);
                
                ps.setInt(1, employee.getDepartment().getTerminalid());
                ps.setString(2, punch.getBadge().getId());
                ps.setObject(3, punch.getOriginaltimestamp());
                ps.setInt(4, punch.getPunchtype().ordinal());
                
                int updateCount = ps.executeUpdate();
                
                if(updateCount > 0) {
                    
                    rs = ps.getGeneratedKeys();
                    
                    if(rs.next()) {
                        result = rs.getInt(1);        
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

        return result;

    }

    private Punch createFromQuery(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int terminalId = rs.getInt("terminalid");

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        Badge badge = badgeDAO.find(rs.getString("badgeid"));

        LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();
        int eventTypeId = rs.getInt("eventtypeid");
        EventType punchType = EventType.values()[eventTypeId];

        return new Punch(id, terminalId, badge, timestamp, punchType);
    }
}
