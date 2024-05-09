package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.TypeCredit;
import tn.esprit.services.TypeCreditService;

import java.io.IOException;

public class AjouterTypeCredit {

    @FXML
    private TextField NomTypeTF;

    @FXML
    private TextField TauxCreditDirectTF;

    private final TypeCreditService typeCreditService = new TypeCreditService();

    @FXML
    void AjouterTypeCredit(ActionEvent event) {
        try {
            typeCreditService.Add(new TypeCredit(NomTypeTF.getText(), Float.parseFloat(TauxCreditDirectTF.getText())));
            showAlert(Alert.AlertType.INFORMATION, "Success", "Type de crédit ajouté avec succès.");
            clearFields();
            RetourTLV(event);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Veuillez entrer un nombre valide pour le taux de crédit.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    void RetourTLV(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        NomTypeTF.clear();
        TauxCreditDirectTF.clear();
    }
}
