package edu.jsu.mcis.cs310.tas_fa23;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Employee {
    private final int id;
    private final String firstname, middlename, lastname;
    private final LocalDateTime active;
    private final Badge badge;
    
    
    private final EmployeeType employeetype;
    
    /**
     * Create a new Employee
     * @param id
     * @param firstname
     * @param middlename
     * @param lastname
     * @param active
     * @param badge
     * @param employeetype 
     */
    
    public Employee(int id, String firstname, String middlename, String lastname, LocalDateTime active, Badge badge, EmployeeType employeetype) {
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.active = active;
        this.badge = badge;
        this.employeetype = employeetype;
    }
    
    /**
     * 
     * @return The Employee's id
     */
    
    public int getId() {
        return id;
    }
    
    /**
     * 
     * @return The Employee's first name
     */
    
    public String getFirstname() {
        return firstname;
    }
    
    /**
     * 
     * @return The Employee's middle name
     */
    
    public String getMiddlename() {
        return middlename;
    }
    
    /**
     * 
     * @return The Employee's last name
     */
    
    public String getLastname() {
        return lastname;
    }
    
    /**
     * 
     * @return The date the Employee started on
     */
    
    public LocalDateTime getActivedate() {
        return active;
    }
    
    /**
     * 
     * @return An object with information about the Employee's badge
     */
    
    public Badge getBadge() {
        return badge;
    }
    
    /**
     * Returns an integer to identify the type of an Employee
     * @return (0 = Part-Time, 1 = Full-Time)
     */
    
    public EmployeeType getEmployeetype() {
        return employeetype;
    }
    
    /**
     * Returns a String representing an Employee
     * @return "ID #id: LastName, FirstName MiddleName (#badgeid), EmployeeType,
     * Department: department, Active: ActiveDate"
     */
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID #").append(id).append(": ");
        sb.append(lastname).append(", ").append(firstname);
        sb.append(" ").append(middlename).append(" (#").append(badge.getId()).append(')');
        sb.append(", Type: ").append(employeetype).append(", Department: ").append("Shipping");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        sb.append(", Active: ").append(active.format(formatter));
        
        return sb.toString();
    }
}
