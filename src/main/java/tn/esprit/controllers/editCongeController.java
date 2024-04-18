package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private final CongeService cs = new CongeService();
    public void initData(Conge congé) {
        this.selectedConge = congé;
        // Afficher les données du congé dans les champs de la vue de modification
        descriptionTF.setText(congé.getDescription());
        datedebutTF.setValue(congé.getDate_Debut().toLocalDate());
        datefinTF.setValue(congé.getDate_Fin().toLocalDate());
        type_congeTF.setText(congé.getType_conge());
        justificationTF.setText(congé.getJustification());

    }

    // Méthode appelée lors de la sauvegarde des modifications
    @FXML
    void modifierC() {
        // Mettre à jour les données du congé sélectionné avec les nouvelles valeurs des champs
        selectedConge.setDescription(descriptionTF.getText());
        selectedConge.setDate_Debut(Date.valueOf(datedebutTF.getValue()));
        selectedConge.setDate_Fin(Date.valueOf(datefinTF.getValue()));
        selectedConge.setType_conge(type_congeTF.getText());
        selectedConge.setJustification(justificationTF.getText());
        selectedConge.setStatus(statusCB.isSelected());


        // Call CongeService to add the leave request
        cs.update(selectedConge);


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
