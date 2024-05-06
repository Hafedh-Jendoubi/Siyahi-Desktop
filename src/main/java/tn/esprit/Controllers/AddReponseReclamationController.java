package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tn.esprit.interfaces.IService;
import tn.esprit.models.ObjetReclamation;
import tn.esprit.models.Reclamation;
import tn.esprit.models.ReponseReclamation;
import tn.esprit.services.ReclamationService;

import java.io.IOException;
import java.sql.Timestamp;

public class AddReponseReclamationController {
    @FXML
    private TextField descriptionField;

    @FXML
    private TextField reclamationIdField;

    @FXML
    private TextField dateCreationField;

    @FXML
    private TextField auteurField;










    private IService<ReponseReclamation> reponseReclamationService;
    private Reclamation selectedReclamation;

    public void setReponseReclamationService(IService<ReponseReclamation> reponseReclamationService) {
        this.reponseReclamationService = reponseReclamationService;
    }

    public void setSelectedReclamation(Reclamation selectedReclamation) {
        this.selectedReclamation = selectedReclamation;
    }

    @FXML
    private void ajouterReponseReclamation(ActionEvent event) {
        // Récupérer les valeurs des champs
        String description = descriptionField.getText();
        String reclamationId = reclamationIdField.getText();
        String dateCreation = dateCreationField.getText();
        String auteur = auteurField.getText();

        // Vérifier si les champs ne sont pas vides
        if (description.isEmpty() || reclamationId.isEmpty() || dateCreation.isEmpty() || auteur.isEmpty()) {
            // Afficher un message d'erreur ou gérer de manière appropriée si un champ est vide
            System.out.println("Veuillez remplir tous les champs.");
            return;
        }

        // Effectuer les opérations nécessaires pour ajouter une réponse à la réclamation
        // Par exemple, vous pouvez appeler une méthode dans votre service ou DAO pour effectuer l'ajout dans la base de données

        // Réinitialiser les champs après l'ajout
        descriptionField.clear();
        reclamationIdField.clear();
        dateCreationField.clear();
        auteurField.clear();

        // Afficher un message de succès ou rediriger l'utilisateur vers une autre page
        System.out.println("Réponse à la réclamation ajoutée avec succès !");
    }

    // Méthode pour gérer l'action du bouton "Retour"




    @FXML
    private void retourHome() {
        // Gérer la logique de retour à l'écran d'accueil
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void RetourHome(ActionEvent event) {
        try {
            // Charger le fichier FXML de votre interface Home
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée depuis le fichier FXML
            Scene scene = new Scene(root);

            // Obtenir la fenêtre principale à partir de l'événement
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre principale
            stage.setScene(scene);
            stage.show(); // Afficher la nouvelle scène

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}