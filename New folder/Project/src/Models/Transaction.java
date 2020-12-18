/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Controllers.DBCon;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author feres
 */
public class Transaction {
    String idT;
    Personne p;
    Compte c;
    String type;
    String Datet;
    Float amount;
    public Transaction(Personne p, Compte c, String type, String Datet,float amount) {
        this.p = p;
        this.c = c;
        this.type = type;
        this.amount = amount;
        setDatet(Datet);
    }

    public Float getAmount() {
        return amount;
    }

    public String getIdT() {
        return idT;
    }

    public void setIdT(String idT) {
        this.idT = idT;
    }

    public void addTransaction(){
        Connection con = DBCon.connect();
        try {
            Statement stmt = con.createStatement();
            stmt.execute("insert into transaction values('"+p.getCin()+"','"+c.getIdCompte()+"',dbms_random.string('U', 10),'"+type+"','"+Datet+"',"+amount+")");
        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setP(Personne p) {
        this.p = p;
    }

    public void setC(Compte c) {
        this.c = c;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int setDatet(String Datet) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try {
            format.parse(Datet);
            this.Datet = Datet;
            return 1;
        } catch (java.text.ParseException ex) {
            return 0;

        }
    }

    public Personne getP() {
        return p;
    }

    public Compte getC() {
        return c;
    }

    public String getType() {
        return type;
    }

    public String getDatet() {
        return Datet;
    }
}
