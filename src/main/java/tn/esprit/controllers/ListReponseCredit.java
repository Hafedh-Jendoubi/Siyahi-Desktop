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
import tn.esprit.models.ReponseCredit;
import tn.esprit.services.ReponseCreditService;

import java.io.IOException;
import java.util.Optional;
public class ListReponseCredit {
    @FXML
    private ListView<ReponseCredit> ListReponseCreditLV;

    private ReponseCreditService ReponseCreditService = new ReponseCreditService();

    @FXML
    public void initialize() {
        ObservableList<ReponseCredit> credits = FXCollections.observableArrayList(ReponseCreditService.getAll());
        ListReponseCreditLV.setItems(credits);
    }

    @FXML
    void AjouterRC(ActionEvent event) {
        try {
            Parent ajoute = FXMLLoader.load(getClass().getResource("/AjouterReponseCredit.fxml"));
            Scene ajouteSecene = new Scene(ajoute);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouteSecene);
            window.setHeight(400); window.setMaxHeight(400); window.setMinHeight(400);
            window.setWidth(606); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Ajouter une reponse à un credit");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void SupprimerRC(ActionEvent event) {
        ReponseCreditService rcs = new ReponseCreditService();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer la reponse du credit suivante ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            rcs.Delete(ListReponseCreditLV.getSelectionModel().getSelectedItem());
            ListReponseCreditLV.getItems().remove(ListReponseCreditLV.getSelectionModel().getSelectedItem());
            showSuccessMessage("Credit supprimé avec succès!");
        } else {
            alert.close();
        }
    }

    @FXML
    void VoirCreditsLV(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListCredit.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
