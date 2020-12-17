/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Compte(ResultSet rs, Personne pers) {
        p = pers;

        try {
            rs.next();
            idCompte = rs.getString(2);
            RIB = rs.getString(3);
            mdp = rs.getString(4);
            type = rs.getString(5);
            Solde = rs.getFloat(6);
        } catch (SQLException ex) {
            Logger.getLogger(Compte.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void setViewInfo(Text s,Text r){
        s.setText(Solde.toString());
        r.setText(RIB);
    }
    public void deposit(Float sld){
        Solde += sld;
    }
    public int withdraw(Float sld){
    if(Solde-sld >= 0) {Solde -=sld; return 1;}
    else return 0;
    }

}
