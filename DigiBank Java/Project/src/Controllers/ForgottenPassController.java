
package Controllers;

import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.Properties;
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
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;


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
    String mail;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        exitbtn.setOnMousePressed(e -> exitbtn.getScene().getWindow().hide()); // exit on click
    }

    // on reset clicked
    @FXML
    private void resetHandle(ActionEvent event) {
        String res;
        String user = username.getText(); //get username
        mail = email.getText(); //get email
        res = Authentification.userEmailCheck(user, mail); //check if matches
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
                    //Send Email
        String fromEmail = "digibank.noreply@gmail.com"; //requires valid gmail id
        String password = "java2020"; // correct password for gmail id
        String toEmail = mail; // can be any email id 

        System.out.println("SSLEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getDefaultInstance(props, auth);
        System.out.println("Session created");
        EmailUtil.sendEmail(session, toEmail, "DigiBank Password Reset", "Your Code is " +code.toString());

            // go to code  page
            ObservableList<Node> childs = stackpane.getChildren();
            Node topNode = childs.get(childs.size() - 1);
            topNode.toBack();

        } else {
            errormsg.setText("Invalid Credentials! Please Verify");
            email.setStyle("-fx-prompt-text-fill: #a10000");
            username.setStyle("-fx-prompt-text-fill: #a10000");
        }
    }

    @FXML
    private void codeHandle(ActionEvent event) {

        //check if code entered matches the generated one
        String codeCheck = codearea.getText();
        if (codeCheck.equals(code.toString())) {
            // go to new pass page
            ObservableList<Node> childs = stackpane.getChildren();
            Node topNode = childs.get(childs.size() - 1);
            topNode.toBack();
        } else {
            errormsg2.setText("Invalid Code! Please Verify");
            codearea.setStyle("-fx-prompt-text-fill: #a10000");
        }
    }
    //change password in BD
    @FXML
    private void newPassHandle(ActionEvent event) {
        String pass = newPass.getText();
        String confirm = newPassConfirm.getText();
        if (pass.equals(confirm)) { // check if confirmation matches
            Authentification.ChangePassword(userfinal, pass); // change Password in BD
            Stage current;
            current = (Stage) newPass.getScene().getWindow();
            current.close();
        } else {
            errormsg2.setText("Passwords Don't Match! Please Verify"); //error text
            newPassConfirm.setStyle("-fx-prompt-text-fill: #a10000");
        }

    }

}
