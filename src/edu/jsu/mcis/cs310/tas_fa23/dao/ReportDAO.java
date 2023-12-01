package edu.jsu.mcis.cs310.tas_fa23.dao;

import com.github.cliftonlabs.json_simple.Jsoner;
import edu.jsu.mcis.cs310.tas_fa23.Badge;
import edu.jsu.mcis.cs310.tas_fa23.Department;
import edu.jsu.mcis.cs310.tas_fa23.EmployeeType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportDAO {

    // Badge and Employee Queries
    private static final String QUERY_EMPLOYEE = "SELECT e.*, et.description AS employeetype, s.description AS shift, b.id AS badgeid, b.description AS name, d.description AS department FROM employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id JOIN badge b ON e.badgeid = b.id JOIN department d ON e.departmentid = d.id ORDER BY e.lastname, e.firstname";
    private static final String QUERY_EMPLOYEE_DEPARTMENT = "SELECT e.*, et.description AS employeetype, s.description AS shift, b.id AS badgeid, b.description AS name, d.description AS department FROM employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id JOIN badge b ON e.badgeid = b.id JOIN department d ON e.departmentid = d.id WHERE e.departmentid = ? ORDER BY e.lastname, e.firstname";
    private static final String QUERY_EMPLOYEE2 = "SELECT e.*, et.description AS employeetype, s.description AS shift, b.id AS badgeid, b.description AS name, d.description AS department FROM employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id JOIN badge b ON e.badgeid = b.id JOIN department d ON e.departmentid = d.id ORDER BY d.description, e.firstname, e.lastname, e.middlename";
    private static final String QUERY_EMPLOYEE_DEPARTMENT2 = "SELECT e.*, et.description AS employeetype, s.description AS shift, b.id AS badgeid, b.description AS name, d.description AS department FROM employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id JOIN badge b ON e.badgeid = b.id JOIN department d ON e.departmentid = d.id WHERE e.departmentid = ? ORDER BY d.description, e.firstname, e.lastname, e.middlename";
    // WhosInWhosOut Queries
    private static final String QUERY_IN_OUT = "SELECT e.firstname, e.lastname, e.badgeid, et.description AS employeetype, s.description AS shift, MIN(CASE WHEN p.eventtypeid = 1 THEN p.timestamp END) AS arrived, CASE WHEN MIN(CASE WHEN p.eventtypeid = 1 THEN p.timestamp END) <= ? THEN 'In' ELSE 'Out' END AS status FROM employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id LEFT JOIN event p ON e.badgeid = p.badgeid AND DATE(p.timestamp) = ? GROUP BY et.description, e.lastname, e.firstname, e.badgeid, s.description ORDER BY status, employeetype, e.lastname, e.firstname";
    private static final String QUERY_IN_OUT_DEPARTMENT = "SELECT e.firstname, e.lastname, e.badgeid, et.description AS employeetype, s.description AS shift, MIN(CASE WHEN p.eventtypeid = 1 THEN p.timestamp END) AS arrived, CASE WHEN MIN(CASE WHEN p.eventtypeid = 1 THEN p.timestamp END) <= ? THEN 'In' ELSE 'Out' END AS status FROM employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id LEFT JOIN event p ON e.badgeid = p.badgeid AND DATE(p.timestamp) = ? WHERE e.departmentid = ? GROUP BY et.description, e.lastname, e.firstname, e.badgeid, s.description ORDER BY status, employeetype, e.lastname, e.firstname";
    //Absenteeism Queries
    
    private final DAOFactory daoFactory;

    ReportDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public String getBadgeSummary(Integer departmentId) {
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
                    String employeetype = rs.getString("employeetype");
                    String department = rs.getString("department");
                    String name = rs.getString("name");
                                        
                    badgeData.put("badgeid", badgeid);
                    badgeData.put("name", name);
                    
                    badgeData.put("department", department);
                    
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
    
    public String getHoursSummary(LocalDate date, Integer departmentId, EmployeeType employeeType) {        
        ArrayList<HashMap<String, String>> hoursSummary = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                if (departmentId != null) {
                    
                } else {
                    
                }
                              
                rs = ps.executeQuery();
                
                while (rs.next()) {
                    HashMap<String, String> hourData = new HashMap<>();
                    
                    hoursSummary.add(hourData);
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

        return Jsoner.serialize(hoursSummary);

    }
    
    public String getAbsenteeismHistory(Integer employeeId) {        
        ArrayList<HashMap<String, String>> absHistory = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                
                              
                rs = ps.executeQuery();
                
                while (rs.next()) {
                    HashMap<String, String> absData = new HashMap<>();
                    
                    absHistory.add(absData);
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

        return Jsoner.serialize(absHistory);

    }
    
    public String getEmployeeSummary(Integer departmentId) {
        ArrayList<HashMap<String, Object>> employees = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                if (departmentId == null) {
                    ps = conn.prepareStatement(QUERY_EMPLOYEE2);
                } else {
                    ps = conn.prepareStatement(QUERY_EMPLOYEE_DEPARTMENT2);
                    ps.setInt(1, departmentId);
                }

                rs = ps.executeQuery();

                while (rs.next()) {
                    HashMap<String, Object> employeeData = new HashMap<>();

                    String badgeid = rs.getString("badgeid");
                    String employeetype = rs.getString("employeetype");
                    String department = rs.getString("department");
                    String firstname = rs.getString("firstname");
                    String shift = rs.getString("shift");
                    LocalDate active = rs.getDate("active").toLocalDate();
                    String middlename = rs.getString("middlename");
                    String lastname = rs.getString("lastname");
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    
                    employeeData.put("firstname", firstname);
                    employeeData.put("employeetype", employeetype);
                    employeeData.put("badgeid", badgeid);
                    employeeData.put("shift", shift);
                    employeeData.put("middlename", middlename);
                    employeeData.put("active", active.format(formatter));
                    employeeData.put("department", department);
                    employeeData.put("lastname", lastname);
                    
                    employees.add(employeeData);
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
