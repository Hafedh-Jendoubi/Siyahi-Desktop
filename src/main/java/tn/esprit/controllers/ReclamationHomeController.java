package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

import static tn.esprit.services.UserService.connectedUser;

public class ReclamationHomeController {

    @FXML
    private VBox chatSection;

    @FXML
    private Button onlineService;

    @FXML
    private Button chat1But;

    @FXML
    void initialize(){
        if(connectedUser.getRoles().equals("Client")){
            chat1But.setOpacity(1); onlineService.setOpacity(0);
        }else{
            chat1But.setOpacity(0); onlineService.setOpacity(1);
        }
    }

    @FXML
    private void addNewReclamation(ActionEvent event) {
        try {
            // Charger le fichier FXML de votre autre interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddReclamation.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée depuis le fichier FXML
            Scene scene = new Scene(root);

            // Obtenir la fenêtre principale à partir de l'événement
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre principale
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showReclamationList(ActionEvent event) {
        // Ajoutez ici le code pour gérer l'action "Show Reclamation List"
    }

    public void searchReclamations(ActionEvent event) {
    }

    public void navigateToAdd(ActionEvent event) {
        try {
            // Charger le fichier FXML de votre autre interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddReclamation.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée depuis le fichier FXML
            Scene scene = new Scene(root);

            // Obtenir la fenêtre principale à partir de l'événement
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre principale
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToReclamations(ActionEvent event) {
        try {
            // Charger le fichier FXML de votre autre interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListReclamation.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée depuis le fichier FXML
            Scene scene = new Scene(root);

            // Obtenir la fenêtre principale à partir de l'événement
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre principale
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToContact(ActionEvent event) {
    }

    public void navigateToAdmin(ActionEvent event) {
        try {
            // Charger le fichier FXML de votre autre interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddReponseReclamation.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée depuis le fichier FXML
            Scene scene = new Scene(root);

            // Obtenir la fenêtre principale à partir de l'événement
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre principale
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigatetochatserver(ActionEvent event) {
        try {
            // Charger le fichier FXML de votre autre interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChatServerGUI.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée depuis le fichier FXML
            Scene scene = new Scene(root);

            // Obtenir la fenêtre principale à partir de l'événement
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre principale
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToclientchat(ActionEvent event) {
        try {
            // Charger le fichier FXML de votre autre interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChatClientGUI.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée depuis le fichier FXML
            Scene scene = new Scene(root);

            // Obtenir la fenêtre principale à partir de l'événement
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre principale
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
