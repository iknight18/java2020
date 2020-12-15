/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
public class LoginController implements Initializable {
    private String res;
    @FXML
    AnchorPane anchor;
    @FXML
    FontAwesomeIconView exbutton;
    @FXML
    JFXButton signinbtn;
    @FXML
    JFXTextField username;
    @FXML
    JFXPasswordField password;
    @FXML
    Text errormsg;
    /**
     * Initializes the controller class.
     */
    double xOffset,yOffset;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        exbutton.setOnMousePressed(e -> Platform.exit());
        
       
    }    
    @FXML
    private void signInHandle(ActionEvent event){
     
            String user = username.getText();
            String pass = password.getText();
            res = Authentification.authenTest(user, pass);
            if (res != "N") {
            Stage current = (Stage) signinbtn.getScene().getWindow();
            current.close();
        }
            else{
                errormsg.setText("Invalid Credentials! Please Verify");
                password.setStyle("-fx-prompt-text-fill: #a10000");
                username.setStyle("-fx-prompt-text-fill: #a10000");
    }
    }
    @FXML
    private void forgotHandle(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ForgottenPass.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root); 
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        // Drag Handle
            root.setOnMousePressed(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
                System.out.println(xOffset+"-"+yOffset);
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
    public String getCin(){
        return res;
    }
}
