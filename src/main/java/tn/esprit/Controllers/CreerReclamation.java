package tn.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.models.ObjetReclamation;
import tn.esprit.models.Reclamation;
import tn.esprit.services.ReclamationService;

import java.sql.Timestamp;

public class CreerReclamation {

    @FXML
    private ComboBox<ObjetReclamation> objectComboBox;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField auteurField;

    @FXML
    private TextField emailField;

    @FXML
    public void ajouterReclamation() {
        ObjetReclamation objReclamation = objectComboBox.getValue(); // Obtenir l'objet sélectionné dans le ComboBox
        String description = descriptionField.getText();
        String auteur = auteurField.getText();
        String email = emailField.getText();
        Timestamp dateCreation = new Timestamp(System.currentTimeMillis()); // La date de création peut être automatiquement générée

        try {
            // Créer une nouvelle réclamation
            Reclamation nouvelleReclamation = new Reclamation(objReclamation, description, dateCreation, auteur, email, true);

            // Appeler le service pour ajouter la réclamation à la base de données
            ReclamationService reclamationService = new ReclamationService();
            reclamationService.add(nouvelleReclamation);

            System.out.println("Réclamation ajoutée : " + objReclamation + " - " + description + " - " + auteur + " - " + email);
        } catch (IllegalArgumentException e) {
            // Gérer les erreurs de validation
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}
