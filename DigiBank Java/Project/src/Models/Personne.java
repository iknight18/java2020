/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

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
    public Personne() {
    }
    //constructor with resultset forom BD
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
//getters
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
//set the grid in account view
    public void setGrid(GridPane gp) {
        Text t = (Text) gp.getChildren().get(11);
        t.setText(prenom);
        t = (Text) gp.getChildren().get(12);
        t.setText(nom);
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
    //set the grid in settings view
    public void setGridSetting(GridPane gp){
            JFXComboBox c;
            JFXTextField t = (JFXTextField) gp.getChildren().get(11);
        t.setText(prenom);
        t = (JFXTextField) gp.getChildren().get(12);
        t.setText(nom);
        t = (JFXTextField) gp.getChildren().get(13);
        t.setText(cin);
        c = (JFXComboBox) gp.getChildren().get(14);
        c.getSelectionModel().select(sexe);
        t = (JFXTextField) gp.getChildren().get(15);
        t.setText(DateNaiss);
        t = (JFXTextField) gp.getChildren().get(16);
        t.setText(num);
        t = (JFXTextField) gp.getChildren().get(17);
        t.setText(Adresse);
        t = (JFXTextField) gp.getChildren().get(18);
        t.setText(zip);
        t = (JFXTextField) gp.getChildren().get(19);
        t.setText(gov);
        c = (JFXComboBox) gp.getChildren().get(20);
        c.getSelectionModel().select(status);
        t = (JFXTextField) gp.getChildren().get(21);
        t.setText(email);
    }
    //setters with verification
    public int setCin(String cin) {
        if (cin.length() != 8) { //cin = 8 all nums
            return 0;
        } else {
            try {
                Integer.parseInt(cin);
                this.cin = cin;
                return 1;
            } catch (NumberFormatException e) {
                return -1;
            }
        }
    }

    public int setNom(String nom) {
        if (nom.length() > 30) {//nom alpha and ' and space
            return -1;
        } else if (nom.matches("[a-zA-Z\\s']+")) {
            this.nom = nom;
            return 1;
        } else {
            return 0;
        }

    }

    public int setPrenom(String prenom) {
        if (prenom.length() > 30) { //prenom alpha and space and '
            return -1;
        } else if (prenom.matches("[a-zA-Z\\s']+")) {
            this.prenom = prenom;
            return 1;
        } else {
            return 0;
        }
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int setDateNaiss(String DateNaiss) { //date format but not validation
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        try {
            format.parse(DateNaiss);
            this.DateNaiss = DateNaiss;
            return 1;
        } catch (java.text.ParseException ex) {
            return 0;

        }
    }

    public int setNum(String num) { // num all nums
        try {   
            Integer.parseInt(num);
            this.num = num;
            return 1;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void setVille(String Ville) {
        this.Ville = Ville;

    }

    public void setAdresse(String Adresse) {
        this.Adresse = Adresse;
    }

    public int setZip(String zip) {
        if (zip.length() != 4) { //zip 4 digits
            return 0;
        } else {
            try {
                Integer.parseInt(zip);
                this.zip = zip;
                return 1;
            } catch (NumberFormatException e) {
                return -1;
            }
        }
    }

    public int setGov(String gov) { //gov alpha space and '

        if (gov.matches("[a-zA-Z\\s']+")) {
            this.gov = gov;
            return 1;
        } else {
            return 0;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int setEmail(String email) {
//valid email
        if (email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            this.email = email;
            System.out.println("Email is Valid");
            return 1;
        }
        else{
            return 0;
        }
    }

    public void setName(Text n) { // name in card view
        n.setText(prenom + " " + nom.toUpperCase());
    }
}
