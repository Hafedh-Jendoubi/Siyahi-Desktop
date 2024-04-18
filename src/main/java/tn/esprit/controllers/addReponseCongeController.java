package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.ReponseConge;
import tn.esprit.services.ReponseCongeService;

import java.io.IOException;
import java.sql.Date;

import java.sql.Date;

public class addReponseCongeController {
    @FXML
    private DatePicker datedebut;

    @FXML
    private DatePicker datefin;

    @FXML
    private TextField Description;
    private final ReponseCongeService rs = new ReponseCongeService();


    @FXML
    void ajouterR(ActionEvent event) {
        // Check if any of the required fields are empty
        if (datedebut.getValue() == null || datefin.getValue() == null  || Description.getText().isEmpty()  ) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all required fields.");
            return;
        }

        try {
            // Extract data from input fields
            Date dateDebut = Date.valueOf(datedebut.getValue());
            Date dateFin = Date.valueOf(datefin.getValue());
            String description = Description.getText();

            // Create a Conge object
            ReponseConge rconge = new ReponseConge(description, dateDebut, dateFin);

            // Call CongeService to add the leave request
            rs.add(rconge);

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Leave request added successfully.");

            // Clear input fields
            clearFields();
        } catch (Exception e) {
            // Show error message if any exception occurs
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
    // Helper method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Helper method to clear input fields after submission
    private void clearFields() {
        datedebut.setValue(null);
        datefin.setValue(null);
        Description.clear();

    }
    @FXML
    void retourLV(ActionEvent event) {
        try {
            Parent retour = FXMLLoader.load(getClass().getResource("/ListReponseConge.fxml"));
            Scene listSecene = new Scene(retour);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(listSecene);
            window.setHeight(400); window.setMaxHeight(400); window.setMinHeight(400);
            window.setWidth(606); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | liste des  reponses congé");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        }
}
