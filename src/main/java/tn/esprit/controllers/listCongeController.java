package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import tn.esprit.models.Conge;
import tn.esprit.services.CongeService; // Assurez-vous d'avoir une classe CongeService pour récupérer les données


import java.io.IOException;
import java.util.Optional;

public class listCongeController {

    @FXML
    private ListView<Conge> listViewConge;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    private CongeService congeService = new CongeService(); // Changez CongeService avec votre service réel

    @FXML
    public void initialize() {
        // Récupérer les données de congé et les lier au ListView
        ObservableList<Conge> conges = FXCollections.observableArrayList(congeService.getAll());
        listViewConge.setItems(conges);
    }
    @FXML
    void modifierConge(ActionEvent event) {
        Conge selectedConge = listViewConge.getSelectionModel().getSelectedItem();

        if (selectedConge == null) {
            // Aucun congé sélectionné, afficher un message d'erreur
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun congé sélectionné", "Veuillez sélectionner un congé à modifier.");
            return;
        }

        try {
            // Charger la vue de modification du congé
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditConge.fxml"));
            Parent edit = loader.load();

            // Passer le congé sélectionné au contrôleur de la vue de modification
            editCongeController editCongeController = loader.getController();
            editCongeController.initData(selectedConge);

            // Afficher la fenêtre de modification
            Scene editScene = new Scene(edit);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(editScene);
            window.setHeight(515); window.setMaxHeight(515); window.setMinHeight(515);
            window.setWidth(530); window.setMaxWidth(530); window.setMinWidth(530);
            window.setTitle("Siyahi Bank | Modifier un congé");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void supprimerConge(ActionEvent event) {
        CongeService cs = new CongeService();
        Conge selectedConge = listViewConge.getSelectionModel().getSelectedItem();

        if (selectedConge == null) {
            // Aucun congé sélectionné, afficher un message d'erreur
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun congé sélectionné", "Veuillez sélectionner un congé à supprimer.");
            return;
        }
        if (selectedConge.isStatus()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Impossible de supprimer le congé", "Ce congé a déjà été traité et ne peut pas être supprimé.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer le conge suivant ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            cs.delete(listViewConge.getSelectionModel().getSelectedItem());
            listViewConge.getItems().remove(listViewConge.getSelectionModel().getSelectedItem());
            showSuccessMessage("Congé supprimé avec succès!");
        } else {
            alert.close();
        }
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void ajoutConge(ActionEvent event) {
        try {
            Parent ajout = FXMLLoader.load(getClass().getResource("/AddConge.fxml"));
            Scene ajoutSecene = new Scene(ajout);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajoutSecene);
            window.setHeight(515); window.setMaxHeight(515); window.setMinHeight(515);
            window.setWidth(600); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Ajouter un congé");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void calendrier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/calendrier.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {

        }
    }
    @FXML
    void edit(ActionEvent event) {
        Conge selectedConge = listViewConge.getSelectionModel().getSelectedItem();

        if (selectedConge == null) {
            // Aucun congé sélectionné, afficher un message d'erreur
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun congé sélectionné", "Veuillez sélectionner un congé à modifier.");
            return;
        }
        if (selectedConge.isStatus()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Impossible de modifier le congé", "Ce congé a déjà été traité et ne peut pas être modifié.");
            return;
        }

        try {
            // Charger la vue de modification du congé
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modfierConge.fxml"));
            Parent edit = loader.load();

            // Passer le congé sélectionné au contrôleur de la vue de modification
            modifierCongeController modifierCongeController = loader.getController();
            modifierCongeController.initData1(selectedConge);

            // Afficher la fenêtre de modification
            Scene editScene = new Scene(edit);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(editScene);
            window.setHeight(515); window.setMaxHeight(515); window.setMinHeight(515);
            window.setWidth(600); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Reponse à un congé");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


}