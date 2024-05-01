package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tn.esprit.models.ObjetReclamation;
import tn.esprit.models.Reclamation;
import tn.esprit.services.ReclamationService;
import org.controlsfx.control.Notifications;


import java.io.IOException;
import java.sql.Timestamp;


public class AddReclamation {

    @FXML
    private ComboBox<ObjetReclamation> objectComboBox;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField auteurField;

    @FXML
    private TextField emailField;

    @FXML
    public void initialize() {
        ObservableList<ObjetReclamation> options = FXCollections.observableArrayList(
                new ObjetReclamation("Frais Bancaires Inattendus"),
                new ObjetReclamation("Blocage de Carte Bancaire"),
                new ObjetReclamation("Virement Non Reçu")
        );
        objectComboBox.setItems(options);
    }

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
            Notifications notificationBuilder = Notifications.create()
                    .title("Réclamation ajoutée !")
                    .text("Votre réclamation est ajoutée avec sucées.")
                    .graphic(new ImageView())
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT);
            notificationBuilder.showInformation();


            System.out.println("Réclamation ajoutée : " + objReclamation + " - " + description + " - " + auteur + " - " + email);
        } catch (IllegalArgumentException e) {
            // Gérer les erreurs de validation
            System.err.println("Erreur : " + e.getMessage());
        }
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