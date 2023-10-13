package edu.jsu.mcis.cs310.tas_fa23.dao;

import edu.jsu.mcis.cs310.tas_fa23.Badge;
import edu.jsu.mcis.cs310.tas_fa23.Department;
import edu.jsu.mcis.cs310.tas_fa23.Employee;
import edu.jsu.mcis.cs310.tas_fa23.EmployeeType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EmployeeDAO {

    private static final String QUERY_FIND_ID = "SELECT * FROM employee WHERE id = ?";
    private static final String QUERY_FIND_BADGE = "SELECT * FROM employee WHERE badgeid = ? ";

    private final DAOFactory daoFactory;

    EmployeeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Employee find(int id) {

        Employee employee = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND_ID);
                ps.setInt(1, id);

                rs = ps.executeQuery();

                if (rs.next()) {
                    employee = createFromQuery(rs);
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

        return employee;
    }

    public Employee find(Badge badge) {

        Employee employee = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND_BADGE);
                ps.setString(1, badge.getId());

                rs = ps.executeQuery();

                if (rs.next()) {
                    employee = createFromQuery(rs);
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

        return employee;
    }

    private Employee createFromQuery(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String firstname = rs.getString("firstname");
        String middlename = rs.getString("middlename");
        String lastname = rs.getString("lastname");

        LocalDateTime active = rs.getTimestamp("active").toLocalDateTime();

        String badgeid = rs.getString("badgeid");
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        Badge badge = badgeDAO.find(badgeid);
        
        int departmentid = rs.getInt("departmentid");
        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();
        Department department = departmentDAO.find(departmentid);
        
        int shiftid = rs.getInt("shiftid");
//        ShiftDAO shiftDAO = daoFactory.getShiftDAO();
//        Shift shift = shiftDAO.find(shiftid);

        int employeetypeid = rs.getInt("employeetypeid");
        EmployeeType employeetype = employeetypeid == 0 ? EmployeeType.PART_TIME : EmployeeType.FULL_TIME;

        return new Employee(id, firstname, middlename, lastname, active, badge, department, employeetype);
    }
}
