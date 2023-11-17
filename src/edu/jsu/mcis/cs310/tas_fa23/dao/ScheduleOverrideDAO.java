package edu.jsu.mcis.cs310.tas_fa23.dao;

import edu.jsu.mcis.cs310.tas_fa23.ScheduleOverride;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;

public class ScheduleOverrideDAO {

    private static final String QUERY_RECURRING = "SELECT * FROM scheduleoverride WHERE start <= ? AND end IS NULL AND badgeid IS NULL";
    private static final String QUERY_RECURRING_EMPLOYEE = "SELECT * FROM scheduleoverride WHERE start <= ? AND end IS NULL AND badgeid = ?";
    private static final String QUERY_TEMPORARY = "SELECT * FROM scheduleoverride WHERE ? >= start AND ? <= end AND end IS NOT NULL AND badgeid IS NULL";
    private static final String QUERY_TEMPORARY_EMPLOYEE = "SELECT * FROM scheduleoverride WHERE ? >= start AND ? <= end AND end IS NOT NULL AND badgeid = ?";

    private final DAOFactory daoFactory;

    ScheduleOverrideDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;

    }

    public ScheduleOverride findRecurring(LocalDate start) {
        ScheduleOverride scheduleOverride = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_RECURRING);
                ps.setDate(1, Date.valueOf(start));

                rs = ps.executeQuery();

                if (rs.next()) {
                    scheduleOverride = createOverride(rs);
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

        return scheduleOverride;

    }

    public ScheduleOverride findRecurringEmployee(LocalDate start, String badgeid) {
        ScheduleOverride scheduleOverride = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_RECURRING_EMPLOYEE);
                ps.setDate(1, Date.valueOf(start));
                ps.setString(2, badgeid);

                rs = ps.executeQuery();

                if (rs.next()) {
                    scheduleOverride = createOverride(rs);
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

        return scheduleOverride;

    }

    public ScheduleOverride findTemporary(LocalDate date) {
        ScheduleOverride scheduleOverride = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_TEMPORARY);
                ps.setDate(1, Date.valueOf(date));
                ps.setDate(2, Date.valueOf(date));

                rs = ps.executeQuery();

                if (rs.next()) {
                    scheduleOverride = createOverride(rs);
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

        return scheduleOverride;

    }

    public ScheduleOverride findTemporaryEmployee(LocalDate date, String badgeid) {
        ScheduleOverride scheduleOverride = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_TEMPORARY_EMPLOYEE);
                ps.setDate(1, Date.valueOf(date));
                ps.setDate(2, Date.valueOf(date));
                ps.setString(3, badgeid);

                rs = ps.executeQuery();

                if (rs.next()) {
                    scheduleOverride = createOverride(rs);
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

        return scheduleOverride;

    }

    private ScheduleOverride createOverride(ResultSet rs) throws SQLException {
        LocalDate start = rs.getDate("start").toLocalDate();
        Date endDate = rs.getDate("end");
        LocalDate end = (endDate != null) ? endDate.toLocalDate() : null;
        int day = rs.getInt("day");
        String badgeId = rs.getString("badgeid");
        int dailyScheduleId = rs.getInt("dailyscheduleid");
        return new ScheduleOverride(start, end, badgeId, day, dailyScheduleId);
    }

}
