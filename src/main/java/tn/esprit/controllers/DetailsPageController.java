package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Reclamation;

import java.io.IOException;

public class DetailsPageController {

    @FXML
    private TextField id;

    @FXML
    private TextField objet;

    @FXML
    private TextField description;

    @FXML
    private TextField date;

    @FXML
    private TextField auteur;

    @FXML
    private TextField email;

    @FXML
    private TextField statut;

    @FXML
    private Button retour;

    public void initData(Reclamation details) {
        id.setText(String.valueOf(details.getId()));
        objet.setText(details.getObject().getNom());
        description.setText(details.getDescription());
        date.setText(details.getDate_creation().toString());
        auteur.setText(details.getAuteur());
        email.setText(details.getEmail());
        statut.setText(details.isStatus() ? "Active" : "Inactive");
    }

    public void RetourList(ActionEvent event) {
        try {
            // Charger le fichier FXML de votre interface Home
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListReclamation.fxml"));
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

