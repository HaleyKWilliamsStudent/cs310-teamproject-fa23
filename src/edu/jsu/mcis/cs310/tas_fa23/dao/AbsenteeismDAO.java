
package edu.jsu.mcis.cs310.tas_fa23.dao;

import edu.jsu.mcis.cs310.tas_fa23.Absenteesim;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbsenteeismDAO {
  private static final String QUERY_FIND = "SELECT * FROM absenteesim WHERE ";
  private final DAOFactory daoFactory;
  
  AbsenteeismDAO(DAOFactory daofactory){
      this.daoFactory = daoFactory;
  }
  
  public Absenteeism find(Employee employee, LocalDate payPeriodStart){
      Absenteeism absenteeism = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      
      try{
          Connection conn = daoFactory.getConnection();
          
          if (conn.isValid(0)){
              ps = conn.preparedStatement()
              
              ps.setInt();
              
              rs = ps.executeQuery();
              
              if (rs.next()){
                  employee employee
              }
          }
      }
  }
}
