package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.models.Credit;
import tn.esprit.services.CreditService;

import java.io.IOException;
import java.util.Optional;

public class ListCredit {

    @FXML
    private ListView<Credit> ListCreditLV;

    private CreditService CreditService = new CreditService();

    @FXML
    public void initialize() {
        ObservableList<Credit> credits = FXCollections.observableArrayList(CreditService.getAll());
        ListCreditLV.setItems(credits);
    }

    @FXML
    void AjouterC(ActionEvent event) {
        try {
            Parent ajout = FXMLLoader.load(getClass().getResource("/AjouterCredit.fxml"));
            Scene ajoutSecene = new Scene(ajout);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajoutSecene);
            window.setHeight(400); window.setMaxHeight(400); window.setMinHeight(400);
            window.setWidth(606); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Ajouter un credit");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void SupprimerC(ActionEvent event) {
        CreditService cs = new CreditService();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer le credit suivant ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            cs.Delete(ListCreditLV.getSelectionModel().getSelectedItem());
            ListCreditLV.getItems().remove(ListCreditLV.getSelectionModel().getSelectedItem());
            showSuccessMessage("Credit supprimé avec succès!");
        } else {
            alert.close();
        }
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}