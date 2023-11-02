package edu.jsu.mcis.cs310.tas_fa23.dao;

import edu.jsu.mcis.cs310.tas_fa23.Absenteeism;
import edu.jsu.mcis.cs310.tas_fa23.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AbsenteeismDAO {

    private static final String QUERY_FIND = "SELECT * FROM absenteesim WHERE ";
    private static final String QUERY_CREATE = "INSERT INTO absenteeism (employeeid, payperiod, percentage) VALUES (?, ?, ?)";
    private static final String QUERY_UPDATE = "UPDATE absenteeism SET payperiod = ?, percentage = ? WHERE employeeid = ?";

    private final DAOFactory daoFactory;

    AbsenteeismDAO(DAOFactory daofactory) {
        this.daoFactory = daofactory;
    }

    public Absenteeism find(Employee employee, LocalDate payPeriodStart) {
        Absenteeism absenteeism = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    if (rs.next()) {

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
        return absenteeism;
    }

    public void create(Absenteeism absenteeism) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                if (find(absenteeism.getEmployee(), absenteeism.getPayperiod()) != null) {
                    ps = conn.prepareStatement(QUERY_UPDATE);
                    ps.setDate(1, java.sql.Date.valueOf(absenteeism.getPayperiod()));
                    ps.setDouble(2, absenteeism.getPercentage().doubleValue());
                    ps.setInt(3, absenteeism.getEmployee().getId());
                } else {
                    ps = conn.prepareStatement(QUERY_CREATE);
                    ps.setInt(1, absenteeism.getEmployee().getId());
                    ps.setDate(2, java.sql.Date.valueOf(absenteeism.getPayperiod()));
                    ps.setDouble(3, absenteeism.getPercentage().doubleValue());
                }

                ps.executeUpdate();

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

    }

}
