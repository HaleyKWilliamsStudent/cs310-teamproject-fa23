package edu.jsu.mcis.cs310.tas_fa23;

import edu.jsu.mcis.cs310.tas_fa23.dao.BadgeDAO;
import edu.jsu.mcis.cs310.tas_fa23.dao.DAOFactory;
import edu.jsu.mcis.cs310.tas_fa23.dao.PunchDAO;
import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        
        // test database connectivity; get DAOs

        DAOFactory daoFactory = new DAOFactory("tas.jdbc");
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
                
        // find badge

        Badge b = badgeDAO.find("31A25435");
                
        // output should be "Test Badge: #31A25435 (Munday, Paul J)"
        
        System.err.println("Test Badge: " + b.toString());
        
        PunchDAO punchDao = daoFactory.getPunchDAO();
        
        Punch p1 = new Punch(103, badgeDAO.find("021890C0"), EventType.CLOCK_IN);
        
        int punchid = punchDao.create(p1);
        
        System.err.println(punchid);
    }

}
