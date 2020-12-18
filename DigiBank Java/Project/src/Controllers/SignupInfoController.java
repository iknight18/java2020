/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author feres
 */
public class SignupInfoController implements Initializable {
    String u;
    String p;
    String r;
    String pi;
    @FXML
    Text username;

    @FXML
    Text password;
    @FXML
    Text RIB;
    @FXML
    Text pin;
    /**
     * Initializes the controller class.
     */
    public SignupInfoController(String u, String p, String r, String pi) {
        this.u = u;
        this.p = p;
        this.r = r;
        this.pi = pi;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        username.setText("Username : "+u);
        password.setText("Password : "+p);
        RIB.setText("RIB : "+r);
        pin.setText("Card PIN : "+pi);
    }    
    
}
