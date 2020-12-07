/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Load;

import Controllers.DBCon;
import Controllers.LoginController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author feres
 */
public class Project extends Application {
    double xOffset,yOffset;
    String cin;
    
    // The Dashboard Launshing Methode
    public void Dashboard() throws IOException{
    FXMLLoader dashLoader = new FXMLLoader(getClass().getResource("/Views/Dashboard.fxml"));
        Parent dashboard = dashLoader.load();
        Scene scene = new Scene(dashboard);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    // The Login Launshing Methode
    public void Login() throws IOException{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Login.fxml"));
            Parent root = loader.load();
            LoginController control = loader.getController();
            Scene scene = new Scene(root); 
            scene.setFill(Color.TRANSPARENT);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setOnHiding(e -> {  // When Login Closes the right way
                    cin =  control.getCin();
                try {
                    if(cin != "N" && cin != "" && cin != null) {
                        Dashboard();}// we open the Dash
                } catch (IOException ex) {
                    Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
                }
                    System.out.println("cin is " +cin);
                 });
            stage.show();
            
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

    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Login(); // starting with Login
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
