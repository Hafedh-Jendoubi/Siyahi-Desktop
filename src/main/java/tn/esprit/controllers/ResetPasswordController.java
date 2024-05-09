package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.services.UserService;

import java.util.Optional;

public class ResetPasswordController {
    UserService us = new UserService();

    @FXML
    private Button confirmBut;

    @FXML
    private TextField newPass;

    @FXML
    private Text newPassLabel;

    @FXML
    private TextField repeatPass;

    @FXML
    private Text repeatPassField;

    @FXML
    private TextField tokenField;

    @FXML
    private AnchorPane tokenLabel;

    @FXML
    private Button verifyBut;

    @FXML
    private Text typeTokenLabel;

    @FXML
    public void verify(ActionEvent event) {
        int id = us.getOneByToken(tokenField.getText());
        if(id == -1){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Verifiez votre Token inséré");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                alert.close();
            }else{
                alert.close();
            }
        }else{
            typeTokenLabel.setOpacity(0);
            tokenField.setOpacity(0);
            verifyBut.setOpacity(0);
            newPassLabel.setOpacity(1);
            repeatPassField.setOpacity(1);
            repeatPass.setOpacity(1);
            repeatPassField.setOpacity(1);
            newPass.setOpacity(1);
            newPassLabel.setOpacity(1);
            confirmBut.setOpacity(1);
        }
    }

    @FXML
    public void goOnReset(ActionEvent event) {
        if(!newPass.getText().equals(repeatPass.getText())){
            repeatPass.setStyle("-fx-border-color: red;");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Votre mot de pass n'est pas identique! Vérifiez.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                alert.close();
            }else{
                alert.close();
            }
        }else{
            int id = us.getOneByToken(tokenField.getText());
            repeatPass.setStyle("-fx-border-color: transparent;");
            us.resetPassword(id, repeatPass.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Votre mot de pass a été modifé");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                alert.close();
            }else{
                alert.close();
            }
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.close();
        }
    }

    @FXML
    void initialize(){
        typeTokenLabel.setOpacity(1);
        tokenField.setOpacity(1);
        verifyBut.setOpacity(1);
        newPassLabel.setOpacity(0);
        repeatPassField.setOpacity(0);
        repeatPass.setOpacity(0);
        repeatPassField.setOpacity(0);
        newPass.setOpacity(0);
        newPassLabel.setOpacity(0);
        confirmBut.setOpacity(0);
    }
}
