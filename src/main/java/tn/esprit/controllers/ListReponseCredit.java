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
    void ModifierMLV(ActionEvent event) {
        ReponseCredit selectedReponseCredit = ListReponseCreditLV.getSelectionModel().getSelectedItem();

        if (selectedReponseCredit == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucune reponse n'est sélectionné", "Veuillez sélectionner une reponse à modifier.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReponseCredit.fxml"));
            Parent edit = loader.load();

            ModifierReponseCredit ModifierReponseCredit = loader.getController();
            ModifierReponseCredit.initData(selectedReponseCredit);

            Scene editScene = new Scene(edit);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(editScene);
            window.setHeight(400); window.setMaxHeight(400); window.setMinHeight(400);
            window.setWidth(606); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Modifier une reponse d'un credit");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void SupprimerRC(ActionEvent event) {
        ReponseCredit selectedReponseCredit = ListReponseCreditLV.getSelectionModel().getSelectedItem();

        if (selectedReponseCredit == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucune reponse n'est sélectionné", "Veuillez sélectionner une reponse à modifier.");
            return;
        }

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
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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
