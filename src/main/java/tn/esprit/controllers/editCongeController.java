package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Conge;
import tn.esprit.services.CongeService;

import java.sql.Date;

public class editCongeController {
    @FXML
    private TextField descriptionTF;
    @FXML
    private DatePicker datedebutTF;
    @FXML
    private DatePicker datefinTF;
    @FXML
    private TextField type_congeTF;
    @FXML
    private TextField justificationTF;
    @FXML
    private CheckBox statusCB;

    private Conge selectedConge;

    public void initData(Conge conge) {
        selectedConge = conge;
        // Populate fields with congé details for editing
        descriptionTF.setText(selectedConge.getDescription());
        datedebutTF.setValue(selectedConge.getDate_Debut().toLocalDate());
        datefinTF.setValue(selectedConge.getDate_Fin().toLocalDate());
        type_congeTF.setText(selectedConge.getType_conge());
        justificationTF.setText(selectedConge.getJustification());
        statusCB.setSelected(selectedConge.isStatus());
    }

    @FXML
    private void modifierC(ActionEvent event) {
        // Update congé details with edited values
        selectedConge.setDescription(descriptionTF.getText());
        selectedConge.setDate_Debut(Date.valueOf(datedebutTF.getValue()));
        selectedConge.setDate_Fin(Date.valueOf(datefinTF.getValue()));
        selectedConge.setType_conge(type_congeTF.getText());
        selectedConge.setJustification(justificationTF.getText());
        selectedConge.setStatus(statusCB.isSelected());

        // Call the update method in CongeService to update the congé in the database
        CongeService congeService = new CongeService();
        congeService.update(selectedConge);

        // Close the window after modification
        ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
    }
}
