package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import tn.esprit.models.Reclamation;

import java.net.URL;
import java.util.ResourceBundle;

public class ReclamationDetailsController implements Initializable {

    @FXML
    private ListView<String> listViewReclamationDetails;

    private Reclamation reclamation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set up the ListView to display the reclamation details
        ObservableList<String> reclamationDetails = FXCollections.observableArrayList();
        reclamationDetails.add("ID: " + reclamation.getId());
        reclamationDetails.add("Object: " + reclamation.getObject());
        reclamationDetails.add("Description: " + reclamation.getDescription());
        reclamationDetails.add("Date Creation: " + reclamation.getDate_creation());
        reclamationDetails.add("Auteur: " + reclamation.getAuteur());
        reclamationDetails.add("Email: " + reclamation.getEmail());

        listViewReclamationDetails.setItems(reclamationDetails);
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }
}