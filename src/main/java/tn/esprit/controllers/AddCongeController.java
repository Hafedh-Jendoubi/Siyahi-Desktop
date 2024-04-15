package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import tn.esprit.models.Conge;
import tn.esprit.services.CongeService;
import java.sql.Date;

public class AddCongeController {
    @FXML
    private DatePicker datedebutTF;

    @FXML
    private DatePicker datefinTF;

    @FXML
    private TextField descriptionTF;
    @FXML
    private TextField type_congeTF;

    @FXML
    private CheckBox status;
    @FXML
    private TextField justification;

    private final CongeService cs = new CongeService();


    @FXML
    void ajouterC(ActionEvent event) {
        // Check if any of the required fields are empty
        if (datedebutTF.getValue() == null || datefinTF.getValue() == null || descriptionTF.getText().isEmpty() || type_congeTF.getText().isEmpty()  ) {
            showAlert(AlertType.ERROR, "Error", "Please fill in all required fields.");
            return;
        }

        try {
            // Extract data from input fields
            Date dateDebut = Date.valueOf(datedebutTF.getValue());
            Date dateFin = Date.valueOf(datefinTF.getValue());
            String description = descriptionTF.getText();
            String typeConge = type_congeTF.getText();
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
        type_congeTF.clear();
        justification.clear();
        status.setSelected(false);
    }

}

