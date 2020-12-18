/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Controllers.Authentification;
import Controllers.DBCon;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author feres
 */
public class Virement {

    String idv;
    String dateV;
    String type;
    Compte ce;
    Compte cr;
    Float amount;

    public Virement(String dateV, Compte ce, Compte cr, float amount) {
        this.dateV = dateV;
        this.ce = ce;
        this.cr = cr;
        this.amount = amount;
        if (ce.getIdCompte() == Authentification.user) {
            type = "RECEIVED";
        } else {
            type = "SENT";
        }
    }

    public void setIdv(String idv) {
        this.idv = idv;
    }
    public String getCounter(){
        if(type.equals("RECEIVED")) return ce.getIdCompte();
        else return cr.getIdCompte();
    }
    public Float getAmount() {
        return amount;
    }

    public void addVirement() {
        Connection con = DBCon.connect();
        try {
            Statement stmt = con.createStatement();
            String st = "insert into virement values(dbms_random.string('U', 10),'" + dateV + "','" + ce.getP().getCin() + "','" + ce.getIdCompte() + "','" + cr.getP().getCin() + "',"
                    + "'" + cr.getIdCompte() + "'," + amount + ")";
            System.out.println(st);
            stmt.execute(st);
        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getIdv() {
        return idv;
    }

    public String getType() {
        return type;
    }

    public String getDateV() {
        return dateV;
    }

    public Compte getCe() {
        return ce;
    }

    public Compte getCr() {
        return cr;
    }

}
