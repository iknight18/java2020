/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Controllers.Authentification;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.text.Text;

/**
 *
 * @author feres
 */
public class Compte {

    String idCompte;
    String type;
    String mdp;
    Personne p;
    String RIB;
    Float Solde;
    // Default Constructor with auto generated RIB and Pin
    public Compte() {
        Random rand = new Random();
        StringBuffer code = new StringBuffer();
        StringBuffer RIB = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            code.append(rand.nextInt(10));
        }
        mdp = code.toString();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                RIB.append(rand.nextInt(10));
            }
            if(i!=4) RIB.append(" ");
        }
        this.RIB = RIB.toString();
        this.Solde = 0f; // init solde at 0
    }
//setters with no check needed
    public void setIdCompte(String idCompte) {
        this.idCompte = idCompte;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setP(Personne p) {
        this.p = p;
    }

    public void setRIB(String RIB) {
        this.RIB = RIB;
    }

    public void setSolde(Float Solde) {
        this.Solde = Solde;
    }
//getters
    public String getIdCompte() {
        return idCompte;
    }

    public String getType() {
        return type;
    }

    public String getMdp() {
        return mdp;
    }

    public Personne getP() {
        return p;
    }

    public String getRIB() {
        return RIB;
    }

    public Float getSolde() {
        return Solde;
    }
//Constructor with a resultset and a person
    public Compte(ResultSet rs, Personne pers) {

        try {
            rs.next();
            if (pers == null) {
                p = Authentification.getUserInfo(rs.getString(1));
            } else {
                p = pers;
            }
            idCompte = rs.getString(2);
            RIB = rs.getString(3);
            mdp = rs.getString(4);
            type = rs.getString(5);
            Solde = rs.getFloat(6);
        } catch (SQLException ex) {
            Logger.getLogger(Compte.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    // set solde in View
    public void setViewInfo(Text s, Text r) {
        s.setText(Solde.toString());
        if (r != null) {
            r.setText(RIB);
        }
    }

    public void deposit(Float sld) {
        Solde += sld;
    }
    //Withdraw with balance check
    public int withdraw(Float sld) {
        if (Solde - sld >= 0) {
            Solde -= sld;
            return 1;
        } else {
            return 0;
        }
    }

}
