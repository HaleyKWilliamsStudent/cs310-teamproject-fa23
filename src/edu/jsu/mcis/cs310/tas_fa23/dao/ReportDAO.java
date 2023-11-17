package edu.jsu.mcis.cs310.tas_fa23.dao;

import com.github.cliftonlabs.json_simple.Jsoner;
import edu.jsu.mcis.cs310.tas_fa23.Badge;
import edu.jsu.mcis.cs310.tas_fa23.Department;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportDAO {

    private static final String QUERY_EMPLOYEE = "SELECT * FROM employee ORDER BY lastname, firstname";
    private static final String QUERY_EMPLOYEE_DEPARTMENT = "SELECT * FROM employee WHERE departmentid = ? ORDER BY lastname, firstname";
    private static final String QUERY_EMPLOYEE_TYPE = "SELECT * FROM employeetype where id = ?";

    private final DAOFactory daoFactory;

    ReportDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public String getBadgeSummary(Integer departmentId) {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

        ArrayList<HashMap<String, String>> employees = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                if (departmentId == null) {
                    ps = conn.prepareStatement(QUERY_EMPLOYEE);
                } else {
                    ps = conn.prepareStatement(QUERY_EMPLOYEE_DEPARTMENT);
                    ps.setInt(1, departmentId);
                }

                rs = ps.executeQuery();

                while (rs.next()) {
                    HashMap<String, String> badgeData = new HashMap<>();

                    String badgeid = rs.getString("badgeid");
                    int departmentid = rs.getInt("departmentid");
                    int employeetypeid = rs.getInt("employeetypeid");

                    Badge badge = badgeDAO.find(badgeid);
                    Department department = departmentDAO.find(departmentid);

                    badgeData.put("badgeid", badgeid);
                    badgeData.put("name", badge.getDescription());
                    badgeData.put("department", department.getDescription());

                    ps = conn.prepareStatement(QUERY_EMPLOYEE_TYPE);
                    ps.setInt(1, employeetypeid);

                    ResultSet rs2 = ps.executeQuery();

                    if (rs2.next()) {
                        badgeData.put("type", rs2.getString("description"));

                    }

                    employees.add(badgeData);

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

        return Jsoner.serialize(employees);

    }
}
