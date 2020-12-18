
package Controllers;

import Models.Compte;
import Models.Personne;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author feres
 */
public class SignupController implements Initializable {

    @FXML
    Text errormsg;
    @FXML
    GridPane gridInfo;
    @FXML
    GridPane gridAuth;
    @FXML
    FontAwesomeIconView exbutton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        exbutton.setOnMousePressed(e -> exbutton.getScene().getWindow().hide());
        JFXComboBox t = (JFXComboBox) gridAuth.getChildren().get(5);
        JFXComboBox g = (JFXComboBox) gridInfo.getChildren().get(14);
        JFXComboBox s = (JFXComboBox) gridInfo.getChildren().get(20);
        ObservableList<String> gender = FXCollections.observableArrayList();
        gender.addAll("H", "F");
        ObservableList<String> type = FXCollections.observableArrayList();
        type.addAll("Savings Account", "Current Account");
        t.setItems(type);
        g.setItems(gender);
        //g.getSelectionModel().select(Authentification.getUserInfo().getSexe());
        ObservableList<String> statut = FXCollections.observableArrayList();
        statut.addAll("Single", "Married", "Divorced", "Separated", "Widowed");
        s.setItems(statut);
    }
    
    @FXML
    public void signuphandle(ActionEvent event) {
        boolean v = true;
        Personne p = new Personne();
        Compte c = new Compte();
        errormsg.setText("");
        JFXTextField name = (JFXTextField) gridInfo.getChildren().get(11);
        JFXTextField famName = (JFXTextField) gridInfo.getChildren().get(12);
        JFXTextField Id = (JFXTextField) gridInfo.getChildren().get(13);
        JFXComboBox g = (JFXComboBox) gridInfo.getChildren().get(14);
        JFXTextField date = (JFXTextField) gridInfo.getChildren().get(15);
        JFXTextField phone = (JFXTextField) gridInfo.getChildren().get(16);
        JFXTextField ad = (JFXTextField) gridInfo.getChildren().get(17);
        JFXTextField zip = (JFXTextField) gridInfo.getChildren().get(18);
        JFXTextField gov = (JFXTextField) gridInfo.getChildren().get(19);
        JFXComboBox s = (JFXComboBox) gridInfo.getChildren().get(20);
        JFXTextField mail = (JFXTextField) gridInfo.getChildren().get(21);

        
        JFXTextField username = (JFXTextField) gridAuth.getChildren().get(3);
        JFXPasswordField password = (JFXPasswordField) gridAuth.getChildren().get(4);
        JFXComboBox type = (JFXComboBox) gridAuth.getChildren().get(5);
        if(username.getText().length()<5){
            errormsg.setText("Enter Valid Format Please !");
            username.setStyle("-fx-border-color:red");
            v = false;
        }
        if(password.getText().length()<6){
            errormsg.setText("Enter Valid Format Please !");
            password.setStyle("-fx-border-color:red");
            v = false;
        }
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
            try {
                p.setSexe(g.getSelectionModel().getSelectedItem().toString());
                p.setAdresse(ad.getText());
                p.setStatus(s.getSelectionModel().getSelectedItem().toString());
                c.setIdCompte(username.getText());
                c.setType(type.getSelectionModel().getSelectedItem().toString());
                c.setP(p);
                FXMLLoader infoLoader = new FXMLLoader(getClass().getResource("/Views/SignupInfo.fxml"));
                SignupInfoController infoc = new SignupInfoController(c.getIdCompte(),password.getText(),c.getRIB(),c.getMdp());
                infoLoader.setController(infoc);
                Parent root = infoLoader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.showAndWait();
                Authentification.AddInfo(p, c, password.getText());
                errormsg.getScene().getWindow().hide();
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
            } catch (IOException ex) {
                Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
