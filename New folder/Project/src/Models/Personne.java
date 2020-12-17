/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author feres
 */
public class Personne {

    String cin;
    String nom;
    String prenom;
    String sexe;
    String DateNaiss;
    String num;
    String Ville;
    String Adresse;
    String zip;
    String gov;
    String status;
    String email;

    public Personne(ResultSet rs) throws SQLException {
        rs.next();
        cin = rs.getString(1);
        nom = rs.getString(2);
        prenom = rs.getString(3);
        sexe = rs.getString(4);
        DateNaiss = rs.getString(5);
        num = rs.getString(6);
        Ville = rs.getString(7);
        Adresse = rs.getString(8);
        zip = rs.getString(9);
        gov = rs.getString(10);
        status = rs.getString(11);
        email = rs.getString(12);
    }

    public String getCin() {
        return cin;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public String getDateNaiss() {
        return DateNaiss;
    }

    public String getNum() {
        return num;
    }

    public String getVille() {
        return Ville;
    }

    public String getAdresse() {
        return Adresse;
    }

    public String getZip() {
        return zip;
    }

    public String getGov() {
        return gov;
    }

    public String getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public void setGrid(GridPane gp) {
        Text t = (Text) gp.getChildren().get(11);
        t.setText(nom);
        t = (Text) gp.getChildren().get(12);
        t.setText(prenom);
        t = (Text) gp.getChildren().get(13);
        t.setText(cin);
        t = (Text) gp.getChildren().get(14);
        t.setText(sexe);
        t = (Text) gp.getChildren().get(15);
        t.setText(DateNaiss.substring(0, 10));
        t = (Text) gp.getChildren().get(16);
        t.setText(num);
        t = (Text) gp.getChildren().get(17);
        t.setText(Adresse);
        t = (Text) gp.getChildren().get(18);
        t.setText(zip);
        t = (Text) gp.getChildren().get(19);
        t.setText(gov);
        t = (Text) gp.getChildren().get(20);
        t.setText(status);
        t = (Text) gp.getChildren().get(21);
        t.setText(email);
    }
    public void setName(Text n){
        n.setText(prenom +" "+nom.toUpperCase());
    }
}
