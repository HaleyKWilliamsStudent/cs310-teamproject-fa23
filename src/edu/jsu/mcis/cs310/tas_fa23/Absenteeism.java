package edu.jsu.mcis.cs310.tas_fa23;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Absenteeism {
    
    private final Employee employee;
    private final LocalDateTime payPeriodStart;
    private final BigDecimal absenteeism;
    
    public Absenteeism(Employee employee, LocalDateTime payPeriodStart, BigDecimal absenteeism) {
        this.employee = employee;
        this.payPeriodStart = payPeriodStart;
        this.absenteeism = absenteeism;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDateTime getPayPeriodStart() {
        return payPeriodStart;
    }

    public BigDecimal getAbsenteeism() {
        return absenteeism;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("#").append(employee.getBadge().getId());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        sb.append(" (Pay Period Starting ").append(payPeriodStart.format(formatter)).append("): ");
        sb.append(absenteeism).append("%");
        
        return sb.toString();
    }
    
}
