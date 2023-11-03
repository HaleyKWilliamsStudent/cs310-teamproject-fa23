package edu.jsu.mcis.cs310.tas_fa23;

import edu.jsu.mcis.cs310.tas_fa23.dao.BadgeDAO;
import edu.jsu.mcis.cs310.tas_fa23.dao.DAOFactory;
import edu.jsu.mcis.cs310.tas_fa23.dao.EmployeeDAO;
import edu.jsu.mcis.cs310.tas_fa23.dao.PunchDAO;
import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import edu.jsu.mcis.cs310.tas_fa23.dao.DAOUtility;

public class Main {

    public static void main(String[] args) {
        
        // test database connectivity; get DAOs

        DAOFactory daoFactory = new DAOFactory("tas.jdbc");
//        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
//                
//        // find badge
//
//        Badge b = badgeDAO.find("31A25435");
//                
//        // output should be "Test Badge: #31A25435 (Munday, Paul J)"
//        
//        System.err.println("Test Badge: " + b.toString());
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();
		
        /* Get Punch/Employee Objects */
        
        Punch p = punchDAO.find(3634);
        Employee e = employeeDAO.find(p.getBadge());
        Shift s = e.getShift();
        Badge b = e.getBadge();
        
        /* Get Pay Period Punch List */
        
        LocalDate ts = p.getOriginaltimestamp().toLocalDate();
        LocalDate begin = ts.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate end = begin.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        
        ArrayList<Punch> punchlist = punchDAO.list(b, begin, end);
        
        /* Adjust Punch List */
        
        for (Punch punch : punchlist) {
            punch.adjust(s);
            System.out.println(punch.printAdjusted());
        }
        
        int m = DAOUtility.calculateTotalMinutes(punchlist, s);
        System.out.println(m);
    }

}