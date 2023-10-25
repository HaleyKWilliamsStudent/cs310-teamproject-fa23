package edu.jsu.mcis.cs310.tas_fa23;

import edu.jsu.mcis.cs310.tas_fa23.dao.BadgeDAO;
import edu.jsu.mcis.cs310.tas_fa23.dao.DAOFactory;
import edu.jsu.mcis.cs310.tas_fa23.dao.PunchDAO;
import java.time.*;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class PunchListFindTest {

    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }

    @Test
    public void testFindPunchList1() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts = LocalDate.of(2018, Month.SEPTEMBER, 17);

        Badge b = badgeDAO.find("67637925");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, ts);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        p2.add(punchDAO.find(4716));
        p2.add(punchDAO.find(4811));
        p2.add(punchDAO.find(4813));
        p2.add(punchDAO.find(4847));

        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }

    @Test
    public void testFindPunchList2() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts = LocalDate.of(2018, Month.SEPTEMBER, 27);

        Badge b = badgeDAO.find("87176FD7");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, ts);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        
        p2.add(punchDAO.find(6089));
        p2.add(punchDAO.find(6112));
        p2.add(punchDAO.find(6118));
        p2.add(punchDAO.find(6129));

        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }

    @Test
    public void testFindPunchList3() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts = LocalDate.of(2018, Month.SEPTEMBER, 5);

        Badge b = badgeDAO.find("95497F63");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, ts);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        p2.add(punchDAO.find(3463));
        p2.add(punchDAO.find(3482));

        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }
    
    @Test
    public void testFindPunchList4() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts = LocalDate.of(2018, Month.AUGUST, 9);

        Badge b = badgeDAO.find("618072EA");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, ts);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        p2.add(punchDAO.find(888));
        p2.add(punchDAO.find(954));

        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }
    
    @Test
    public void testFindPunchList5() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts = LocalDate.of(2018, Month.AUGUST, 1);

        Badge b = badgeDAO.find("58EB7EA1");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, ts);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        p2.add(punchDAO.find(166));
        p2.add(punchDAO.find(262));

        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }
    
    @Test
    public void testFindPunchListRange() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts = LocalDate.of(2018, Month.AUGUST, 1);
        LocalDate ts2 = LocalDate.of(2018, Month.AUGUST, 30);

        Badge b = badgeDAO.find("58EB7EA1");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, ts, ts2);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }
                
        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        p2.add(punchDAO.find(166));
        p2.add(punchDAO.find(262));
        p2.add(punchDAO.find(271));
        p2.add(punchDAO.find(342));
        p2.add(punchDAO.find(398));
        p2.add(punchDAO.find(454));
        p2.add(punchDAO.find(502));
        p2.add(punchDAO.find(581));
        p2.add(punchDAO.find(609));
        p2.add(punchDAO.find(664));
        p2.add(punchDAO.find(731));
        p2.add(punchDAO.find(794));
        p2.add(punchDAO.find(838));
        p2.add(punchDAO.find(922));
        p2.add(punchDAO.find(970));
        p2.add(punchDAO.find(1073));
        p2.add(punchDAO.find(1085));
        p2.add(punchDAO.find(1128));
        p2.add(punchDAO.find(1177));
        p2.add(punchDAO.find(1243));
        p2.add(punchDAO.find(1293));
        p2.add(punchDAO.find(1391));
        p2.add(punchDAO.find(1427));
        p2.add(punchDAO.find(1506));
        p2.add(punchDAO.find(1555));
        p2.add(punchDAO.find(1639));
        p2.add(punchDAO.find(1684));
        p2.add(punchDAO.find(1746));
        p2.add(punchDAO.find(2027));
        p2.add(punchDAO.find(2125));
        p2.add(punchDAO.find(2146));
        p2.add(punchDAO.find(2225));
        p2.add(punchDAO.find(2261));
        p2.add(punchDAO.find(2710));
        p2.add(punchDAO.find(2395));
        p2.add(punchDAO.find(2494));
        p2.add(punchDAO.find(2530));
        p2.add(punchDAO.find(2586));
        p2.add(punchDAO.find(2655));
        p2.add(punchDAO.find(2748));
        p2.add(punchDAO.find(2792));
        p2.add(punchDAO.find(2867));
        p2.add(punchDAO.find(2957));
        p2.add(punchDAO.find(2999));
        p2.add(punchDAO.find(3036));
        p2.add(punchDAO.find(3105));

        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }
        
        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());
        
    }
    
    @Test
    public void testFindPunchListRange2() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts = LocalDate.of(2018, Month.SEPTEMBER, 1);
        LocalDate ts2 = LocalDate.of(2018, Month.SEPTEMBER, 30);

        Badge b = badgeDAO.find("95497F63");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, ts, ts2);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }
                
        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        p2.add(punchDAO.find(3463));
        p2.add(punchDAO.find(3482));
        p2.add(punchDAO.find(3548));
        p2.add(punchDAO.find(3586));
        p2.add(punchDAO.find(3659));
        p2.add(punchDAO.find(3694));
        p2.add(punchDAO.find(3765));
        p2.add(punchDAO.find(3830));
        p2.add(punchDAO.find(3916));
        p2.add(punchDAO.find(3965));
        p2.add(punchDAO.find(4057));
        p2.add(punchDAO.find(4126));
        p2.add(punchDAO.find(4234));
        p2.add(punchDAO.find(4279));
        p2.add(punchDAO.find(4393));
        p2.add(punchDAO.find(4429));
        p2.add(punchDAO.find(4535));
        p2.add(punchDAO.find(4585));
        p2.add(punchDAO.find(4653));
        p2.add(punchDAO.find(4677));
        p2.add(punchDAO.find(4926));
        p2.add(punchDAO.find(4964));
        p2.add(punchDAO.find(5065));
        p2.add(punchDAO.find(5118));
        p2.add(punchDAO.find(5217));
        p2.add(punchDAO.find(5256));
        p2.add(punchDAO.find(5349));
        p2.add(punchDAO.find(5418));
        p2.add(punchDAO.find(5641));
        p2.add(punchDAO.find(5694));
        p2.add(punchDAO.find(5786));
        p2.add(punchDAO.find(5841));
        p2.add(punchDAO.find(5928));
        p2.add(punchDAO.find(5981));
        p2.add(punchDAO.find(6078));
        p2.add(punchDAO.find(6121));
        p2.add(punchDAO.find(6216));
        p2.add(punchDAO.find(6280));
        p2.add(punchDAO.find(6360));
        p2.add(punchDAO.find(6407));

        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }
        
        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());
        
    }

}
