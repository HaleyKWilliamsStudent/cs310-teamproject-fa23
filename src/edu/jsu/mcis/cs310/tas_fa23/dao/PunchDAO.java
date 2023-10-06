package edu.jsu.mcis.cs310.tas_fa23.dao;

import edu.jsu.mcis.cs310.tas_fa23.Punch;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PunchDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
    
    private final DAOFactory daoFactory;
    
    PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public Punch find(int id) {

        Punch punch = null;

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

                        int terminalId = rs.getInt("terminalid");
                        String badgeId = rs.getString("badgeid");
                        Timestamp timestamp = rs.getTimestamp("timestamp");
                        int eventTypeId = rs.getInt("eventtypeid");
                        
                        punch = new Punch(id, terminalId, badgeId, timestamp, eventTypeId);

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
