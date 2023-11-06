package edu.jsu.mcis.cs310.tas_fa23.dao;

import edu.jsu.mcis.cs310.tas_fa23.Absenteeism;
import edu.jsu.mcis.cs310.tas_fa23.Employee;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class AbsenteeismDAO {

    private static final String QUERY_FIND = "SELECT * FROM absenteeism WHERE employeeid = ? ";
    private static final String QUERY_CREATE = "INSERT INTO absenteeism (employeeid, payperiod, percentage) VALUES (?, ?, ?)";
    private static final String QUERY_UPDATE = "UPDATE absenteeism SET payperiod = ?, percentage = ? WHERE employeeid = ?";

    private final DAOFactory daoFactory;

    AbsenteeismDAO(DAOFactory daofactory) {
        this.daoFactory = daofactory;
    }

    /**
     * Searches the "absenteeism" table for a certain employee's absenteeism.
     * @param employee
     * @param payperiod
     * @return A Absenteeism object for the given employee.
     */
    
    public Absenteeism find(Employee employee, LocalDate payperiod) {
        Absenteeism absenteeism = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, employee.getId());

                rs = ps.executeQuery();

                if (rs.next()) {

                    double percentage = rs.getDouble("percentage");
                    
                    DecimalFormat df = new DecimalFormat("#0.00");
                    String formattedPercent = df.format(percentage);
                    
                    BigDecimal bigPercentage = new BigDecimal(formattedPercent);

                    absenteeism = new Absenteeism(employee, payperiod, bigPercentage);

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
    
    /**
     * Inserts a new Absenteeism object into the absenteeism table.
     * @param absenteeism 
     */

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
