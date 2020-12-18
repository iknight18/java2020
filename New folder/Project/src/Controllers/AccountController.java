/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Compte;
import Models.Personne;
import Models.Transaction;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author feres
 */
public class AccountController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    FontAwesomeIconView exbutton;
    @FXML
    GridPane grid;
    @FXML
    Text username;
    @FXML
    Text name;
    @FXML
    Text rib;
    @FXML
    Text solde;
    Personne p;
    Compte c;
    @FXML
    JFXTextField amount;
    @FXML
    Text error;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        exbutton.setOnMousePressed(e -> Platform.exit());

        try {
            username.setText(Authentification.user);
            p = Authentification.getUserInfo();
            p.setGrid(grid);
            c = Authentification.getCompteInfo(p);
            c.setViewInfo(solde, rib);
            p.setName(name);
        } catch (SQLException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deposit(ActionEvent e) {
        try {
            error.setText("");
            Float s = Float.parseFloat(amount.getText());
            c.deposit(s);
            c.setViewInfo(solde, rib);
            Authentification.UpdateInfo(p, c);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date();
            Transaction t = new Transaction(p, c, "DEPOSIT", formatter.format(date),s);
            t.addTransaction();
            amount.clear();

        } catch (NumberFormatException ex) {
            error.setText("Please enter a number !");
            amount.setStyle("-fx-prompt-text-fill: #a10000");
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void withdraw(ActionEvent e) {
        try {
            error.setText("");
            Float s = Float.parseFloat(amount.getText());
            if (c.withdraw(s) == 0) {
                throw new Exception("Not Enough Balance !");
            }
            c.setViewInfo(solde, rib);
            Authentification.UpdateInfo(p, c);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date();
            Transaction t = new Transaction(p, c, "WITHDRAWAL", formatter.format(date),s);
            t.addTransaction();
            amount.clear();
        } catch (NumberFormatException ex) {
            error.setText("Please enter a number !");
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            error.setText(ex.getMessage());
        }
    }

}
