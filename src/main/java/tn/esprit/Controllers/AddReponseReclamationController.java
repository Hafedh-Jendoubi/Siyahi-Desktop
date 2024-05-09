package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import tn.esprit.interfaces.ReclService;
import tn.esprit.models.Reclamation;
import tn.esprit.models.ReponseReclamation;
import tn.esprit.services.ReclamationService;
import tn.esprit.services.ReponseReclamationService;
import tn.esprit.util.EmailService;

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

    private ReclService<ReponseReclamation> reponseReclamationService;
    private Reclamation selectedReclamation;

    public String ObjectReclamation;

    public void setReponseReclamationService(ReclService<ReponseReclamation> reponseReclamationService) {
        this.reponseReclamationService = reponseReclamationService;
    }

    public void setSelectedReclamation(Reclamation selectedReclamation) {
        this.selectedReclamation = selectedReclamation;
    }

    public void initData(int id) {
        reclamationIdField.setOpacity(0);
        reclamationIdField.setText(String.valueOf(id));
        ReclamationService rs = new ReclamationService();
        Reclamation reclamation = rs.getOne(id);
        ObjectReclamation = String.valueOf(reclamation.getObject());
    }

    @FXML
    private void ajouterReponseReclamation(ActionEvent event) {
        // Récupérer les valeurs des champs
        String description = descriptionField.getText();
        String auteur = auteurField.getText();

        // Vérifier si les champs ne sont pas vides
        if (description.isEmpty() ||  auteur.isEmpty()) {
            // Afficher un message d'erreur ou gérer de manière appropriée si un champ est vide
            System.out.println("Veuillez remplir tous les champs.");
            return;
        }

        // Effectuer les opérations nécessaires pour ajouter une réponse à la réclamation
        // Par exemple, vous pouvez appeler une méthode dans votre service ou DAO pour effectuer l'ajout dans la base de données

        ReponseReclamationService rs = new ReponseReclamationService();
        rs.add(new ReponseReclamation(descriptionField.getText(), Integer.parseInt(reclamationIdField.getText()), 2, new Timestamp(System.currentTimeMillis()), auteurField.getText()));

        if(ObjectReclamation.equals("Demande Extrait")){
            EmailService es = new EmailService();
            es.sendEmail("ggliheb@gmail.com", "Test Email", "<h1>This is a test email</h1>");
        }else{
            System.out.println("GG");
        }

        RetourHome(event);
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
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();


    }
}