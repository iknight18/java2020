/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author feres
 */
public class ForgottenPassController implements Initializable {
    StringBuffer code;
    String userfinal;
    @FXML
    FontAwesomeIconView exitbtn;
    @FXML
    JFXTextArea username;
    @FXML
    JFXTextArea email;
    @FXML
    Text errormsg;
    @FXML
    Text errormsg2;
    @FXML
    Text errormsg3;
    @FXML
    StackPane stackpane;
    @FXML
    JFXTextArea codearea;
    @FXML
    JFXTextArea newPass;
    @FXML
    JFXTextArea newPassConfirm;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        exitbtn.setOnMousePressed(e -> exitbtn.getScene().getWindow().hide());
    }    
    @FXML
    private void resetHandle(ActionEvent event){
        String res;
        String user = username.getText();
        String mail = email.getText();
        res = Authentification.userEmailCheck(user, mail);
        if (res != "N") {
                    // generate code
            userfinal = user;
            Random rand = new Random();
            code = new StringBuffer();
            for (int i = 0; i < 5; i++) {
                code.append(rand.nextInt(10));
            }
            System.out.println(code.toString());
                    //send code
                                             // go to code  page
            ObservableList<Node> childs = stackpane.getChildren();
            Node topNode = childs.get(childs.size()-1);
            topNode.toBack();

            
        }
            else{
                errormsg.setText("Invalid Credentials! Please Verify");
                email.setStyle("-fx-prompt-text-fill: #a10000");
                username.setStyle("-fx-prompt-text-fill: #a10000");
            }       
    }
    @FXML
    private void codeHandle(ActionEvent event){
        String codeCheck = codearea.getText();
        if (codeCheck.equals(code.toString())) {
                         // go to new pass page
            ObservableList<Node> childs = stackpane.getChildren();
            Node topNode = childs.get(childs.size()-1);
            topNode.toBack();
        }
        else{
            errormsg2.setText("Invalid Code! Please Verify");
            codearea.setStyle("-fx-prompt-text-fill: #a10000");
        }
    }
    @FXML
    private void newPassHandle(ActionEvent event){
            String pass = newPass.getText();
            String confirm = newPassConfirm.getText();
            if (pass.equals(confirm)) {
                Authentification.ChangePassword(userfinal, pass);
                Stage current;
                current = (Stage) newPass.getScene().getWindow();
                current.close();
            }
            else{
                errormsg2.setText("Passwords Don't Match! Please Verify");
                newPassConfirm.setStyle("-fx-prompt-text-fill: #a10000");
            }
    
    }

}
