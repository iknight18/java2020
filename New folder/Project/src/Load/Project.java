/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Load;

import Controllers.DBCon;
import Controllers.LoginController;
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
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Login.fxml"));
        FXMLLoader dashLoader = new FXMLLoader(getClass().getResource("/Views/Dashboard.fxml"));
        Parent dashboard = dashLoader.load();
        Parent root = loader.load();
        LoginController control = loader.getController();
        Scene scene1 = new Scene(root); 
        Scene scene2 = new Scene(dashboard);
        scene1.setFill(Color.TRANSPARENT);
        stage.setScene(scene1);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setOnHidden(e -> {
           cin =  control.getCin();
           System.out.println(cin);
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
