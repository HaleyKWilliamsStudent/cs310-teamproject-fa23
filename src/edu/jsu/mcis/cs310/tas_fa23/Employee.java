package edu.jsu.mcis.cs310.tas_fa23;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Employee {
    private final int id;
    private final String firstname, middlename, lastname;
    private final LocalDateTime active;
    private final Badge badge;
    private final Department department;
    private final Shift shift;
    private final EmployeeType employeetype;
    
    /**
     * Create a new Employee
     * @param id The id of the Employee
     * @param firstname String representing the Employee's First Name
     * @param middlename String representing the Employee's Middle Name
     * @param lastname String representing the Employee's Last Name
     * @param active The date the Employee started on
     * @param badge A Badge Object representing the Employee's badge
     * @param department A Department Object representing the Employee's department
     * @param shift A Shift Object representing the Employee's shift
     * @param employeetype The type of Employee Part-Time / Full-Time
     */
    
    public Employee(int id, String firstname, String middlename, String lastname, LocalDateTime active, Badge badge, Department department, Shift shift, EmployeeType employeetype) {
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.active = active;
        this.badge = badge;
        this.department = department;
        this.shift = shift;
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
     * Gets the Department that the Employee is in
     * @return A Department Object
     */
    
    public Department getDepartment() {
        return department;
    }
    
    /**
     * Gets the Shift that the Employee is on
     * @return A Shift Object
     */
    
    public Shift getShift() {
        return shift;
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
        sb.append(", Type: ").append(employeetype).append(", Department: ").append(department.getDescription());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        sb.append(", Active: ").append(active.format(formatter));
        
        return sb.toString();
    }
}
