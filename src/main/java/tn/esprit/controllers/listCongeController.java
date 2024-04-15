package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        try {
            Parent edit = FXMLLoader.load(getClass().getResource("/EditConge.fxml"));
            Scene editSecene = new Scene(edit);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(editSecene);
            window.setHeight(400); window.setMaxHeight(400); window.setMinHeight(400);
            window.setWidth(606); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Modifier un congé");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void supprimerConge(ActionEvent event) {
        CongeService cs = new CongeService();
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
            Parent ajout = FXMLLoader.load(getClass().getResource("/EditConge.fxml"));
            Scene ajoutSecene = new Scene(ajout);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajoutSecene);
            window.setHeight(400); window.setMaxHeight(400); window.setMinHeight(400);
            window.setWidth(606); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | Ajouter un congé");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}