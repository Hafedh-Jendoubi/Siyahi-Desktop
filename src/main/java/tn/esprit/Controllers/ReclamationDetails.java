package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.models.Reclamation;

public class ReclamationDetails {

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

}
