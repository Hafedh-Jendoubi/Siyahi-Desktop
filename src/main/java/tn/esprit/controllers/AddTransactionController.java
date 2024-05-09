package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.models.Transaction;
import tn.esprit.models.User;
import tn.esprit.services.TransactionService;
import tn.esprit.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.Optional;

import static tn.esprit.services.UserService.connectedUser;

public class AddTransactionController {
    @FXML
    private TextField montant;

    @FXML
    private PasswordField pwd;

    @FXML
    private ComboBox<Long> rib;

    @FXML
    private TextField rib_rec;

    @FXML
    private Text err1;

    @FXML
    private Text err2;

    @FXML
    private Text err3;

    private int tries = 0;
    @FXML
    void AddTransaction(ActionEvent event) {
        int i = 0;
        UserService us = new UserService();
        TransactionService ts = new TransactionService();
        if(rib_rec.getText().matches("[0-9]{16}")){
            if(us.getOneByRIB(Long.parseLong(rib_rec.getText())) == -1){
                err1.setText("• RIB n'existe pas."); err1.setOpacity(1);
            }else{
                rib_rec.setStyle("-fx-border-color: transparent;"); err1.setOpacity(0); i++;
            }
        }else{
            rib_rec.setStyle("-fx-border-color: red;"); err1.setText("• RIB doit contenir 16 chiffres."); err1.setOpacity(1);
        }
        if(montant.getText().matches("[0-9]{3,}")){
            montant.setStyle("-fx-border-color: transparent;");
            double cash = Float.parseFloat(montant.getText());
            if(cash > 0){ //This function depends on the service the account has! Leave it [0..1000] for now.
                montant.setStyle("-fx-border-color: transparent;"); err2.setOpacity(0); i++;
            }else{
                montant.setStyle("-fx-border-color: red;"); err2.setText("• Solde doit être au minimum 100."); err2.setOpacity(1);
            }
        }else{
            montant.setStyle("-fx-border-color: red;"); err2.setText("• Solde doit être au minimum 100."); err2.setOpacity(1);
        }
        if(i == 2){
            User user = us.getOneByEMAIL(connectedUser.getEmail());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if(passwordEncoder.matches(pwd.getText(), user.getPassword())){
                pwd.setStyle("-fx-border-color: transparent;"); i++; err3.setOpacity(0);
            }else{
                tries++;
                pwd.setStyle("-fx-border-color: red"); err3.setOpacity(1); err3.setText("Mot de passe incorrect.");
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
        if(i == 3) { //Tous les inputs sont valides:
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous vraiment transferer " + Float.parseFloat(montant.getText()) + "TND de RIB: \"" + rib.getValue() + "\" vers RIB: \"" + rib_rec.getText() + "\"");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ts.add(new Transaction(rib.getValue(), Long.parseLong(rib_rec.getText()), Double.parseDouble(montant.getText()), new Date(System.currentTimeMillis())));
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

    @FXML
    void initialize(){
        err1.setOpacity(0);
        err2.setOpacity(0);
        err3.setOpacity(0);

        TransactionService ts = new TransactionService();
        ObservableList<Long> RIB = FXCollections.observableArrayList(ts.getOwnerOfRIBs(connectedUser.getId()));
        rib.setValue(ts.getOwnerOfRIBs(connectedUser.getId()).get(0));
        rib.setItems(RIB);
    }
}
