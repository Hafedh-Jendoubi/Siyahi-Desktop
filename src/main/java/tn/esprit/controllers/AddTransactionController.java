package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tn.esprit.models.Transaction;
import tn.esprit.models.User;
import tn.esprit.services.TransactionService;
import tn.esprit.services.UserService;

import java.sql.Date;
import java.util.Optional;

import static tn.esprit.services.UserService.connectedUser;

public class AddTransactionController {
    @FXML
    private TextField montant;

    @FXML
    private PasswordField pwd;

    @FXML
    private TextField rib;

    @FXML
    private TextField rib_rec;

    private int tries = 0;
    @FXML
    void AddTransaction(ActionEvent event) {
        int i = 0;
        UserService us = new UserService();
        TransactionService ts = new TransactionService();
        if(rib.getText().matches("[0-9]{16}")){
            rib.setStyle("-fx-border-color: transparent;"); i++;
        }else{
            rib.setStyle("-fx-border-color: red;");
        }
        if(rib_rec.getText().matches("[0-9]{16}")){
            rib_rec.setStyle("-fx-border-color: transparent;"); i++;
        }else{
            rib_rec.setStyle("-fx-border-color: red;");
        }
        if(montant.getText().matches("[0-9]{2,4}")){
            montant.setStyle("-fx-border-color: transparent;");
            double cash = Float.parseFloat(montant.getText());
            if(cash > 0 && cash < 1000){ //This function depends on the service the account has! Leave it [0..1000] for now.
                montant.setStyle("-fx-border-color: transparent;"); i++;
            }else{
                montant.setStyle("-fx-border-color: red;");
            }
        }else{
            montant.setStyle("-fx-border-color: red;");
        }
        if(i == 3){
            User user = us.getOneByEMAIL(connectedUser.getEmail());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if(passwordEncoder.matches(pwd.getText(), user.getPassword())){
                pwd.setStyle("-fx-border-color: transparent;"); i++;
            }else{
                tries++;
                pwd.setStyle("-fx-border-color: red");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Votre mot de passe est incorrect! [" + tries + "/3]");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    alert.close();
                } else {
                    alert.close();
                }
            }
        }
        if(tries == 3){
            CancelAddTransaction(event);
        }
        if(i == 4) { //Tous les inputs sont valides:
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous vraiment transferer " + Float.parseFloat(montant.getText()) + "TND de RIB: \"" + rib.getText() + "\" vers RIB: \"" + rib_rec.getText() + "\"");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ts.add(new Transaction(Long.parseLong(rib.getText()), Long.parseLong(rib_rec.getText()), Double.parseDouble(montant.getText()), new Date(System.currentTimeMillis())));
                CancelAddTransaction(event);
            }else{
                alert.close();
            }
            CancelAddTransaction(event);
        }
    }

    @FXML
    void CancelAddTransaction(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
}
