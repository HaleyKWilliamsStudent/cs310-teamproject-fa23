package edu.jsu.mcis.cs310.tas_fa23.dao;

import edu.jsu.mcis.cs310.tas_fa23.Badge;
import edu.jsu.mcis.cs310.tas_fa23.EventType;
import edu.jsu.mcis.cs310.tas_fa23.Punch;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PunchDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
    
    private final DAOFactory daoFactory;
    
    PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /**
     * Searches the "event" table in the database using the id to find a punch
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

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    if (rs.next()) {

                        int terminalId = rs.getInt("terminalid");
                        Badge badge = badgeDAO.find(rs.getString("badgeid"));
                        LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();
                        int eventTypeId = rs.getInt("eventtypeid");
                        
                        EventType punchType = null;
                        
                        switch (eventTypeId) {
                            case 0 ->  {
                                punchType = EventType.CLOCK_OUT;
                            }
                            case 1 ->  {
                                punchType = EventType.CLOCK_IN;
                            }
                            case 2 ->  {
                                punchType = EventType.TIME_OUT;
                            }
                            default -> {
                                throw new IllegalStateException("Invalid Id");
                            }
                        }
                        
                        punch = new Punch(id, terminalId, badge, timestamp, punchType);

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

        return punch;

    }
    
}
