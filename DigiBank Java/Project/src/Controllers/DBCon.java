
package Controllers;
import java.sql.*;

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
            System.out.println("Connected Successfully ! ");
        } catch (SQLException ex) {
            System.out.println("Couldn't Connect :"+ ex.getMessage());
        }
        return connection;
    }
}
