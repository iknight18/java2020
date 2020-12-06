/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author feres
 */
public class DBCon {
    public static Connection connect(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not Working");
           System.out.println(ex.getMessage());
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","tpjava","123456");
            System.out.println("connection mrigla");
        } catch (SQLException ex) {
            System.out.println("Coudnlt Connect"+ ex.getMessage());
        }
        return connection;
    }
}
