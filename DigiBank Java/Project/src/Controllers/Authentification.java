/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Compte;
import Models.Personne;
import Models.Transaction;
import Models.Virement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author feres
 */
public class Authentification {

    public static String Cin = "N";
    public static String user = "";
    // Get current user Trans Hist
    public static ObservableList<Transaction> getTransactionsHist() throws SQLException {
        Personne p = getUserInfo(); // get current user inf
        Compte c = getCompteInfo(p);//get current uerr account info
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        Transaction t;
        Connection conn = DBCon.connect();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select idtransaction,type,date_t,amount from transaction where cin ='" + Cin + "'");
            while (rs.next()) {
                t = new Transaction(p, c, rs.getString(2), rs.getString(3), rs.getFloat(4));
                t.setIdT(rs.getString(1));
                transactions.add(t);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
        }

        return transactions; // observable list des transactions
    }
    //Get Current user Transfer Hist
    public static ObservableList<Virement> getTransferHist() {
        ObservableList<Virement> virements = FXCollections.observableArrayList();
        Virement t;
        Connection conn = DBCon.connect();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from virement where idcompteemm ='" + user + "' or idcompteacc ='" + user + "'");
                        
            while (rs.next()) {
                t = new Virement(rs.getString(2), getCompteInfo(getUserInfo(rs.getString(3))), getCompteInfo(getUserInfo(rs.getString(5))), rs.getFloat(7));
                t.setIdv(rs.getString(1));
                virements.add(t);

            }
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
        }

        return virements; // return Observable List des Transfers
    }
    // check if RIB exists
    public static boolean checkRIB(String rib) {
        Connection conn = DBCon.connect();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select rib from compte where rib ='" + rib + "'");

            if (!rs.next()) {
                conn.close();

                return false;
            } else {
                conn.close();

                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
    }
    // Clear Transactions and Transfer History
    public static void clearHistory(List<String> transId, List<String> virmID) {
        try {
            Connection conn = DBCon.connect();
            for (String id : transId) {
                Statement stmt = conn.createStatement();
                stmt.execute("delete from transaction where idtransaction = '" + id + "'");
                stmt.close();
            }
            for (String id : virmID) {
                Statement stmt = conn.createStatement();
                stmt.execute("delete from virement where idvirement = '" + id + "'");
                stmt.close();

            }
        } catch (SQLException ex) {
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
// Authentification
    public static String authenTest(String username, String password) {
        try {
            String passCheck = "";
            Connection conn = DBCon.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select password,cin from authentification where username ='" + username + "'");
            while (rs.next()) {
                passCheck = rs.getString(1);
                Cin = rs.getString(2); // Update static value of CIN of Current User
            }
            if (password.equals(passCheck)) {
                user = username;
                return Cin; //retourn la CIN si Authentification Reuissite

            }
            conn.close();

        } catch (Exception ex) {
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "N"; // return "N" in any error
    }

    // Check if User and Email match
    public static String userEmailCheck(String username, String email) {
        String emailCheck = "N";

        try {
            Connection conn = DBCon.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select email from authentification,personne where username ='" + username + "' and authentification.cin = personne.cin");
            while (rs.next()) {
                emailCheck = rs.getString(1);
            }
            if (!emailCheck.equals(email)) {
                emailCheck = "N";
            }
            conn.close();

        } catch (Exception ex) {
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emailCheck;
    }

    //Change password in BD
    public static void ChangePassword(String username, String password) {
        try {
            Connection conn = DBCon.connect();
            Statement stmt = conn.createStatement();
            stmt.executeQuery("update authentification set password ='" + password + "' where username ='" + username + "'");
            conn.close();

        } catch (Exception ex) {
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // get Current User Info From BD and Return Personne object
    public static Personne getUserInfo() {
        Connection conn = DBCon.connect();
        try {
            Statement stmt = conn.createStatement();
            Personne p = new Personne(stmt.executeQuery("select * from personne where cin = " + Cin));
            conn.close();
            return p;
        } catch (Exception ex) {
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    // get the Persons Account Info return Compte object
    public static Compte getCompteInfo(Personne p) throws SQLException {
        Connection conn = DBCon.connect();
        Statement stmt = conn.createStatement();
        Compte c = new Compte(stmt.executeQuery("select * from compte where cin =" + p.getCin()), p);
        conn.close();
        return c;
    }
    // get User Info "Surcharge" With cin given
    public static Personne getUserInfo(String cin) {
        Connection conn = DBCon.connect();
        try {
            Statement stmt = conn.createStatement();
            Personne p = new Personne(stmt.executeQuery("select * from personne where cin = " + cin));
            return p;
        } catch (SQLException ex) {
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    // get Account Info "Surcharge" With Rib given
    public static Compte getCompteInfo(String rib) throws SQLException {
        Connection conn = DBCon.connect();
        Statement stmt = conn.createStatement();
        Compte c = new Compte(stmt.executeQuery("select * from compte where rib ='" + rib + "'"), null);
        return c;
    }
    // Update the info of Personne and Compte in BD
    public static void UpdateInfo(Personne p, Compte c) {
        Connection conn = DBCon.connect();
        try {
            Statement stmt = conn.createStatement();

            if (p != null) {
                String qry1 = "update personne set"
                        + " cin ='" + p.getCin()
                        + "', nom ='" + p.getNom()
                        + "', prenom ='" + p.getPrenom()
                        + "', sexe ='" + p.getSexe()
                        + "', datenaiss ='" + p.getDateNaiss()
                        + "', numtel ='" + p.getNum()
                        + "', ville ='" + p.getVille()
                        + "', adresse ='" + p.getAdresse()
                        + "', code_postal ='" + p.getZip()
                        + "', gouvernerat ='" + p.getGov()
                        + "', statut_social ='" + p.getStatus()
                        + "', email ='" + p.getEmail()
                        + "' where cin = '" + p.getCin() + "'";
                System.out.println(qry1);
                stmt.executeUpdate(qry1);
            }

            String qry2 = "update compte set"
                    + " cin ='" + c.getP().getCin()
                    + "', idcompte ='" + c.getIdCompte()
                    + "', rib ='" + c.getRIB()
                    + "', mdp ='" + c.getMdp()
                    + "', typecompte='" + c.getType()
                    + "', solde =" + c.getSolde()
                    + " where cin = '" + p.getCin() + "'";
            System.out.println(qry2);

            stmt.executeUpdate(qry2);
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Add new Person and Account with Authentification
    public static void AddInfo(Personne p,Compte c,String pass){
        Connection conn = DBCon.connect();
        try {
            Statement stmt = conn.createStatement();

            if (p != null) {
                String qry1 = "Insert into personne Values("
                        + "'" + p.getCin()
                        + "','" + p.getNom()
                        + "','" + p.getPrenom()
                        + "','" + p.getSexe()
                        + "','" + p.getDateNaiss()
                        + "','" + p.getNum()
                        + "','" + p.getVille()
                        + "','" + p.getAdresse()
                        + "','" + p.getZip()
                        + "','" + p.getGov()
                        + "','" + p.getStatus()
                        + "','" + p.getEmail()+"')";
                System.out.println(qry1);
                stmt.execute(qry1);
            }

            String qry2 = "insert into compte values("
                    + "'" + c.getP().getCin()
                    + "','" + c.getIdCompte()
                    + "','" + c.getRIB()
                    + "','" + c.getMdp()
                    + "','" + c.getType()
                    + "'," + c.getSolde()+")";
            System.out.println(qry2);

            stmt.execute(qry2);
            String qry3 = "insert into Authentification values("
                    + "'" + c.getIdCompte()
                    + "','"+ pass
                    + "','" + p.getCin()
                    + "')";
            System.out.println(qry3);
            stmt.execute(qry3);

            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
