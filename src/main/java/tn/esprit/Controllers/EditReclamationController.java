package tn.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.models.Reclamation;
import tn.esprit.services.ReclamationService;

public class EditReclamationController {

    @FXML
    private ComboBox<Reclamation> reclamationComboBox;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField auteurField;

    @FXML
    private TextField emailField;

    private ReclamationService reclamationService;

    @FXML
    public void initialize() {
        // Initialiser le service de réclamation
        reclamationService = new ReclamationService();

        // Remplir la ComboBox avec toutes les réclamations existantes
        reclamationComboBox.getItems().addAll(reclamationService.getAll());
    }

    @FXML
    public void modifierReclamation() {
        Reclamation reclamation = reclamationComboBox.getValue();
        if (reclamation != null) {
            // Mettre à jour les détails de la réclamation
            reclamation.setDescription(descriptionField.getText());
            reclamation.setAuteur(auteurField.getText());
            reclamation.setEmail(emailField.getText());

            // Appeler le service pour mettre à jour la réclamation
            reclamationService.update(reclamation);
        }
    }
}
