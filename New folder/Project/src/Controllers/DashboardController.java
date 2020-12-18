/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Personne;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author feres
 */
public class DashboardController implements Initializable {

    @FXML
    HBox right;
    @FXML
    JFXButton accountbtn;
    @FXML
    JFXButton transbtn;
    @FXML
    JFXButton genbtn;
    @FXML
    FontAwesomeIconView gearbtn;

    double xOffset,yOffset;
    Personne p = Authentification.getUserInfo();


    public void initialize(URL url, ResourceBundle rb) {

        this.accountBtn(null);  
        
        }
    @FXML
    private void transBtn(ActionEvent e){
        accountbtn.getStyleClass().clear();
        accountbtn.getStyleClass().add("UnselectedMenu");
        genbtn.getStyleClass().clear();
        genbtn.getStyleClass().add("UnselectedMenu");
        transbtn.getStyleClass().clear();
        transbtn.getStyleClass().add("SelectedMenu");
        try {
                FXMLLoader trLoader = new FXMLLoader(getClass().getResource("/Views/Transactions.fxml"));
            VBox a = trLoader.load();
            HBox.setHgrow(a, Priority.ALWAYS);
            right.getChildren().remove(right.getChildren().size()-1);
            right.getChildren().add(a);
            System.out.println("Account Called !");
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @FXML
    private void generateBtn(ActionEvent e){
        accountbtn.getStyleClass().clear();
        accountbtn.getStyleClass().add("UnselectedMenu");
        transbtn.getStyleClass().clear();
        transbtn.getStyleClass().add("UnselectedMenu");
        genbtn.getStyleClass().clear();
        genbtn.getStyleClass().add("SelectedMenu");
    }
    @FXML
    private void accountBtn(ActionEvent e){
        accountbtn.getStyleClass().clear();
        accountbtn.getStyleClass().add("SelectedMenu");
        transbtn.getStyleClass().clear();
        transbtn.getStyleClass().add("UnselectedMenu");
        genbtn.getStyleClass().clear();
        genbtn.getStyleClass().add("UnselectedMenu");
        
        try {
                FXMLLoader acLoader = new FXMLLoader(getClass().getResource("/Views/Account.fxml"));
            VBox a = acLoader.load();
            HBox.setHgrow(a, Priority.ALWAYS);
            right.getChildren().remove(right.getChildren().size()-1);
            right.getChildren().add(a);
            System.out.println("Account Called !");
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void gearBtn(MouseEvent e){
        try {
                FXMLLoader stLoader = new FXMLLoader(getClass().getResource("/Views/Settings.fxml"));
            SettingsController st = stLoader.getController();
            Parent root = stLoader.load();
            Scene scene = new Scene(root); 
            scene.setFill(Color.TRANSPARENT);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            stage.setOnHiding(ev  -> accountBtn(null));
            // Drag Handle
            root.setOnMousePressed(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        
        });
             root.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX()-xOffset);
                stage.setY(event.getScreenY()-yOffset);
            }
        
        });
                   

        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    }    
    

