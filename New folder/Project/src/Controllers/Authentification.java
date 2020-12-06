/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author feres
 */
public class Authentification {
    public static String authenTest(String username,String password){
                    String Cin ="N";        
        try { 
            String passCheck = "";
            Connection conn = DBCon.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs= stmt.executeQuery("select password,cin from authentification where username ='"+username+"'");  
            while(rs.next()){ 
                passCheck = rs.getString(1);
                Cin = rs.getString(2);
            }
                    if (password.equals(passCheck)) {
                        
                        return Cin;
                        
                    }     
        } catch (Exception ex) {
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "N";
    }
}
