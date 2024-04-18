package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.models.Conge;
import tn.esprit.models.ReponseConge;
import tn.esprit.services.CongeService;
import tn.esprit.services.ReponseCongeService;

import java.sql.Date;

public class editReponseCongeController {
    private final ReponseCongeService rs = new ReponseCongeService();
    private ReponseConge selectedrConge;
    @FXML
    private TextField description;
    @FXML
    private DatePicker datedebut;
    @FXML
    private DatePicker datefin;
    public void initData1(ReponseConge rcongé) {
        this.selectedrConge = rcongé;
        // Afficher les données du congé dans les champs de la vue de modification
        description.setText(rcongé.getDescription());
        datedebut.setValue(rcongé.getDate_debut().toLocalDate());
        datefin.setValue(rcongé.getDate_fin().toLocalDate());


    }

    // Méthode appelée lors de la sauvegarde des modifications
    @FXML
    void modifierR() {
        // Mettre à jour les données du congé sélectionné avec les nouvelles valeurs des champs
        selectedrConge.setDescription(description.getText());
        selectedrConge.setDate_debut(Date.valueOf(datedebut.getValue()));
        selectedrConge.setDate_fin(Date.valueOf(datefin.getValue()));



        // Call CongeService to add the leave request
        rs.update(selectedrConge);


        // Afficher un message de succès
        showAlert(Alert.AlertType.INFORMATION, "Modification réussie", null, "Les modifications ont été enregistrées avec succès.");
    }

    // Fonction utilitaire pour afficher une boîte de dialogue d'alerte
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
