/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


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
    // Check if User and Email match
    public static String userEmailCheck(String username,String email){
        String emailCheck = "N";

        try { 
            Connection conn = DBCon.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs= stmt.executeQuery("select email from authentification,personne where username ='"+username+"' and authentification.cin = personne.cin");  
            while(rs.next()){ 
                emailCheck = rs.getString(1);
            }
            if(!emailCheck.equals(email)) emailCheck ="N";
    
        } catch (Exception ex) {
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emailCheck;
    }
    
    //Change password
    public static void ChangePassword(String username,String password){
    try { 
            Connection conn = DBCon.connect();
            Statement stmt = conn.createStatement();
            stmt.executeQuery("update authentification set password ='"+password+"' where username ='"+username+"'");  
    
        } catch (Exception ex) {
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Email Sending Methodes with java mail
    public static void sendMail(String recepient) throws Exception {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "xxxxxxxxx@gmail.com";
        //Your gmail password
        String password = "xxxxxxxx";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        Message message = prepareMessage(session, myAccountEmail, recepient);

        //Send mail
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("My First Email from Java App");
            String htmlCode = "<h1> WE LOVE JAVA </h1> <br/> <h2><b>Next Line </b></h2>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception ex) {
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
