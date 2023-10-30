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
    private static final String QUERY_CREATE = "";

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
