package edu.jsu.mcis.cs310.tas_fa23.dao;

import com.github.cliftonlabs.json_simple.Jsoner;
import edu.jsu.mcis.cs310.tas_fa23.Badge;
import edu.jsu.mcis.cs310.tas_fa23.Department;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportDAO {

    private static final String QUERY_EMPLOYEE = "SELECT e.badgeid, e.departmentid, et.description as employeetype FROM employee e JOIN employeetype et ON e.employeetypeid = et.id ORDER BY e.lastname, e.firstname";
    private static final String QUERY_EMPLOYEE_DEPARTMENT = "SELECT e.badgeid, e.departmentid, et.description as employeetype FROM employee e JOIN employeetype et ON e.employeetypeid = et.id WHERE e.departmentid = ? ORDER BY e.lastname, e.firstname";
    private static final String QUERY_IN_OUT = "SELECT e.firstname, e.lastname, e.badgeid, et.description AS employeetype, s.description AS shift, MIN(CASE WHEN p.eventtypeid = 1 THEN p.timestamp END) AS arrived, CASE WHEN MIN(CASE WHEN p.eventtypeid = 1 THEN p.timestamp END) <= ? THEN 'In' ELSE 'Out' END AS status FROM employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id LEFT JOIN event p ON e.badgeid = p.badgeid AND DATE(p.timestamp) = ? GROUP BY et.description, e.lastname, e.firstname, e.badgeid, s.description ORDER BY status, employeetype, e.lastname, e.firstname";
    private static final String QUERY_IN_OUT_DEPARTMENT = "SELECT e.firstname, e.lastname, e.badgeid, et.description AS employeetype, s.description AS shift, MIN(CASE WHEN p.eventtypeid = 1 THEN p.timestamp END) AS arrived, CASE WHEN MIN(CASE WHEN p.eventtypeid = 1 THEN p.timestamp END) <= ? THEN 'In' ELSE 'Out' END AS status FROM employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id LEFT JOIN event p ON e.badgeid = p.badgeid AND DATE(p.timestamp) = ? WHERE e.departmentid = ? GROUP BY et.description, e.lastname, e.firstname, e.badgeid, s.description ORDER BY status, employeetype, e.lastname, e.firstname";
    
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
                if (departmentId != null) {
                    ps = conn.prepareStatement(QUERY_IN_OUT_DEPARTMENT);
                    ps.setTimestamp(1, Timestamp.valueOf(ts));
                    ps.setDate(2, Date.valueOf(ts.toLocalDate()));
                    ps.setInt(3, departmentId);
                } else {
                    ps = conn.prepareStatement(QUERY_IN_OUT);
                    ps.setTimestamp(1, Timestamp.valueOf(ts));
                    ps.setDate(2, Date.valueOf(ts.toLocalDate()));
                }
                              
                rs = ps.executeQuery();
                
                while (rs.next()) {
                    HashMap<String, String> inOutData = new HashMap<>();

                    String badgeid = rs.getString("badgeid");
                    String firstname = rs.getString("firstname");
                    String lastname = rs.getString("lastname");
                    String shift = rs.getString("shift");
                    String status = rs.getString("status");

                    Timestamp arrived = rs.getTimestamp("arrived");
                    if (arrived != null && status.equals("In")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
                        inOutData.put("arrived", arrived.toLocalDateTime().format(formatter).toUpperCase());
                    }

                    inOutData.put("employeetype", rs.getString("employeetype"));

                    inOutData.put("firstname", firstname);
                    inOutData.put("badgeid", badgeid);
                    inOutData.put("shift", shift);
                    inOutData.put("lastname", lastname);
                    inOutData.put("status", status);
                    
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
