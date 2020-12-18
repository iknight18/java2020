/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXPasswordField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

// For more Security
public class PassValidationController implements Initializable {

    @FXML
    JFXPasswordField pass;
    @FXML
    Text errormsg;
    String test ="N";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void handlepass(ActionEvent ev){
        test = Authentification.authenTest(Authentification.user, pass.getText()); // check if Password is Good
        if(test == "N") errormsg.setText("Please Check Your Password!");
        else pass.getScene().getWindow().hide();
    }
    public  String getTest(){ return test ;}

}
