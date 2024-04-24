package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.models.TypeCredit;
import tn.esprit.services.TypeCreditService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.Optional;

public class ListTypeCredit {

    @FXML
    private ListView<TypeCredit> ListTypeCreditLV;

    private final TypeCreditService typeCreditService = new TypeCreditService();

    @FXML
    public void initialize() {
        refreshList();
    }

    private void refreshList() {
        ObservableList<TypeCredit> typeCredits = FXCollections.observableArrayList(typeCreditService.getAll());
        ListTypeCreditLV.setItems(typeCredits);
    }

    @FXML
    void AjouterTypeCredit(ActionEvent event) {
        // Code pour aller à l'interface d'ajout d'un type de crédit
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTypeCredit.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            refreshList(); // Rafraîchir la liste après ajout
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ouverture de l'interface d'ajout", e.getMessage());
        }
    }

    @FXML
    void ModifierTypeCredit(ActionEvent event) {
        // Code pour aller à l'interface de modification d'un type de crédit sélectionné dans la liste
        TypeCredit selectedTypeCredit = ListTypeCreditLV.getSelectionModel().getSelectedItem();
        if (selectedTypeCredit == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun type de crédit sélectionné", "Veuillez sélectionner un type de crédit à modifier.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierTypeCredit.fxml"));
            Parent edit = loader.load();

            ModifierTypeCredit modifierTypeCredit = loader.getController();
            modifierTypeCredit.initData(selectedTypeCredit);

            Stage window = new Stage();
            Scene editScene = new Scene(edit);
            window.setScene(editScene);
            window.showAndWait();
            refreshList(); // Rafraîchir la liste après modification
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ouverture de l'interface de modification", e.getMessage());
        }
    }

    @FXML
    void SupprimerTypeCredit(ActionEvent event) {
        // Code pour supprimer un type de crédit sélectionné dans la liste
        TypeCredit selectedTypeCredit = ListTypeCreditLV.getSelectionModel().getSelectedItem();
        if (selectedTypeCredit == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun type de crédit sélectionné", "Veuillez sélectionner un type de crédit à supprimer.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce type de crédit ?");
        alert.setContentText("Cette action est irréversible.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            typeCreditService.Delete(selectedTypeCredit);
            ListTypeCreditLV.getItems().remove(selectedTypeCredit);
            showAlert(Alert.AlertType.INFORMATION, "Suppression réussie", null, "Le type de crédit a été supprimé avec succès.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
