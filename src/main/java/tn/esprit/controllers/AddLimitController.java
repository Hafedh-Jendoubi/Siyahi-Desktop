package tn.esprit.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import tn.esprit.models.Conge;
import tn.esprit.models.limitationConge;
import tn.esprit.services.CongeService;
import tn.esprit.services.limitationcongeService;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.Date;

public class AddLimitController {
    @FXML
    private TextField annee;

    @FXML
    private TextField nbrTF;


    @FXML
    private ComboBox<String> mois;

    private final limitationcongeService cs = new limitationcongeService();
    private int joursRestants = 10;
    @FXML
    private Label remainingDaysLabel;


    @FXML
    void ajouter(ActionEvent event) {
        // Check if any of the required fields are empty
        if (annee.getText().isEmpty() || mois.getValue() == null || nbrTF.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Veuillez remplir tous les champs requis");
            return;
        }

        try {
            // Validate year format and value
            String yearText = annee.getText();
            if (!yearText.matches("\\d{4}")) {
                throw new IllegalArgumentException("L'année doit contenir exactement quatre chiffres.");
            }

            int year = Integer.parseInt(yearText);
            if (year < 2024) {
                throw new IllegalArgumentException("L'année doit être 2024 ou ultérieure.");
            }

            int joursDemandes = Integer.parseInt(nbrTF.getText());
            if (joursDemandes <= joursRestants) {
                // Soustrayez les jours demandés du nombre total de jours disponibles
                joursRestants -= joursDemandes;

                // Ajoutez le congé
                limitationConge conge = new limitationConge(year, mois.getValue(), joursDemandes);
                cs.add(conge);

                // Affichez un message de succès
                showAlert(AlertType.INFORMATION, "Success", "Leave request added successfully.");

                // Mettez à jour l'affichage des jours restants
                updateRemainingDaysLabel();

                // Effacez les champs de saisie
                clearFields();
            } else {
                showAlert(AlertType.ERROR, "Error", "Le nombre de jours demandés est supérieur au nombre de jours restants.");
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error", "Veuillez saisir un nombre valide pour l'année et le nombre de congés.");
        } catch (Exception e) {
            // Show error message if any exception occurs
            showAlert(AlertType.ERROR, "Error", e.getMessage());
        }
    }

    // Helper method to show alerts
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Helper method to clear input fields after submission
    private void clearFields() {

        annee.clear();
        mois.setValue(null);
        nbrTF.clear();

    }
    private void updateRemainingDaysLabel() {
        // Assurez-vous que remainingDaysLabel est initialisé avant de le mettre à jour
        if (remainingDaysLabel != null) {
            // Mettez à jour le texte du Label pour afficher le nombre de jours restants
            remainingDaysLabel.setText("Nombre de jours restants : " + joursRestants);
        }
    }
}
