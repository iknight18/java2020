/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Compte;
import Models.Personne;
import Models.Transaction;
import Models.Virement;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author feres
 */
public class TransactionsController implements Initializable {

    @FXML
    FontAwesomeIconView exbutton;
    Personne p;
    Compte c;
    @FXML
    Text username;

    @FXML
    Text solde;
    @FXML
    TableView<Transaction> table;
    @FXML
    TableView<Virement> tabletransfer;
    @FXML
    JFXTextField rib;
    @FXML
    JFXTextField amount;
    @FXML
    JFXPasswordField pin;
    @FXML
    Text errormsg;
    @FXML
    Text successmsg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        exbutton.setOnMousePressed(e -> Platform.exit());

        refreshTransactions();
        refreshTransfer();


        try {
            username.setText(Authentification.user);
            p = Authentification.getUserInfo();
            c = Authentification.getCompteInfo(p);
            c.setViewInfo(solde, null);

        } catch (SQLException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void refreshTransactions(){
            //create cols;
        TableColumn<Transaction, String> idcol
                = new TableColumn<Transaction, String>("ID");
        TableColumn<Transaction, String> Typecol
                = new TableColumn<Transaction, String>("Type");
        TableColumn<Transaction, String> datecol
                = new TableColumn<Transaction, String>("Date");
        TableColumn<Transaction, String> amount
                = new TableColumn<Transaction, String>("Amount");
        //values
        idcol.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getIdT()));

        Typecol.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getType()));
        datecol.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getDatet()));
        amount.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getAmount().toString()));

        datecol.setSortType(TableColumn.SortType.DESCENDING);
        table.setItems(Authentification.getTransactionsHist());
        table.getColumns().clear();
        table.getColumns().addAll(idcol, Typecol, datecol, amount);
    }
    public void refreshTransfer(){
    //create cols;
        TableColumn<Virement, String> idcol
                = new TableColumn<Virement, String>("ID");
        TableColumn<Virement, String> Typecol
                = new TableColumn<Virement, String>("Type");
        TableColumn<Virement, String> datecol
                = new TableColumn<Virement, String>("Date");
        TableColumn<Virement, String> amount
                = new TableColumn<Virement, String>("Amount");
                TableColumn<Virement, String> compte
                = new TableColumn<Virement, String>("Counter Part");
        //values
        idcol.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getIdv()));

        Typecol.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getType()));
        datecol.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getDateV()));
        amount.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getAmount().toString()));
        compte.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getCounter()));


        datecol.setSortType(TableColumn.SortType.DESCENDING);
        tabletransfer.setItems(Authentification.getTransferHist());
        tabletransfer.getColumns().clear();
        tabletransfer.getColumns().addAll(idcol, Typecol, datecol, amount,compte);
    }
    @FXML
    private void transfer(ActionEvent event) throws Exception {
        try {
            System.out.println("Part0");
            errormsg.setText("");
            successmsg.setText("");
            Float s = Float.parseFloat(amount.getText());
            System.out.println("Pin : " +c.getMdp()+" and pin = "+ pin.getText());
            if (c.withdraw(s) == 0) {
                System.out.println("Part0-1");
                throw new Exception("Not Enough Balance !");
            } else if (Authentification.checkRIB(rib.getText())) {

                //System.out.println("RIB : "+Authentification.checkRIB(rib.getText()));
                if (c.getMdp().equals(pin.getText())) {
                    //Virement
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    Date date = new Date();
                    Virement v = new Virement(formatter.format(date),c,Authentification.getCompteInfo(rib.getText()),s);
                    v.getCr().deposit(s);
                    v.addVirement();
                    c.setViewInfo(solde, null);
                    System.out.println("Part1");
                    Authentification.UpdateInfo(v.getCr().getP(),v.getCr());
                    System.out.println("Part2");
                    Authentification.UpdateInfo(p, c);
                    rib.setText("");
                    amount.setText("");
                    pin.setText("");
                    successmsg.setText("Transfer Successful !");
                    refreshTransfer();
                }else {
                    throw new Exception("Check your PIN");
                }
            }

        } catch (NumberFormatException ex) {
            errormsg.setText("Please Enter a valid amount");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            errormsg.setText(ex.getMessage());
        }
    }

}
