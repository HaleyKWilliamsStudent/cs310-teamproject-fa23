package edu.jsu.mcis.cs310.tas_fa23.dao;

import edu.jsu.mcis.cs310.tas_fa23.Badge;
import edu.jsu.mcis.cs310.tas_fa23.EventType;
import edu.jsu.mcis.cs310.tas_fa23.Punch;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PunchDAO {

    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
    private static final String QUERY_LIST = "SELECT * FROM event WHERE badgeid = ? AND DATE(timestamp) = ?";

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

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);

                rs = ps.executeQuery();

                if (rs.next()) {

                    int terminalId = rs.getInt("terminalid");
                    Badge badge = badgeDAO.find(rs.getString("badgeid"));
                    LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();
                    int eventTypeId = rs.getInt("eventtypeid");

                    EventType punchType = determinePunchType(eventTypeId);

                    punch = new Punch(id, terminalId, badge, timestamp, punchType);

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

        Punch punch = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_LIST);
                ps.setString(1, badge.getId());

                ps.setTimestamp(2, Timestamp.valueOf(date.atStartOfDay()));

                rs = ps.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    int terminalId = rs.getInt("terminalid");
                    LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();
                    int eventTypeId = rs.getInt("eventtypeid");

                    EventType punchType = determinePunchType(eventTypeId);

                    punch = new Punch(id, terminalId, badge, timestamp, punchType);

                    punches.add(punch);
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

    private EventType determinePunchType(int id) {

        EventType punchType = null;

        switch (id) {
            case 0 -> {
                punchType = EventType.CLOCK_OUT;
            }
            case 1 -> {
                punchType = EventType.CLOCK_IN;
            }
            case 2 -> {
                punchType = EventType.TIME_OUT;
            }
            default -> {
                throw new IllegalStateException("Invalid Id");
            }
        }

        return punchType;

    }

}
