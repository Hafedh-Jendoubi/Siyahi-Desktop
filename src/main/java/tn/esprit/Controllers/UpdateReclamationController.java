package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.models.Reclamation;
import tn.esprit.services.ReclamationService;

public class UpdateReclamationController {
    @FXML
    private TextField txtReclamationId;
    @FXML
    private Button btnUpdate;

    private ReclamationService reclamationService;

    public UpdateReclamationController() {
        reclamationService = ReclamationService.getInstance();
    }

    @FXML
    private void updateButtonClicked(ActionEvent event) {
        int reclamationId = Integer.parseInt(txtReclamationId.getText());
        Reclamation reclamation = reclamationService.getOne(reclamationId);
        if (reclamation != null) {
            // Show update form for the reclamation
            showUpdateForm(reclamation);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", null, "No reclamation found with ID: " + reclamationId);
        }
    }

    private void showUpdateForm(Reclamation reclamation) {
        // Implement the logic to show the update form
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void RetourHome(ActionEvent event) {
    }
}