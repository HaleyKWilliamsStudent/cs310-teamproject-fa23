package edu.jsu.mcis.cs310.tas_fa23.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ReportDAO {
    
    private final DAOFactory daoFactory;
    
    ReportDAO (DAOFactory daoFactory){
        this.daoFactory = daoFactory;
    }
    
    public String getBadgeSummary(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            Connection conn = daoFactory.getConnection();
            
            if(conn.isValid(0)){
               rs = ps.executeQuery;
               
               if (rs.next()){
                   
               }
            }
        } catch(SQLException e){
            throw new DAOException(e.getMessage());
    } finally{
            if (rs != null)
        }
}
