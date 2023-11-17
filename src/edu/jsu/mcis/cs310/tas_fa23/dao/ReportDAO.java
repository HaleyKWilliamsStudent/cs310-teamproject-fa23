package edu.jsu.mcis.cs310.tas_fa23.dao;

import com.github.cliftonlabs.json_simple.Jsoner;
import edu.jsu.mcis.cs310.tas_fa23.Badge;
import edu.jsu.mcis.cs310.tas_fa23.Department;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportDAO {

    private static final String QUERY_EMPLOYEE = "SELECT e.badgeid, e.departmentid, et.description as employeetype FROM employee e JOIN employeetype et ON e.employeetypeid = et.id ORDER BY e.lastname, e.firstname";
    private static final String QUERY_EMPLOYEE_DEPARTMENT = "SELECT e.badgeid, e.departmentid, et.description as employeetype FROM employee e JOIN employeetype et ON e.employeetypeid = et.id WHERE e.departmentid = ? ORDER BY e.lastname, e.firstname";
    private static final String QUERY_IN_OUT = "SELECT e.*, p.timestamp as timestamp, s.description as shift, et.description as employeetype from employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id JOIN event p ON e.badgeid = p.badgeid";

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
                    String employeetype = rs.getString("employeetype");

                    Badge badge = badgeDAO.find(badgeid);
                    Department department = departmentDAO.find(departmentid);

                    badgeData.put("badgeid", badgeid);
                    badgeData.put("name", badge.getDescription());
                    badgeData.put("department", department.getDescription());
                    badgeData.put("type", employeetype);

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

    public String getWhosInWhosOut(LocalDateTime ts, Integer departmentId) {

        ArrayList<HashMap<String, String>> employees = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_IN_OUT);
//                ps.setTimestamp(1, Timestamp.valueOf(ts));
//                if (departmentId != null) {
//                    ps.setInt(2, departmentId);
//                    ps.setInt(3, departmentId);
//                } else {
//                    ps.setObject(2, null);
//                    ps.setObject(3, null);
//                }

                rs = ps.executeQuery();

                while (rs.next()) {
                    HashMap<String, String> inOutData = new HashMap<>();

                    String badgeid = rs.getString("badgeid");
                    String firstname = rs.getString("firstname");
                    String lastname = rs.getString("lastname");
                    String shift = rs.getString("shift");
//                    String status = rs.getString("status");

                    Timestamp arrived = rs.getTimestamp("timestamp");
                    if (arrived != null) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
                        inOutData.put("arrived", arrived.toLocalDateTime().format(formatter).toUpperCase());

                    }

                    inOutData.put("employeetype", rs.getString("employeetype"));

                    inOutData.put("firstname", firstname);
                    inOutData.put("badgeid", badgeid);
                    inOutData.put("shift", shift);
                    inOutData.put("lastname", lastname);
//                    inOutData.put("status", status);

                    employees.add(inOutData);
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
