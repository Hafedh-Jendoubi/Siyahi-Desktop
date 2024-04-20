package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import tn.esprit.models.Conge;
import tn.esprit.services.CongeService;

import java.io.IOException;
import java.sql.Date;

public class AddCongeController {
    @FXML
    private DatePicker datedebutTF;

    @FXML
    private DatePicker datefinTF;

    @FXML
    private TextField descriptionTF;
    @FXML
    private ComboBox<String> type_congeTF;

    @FXML
    private CheckBox status;
    @FXML
    private TextField justification;

    private final CongeService cs = new CongeService();


    @FXML
    void ajouterC(ActionEvent event) {
        // Check if any of the required fields are empty
        if (datedebutTF.getValue() == null || datefinTF.getValue() == null  || type_congeTF.getValue().isEmpty()  ) {
            showAlert(AlertType.ERROR, "Error", "Please fill in all required fields.");
            return;
        }

        try {
            // Extract data from input fields
            Date dateDebut = Date.valueOf(datedebutTF.getValue());
            Date dateFin = Date.valueOf(datefinTF.getValue());
            String description = descriptionTF.getText();
            String typeConge = type_congeTF.getValue();
            String justificationText = justification.getText();
            boolean stat = status.isSelected();

            // Create a Conge object
            Conge conge = new Conge(description, dateDebut, dateFin, typeConge, justificationText, stat);

            // Call CongeService to add the leave request
            cs.add(conge);

            // Show success message
            showAlert(AlertType.INFORMATION, "Success", "Leave request added successfully.");

            // Clear input fields
            clearFields();
        } catch (Exception e) {
            // Show error message if any exception occurs
            showAlert(AlertType.ERROR, "Error", e.getMessage());
        }
    }
    // Helper method to show alerts
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Helper method to clear input fields after submission
    private void clearFields() {
        datedebutTF.setValue(null);
        datefinTF.setValue(null);
        descriptionTF.clear();
        datefinTF.setValue(null);
        justification.clear();
        status.setSelected(false);
    }
    @FXML
    void retourLV(ActionEvent event) {
        try {
            Parent retour = FXMLLoader.load(getClass().getResource("/ListConge.fxml"));
            Scene listSecene = new Scene(retour);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(listSecene);
            window.setHeight(400); window.setMaxHeight(400); window.setMinHeight(400);
            window.setWidth(606); window.setMaxWidth(600); window.setMinWidth(600);
            window.setTitle("Siyahi Bank | liste des   congés ");
            window.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

}

