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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


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
    public String getCin(){
        return res;
    }
}
