package edu.jsu.mcis.cs310.tas_fa23;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Absenteeism {
    
    private final Employee employee;
    private final LocalDate payperiod;
    private final BigDecimal percentage;
    
    /**
     * Creates a new Absenteeism object
     * @param employee
     * @param payPeriodStart
     * @param absenteeism 
     */
    
    public Absenteeism(Employee employee, LocalDate payPeriodStart, BigDecimal absenteeism) {
        this.employee = employee;
        this.payperiod = payPeriodStart;
        this.percentage = absenteeism;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getPayperiod() {
        return payperiod;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("#").append(employee.getBadge().getId());  
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        sb.append(" (Pay Period Starting ").append(payperiod.format(formatter)).append("): ");
                
        sb.append(percentage).append("%");
        
        return sb.toString();
    }
    
}
