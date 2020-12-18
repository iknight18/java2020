/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.Authentification;
import Models.Compte;
import Models.Personne;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author feres
 */
public class SettingsController implements Initializable {

    @FXML
    FontAwesomeIconView exbutton;
    @FXML
    GridPane grid;
    @FXML
    Text errormsg;
    @FXML
    Text successmsg;
    Personne p = Authentification.getUserInfo();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        exbutton.setOnMousePressed(e -> exbutton.getScene().getWindow().hide());
        Personne p = Authentification.getUserInfo();
        JFXComboBox g = (JFXComboBox) grid.getChildren().get(14);
        JFXComboBox s = (JFXComboBox) grid.getChildren().get(20);
        ObservableList<String> gender = FXCollections.observableArrayList();
        gender.addAll("H", "F");
        g.setItems(gender);
        //g.getSelectionModel().select(Authentification.getUserInfo().getSexe());
        ObservableList<String> statut = FXCollections.observableArrayList();
        statut.addAll("Single", "Married", "Divorced", "Separated", "Widowed");
        s.setItems(statut);
        p.setGridSetting(grid);
        //s.getSelectionModel().select(Authentification.getUserInfo().getStatus());
    }

    @FXML
    private void save(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/PassValidation.fxml"));
            PassValidationController pv = loader.getController();
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.showAndWait();
            boolean v = true;
            errormsg.setText("");
            successmsg.setText("");
            JFXTextField name = (JFXTextField) grid.getChildren().get(11);
            JFXTextField famName = (JFXTextField) grid.getChildren().get(12);
            JFXTextField Id = (JFXTextField) grid.getChildren().get(13);
            JFXComboBox g = (JFXComboBox) grid.getChildren().get(14);
            JFXTextField date = (JFXTextField) grid.getChildren().get(15);
            JFXTextField phone = (JFXTextField) grid.getChildren().get(16);
            JFXTextField ad = (JFXTextField) grid.getChildren().get(17);
            JFXTextField zip = (JFXTextField) grid.getChildren().get(18);
            JFXTextField gov = (JFXTextField) grid.getChildren().get(19);
            JFXComboBox s = (JFXComboBox) grid.getChildren().get(20);
            JFXTextField mail = (JFXTextField) grid.getChildren().get(21);

            if (p.setPrenom(name.getText()) == -1 || p.setPrenom(name.getText()) == 0) {
                errormsg.setText("Enter Valid Format Please !");
                name.setStyle("-fx-border-color:red");
                v = false;
            }
            if (p.setNom(famName.getText()) == -1 || p.setNom(famName.getText()) == 0) {
                errormsg.setText("Enter Valid Format Please !");
                famName.setStyle("-fx-border-color:red");
                v = false;

            }
            if (p.setCin(Id.getText()) == -1 || p.setCin(Id.getText()) == 0) {
                errormsg.setText("Enter Valid Format Please !");
                Id.setStyle("-fx-border-color:red");
                v = false;

            }
            if (p.setDateNaiss(date.getText()) == 0) {
                errormsg.setText("Enter Valid Format Please !");
                date.setStyle("-fx-border-color:red");
                v = false;

            }
            if (p.setNum(phone.getText()) == 0) {
                errormsg.setText("Enter Valid Format Please !");
                phone.setStyle("-fx-border-color:red");
                v = false;

            }
            if (p.setZip(zip.getText()) == -1 || p.setZip(zip.getText()) == 0) {
                errormsg.setText("Enter Valid Format Please !");
                zip.setStyle("-fx-border-color:red");
                v = false;

            }
            if (p.setGov(gov.getText()) == 0) {
                errormsg.setText("Enter Valid Format Please !");
                gov.setStyle("-fx-border-color:red");
                v = false;

            }
            if (p.setEmail(mail.getText()) == 0) {
                errormsg.setText("Enter Valid Format Please !");
                mail.setStyle("-fx-border-color:red");
                v = false;

            }
            if (v) {
                p.setSexe(g.getSelectionModel().getSelectedItem().toString());
                p.setAdresse(ad.getText());
                p.setStatus(s.getSelectionModel().getSelectedItem().toString());
                successmsg.setText("Saved Successfully !");
                name.setStyle("");
                famName.setStyle("");
                Id.setStyle("");
                g.setStyle("");
                date.setStyle("");
                phone.setStyle("");
                ad.setStyle("");
                zip.setStyle("");
                gov.setStyle("");
                s.setStyle("");
                mail.setStyle("");
                try {
                    Authentification.UpdateInfo(p, Authentification.getCompteInfo(p));
                } catch (SQLException ex) {
                    Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPers(Personne pers) {
        pers = p;
    }

}
