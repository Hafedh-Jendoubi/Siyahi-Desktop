package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import tn.esprit.models.limitationConge;
import tn.esprit.services.CongeService;
import tn.esprit.services.limitationcongeService;
import java.sql.Date;

public class EditLimitController {
    @FXML
    private TextField annee;

    @FXML
    private TextField nbrTF;


    @FXML
    private ComboBox<String> mois;
    private limitationConge selectedConge;
    private final limitationcongeService cs = new limitationcongeService();
    public void initData2(limitationConge congé) {
        this.selectedConge = congé;
        // Afficher les données du congé dans les champs de la vue de modification
        annee.setText(String.valueOf(congé.getAnnee()));
        mois.setValue(congé.getMois());
        nbrTF.setText(String.valueOf(congé.getNbr_jours()));


    }

    // Méthode appelée lors de la sauvegarde des modifications
    @FXML
    void modifier() {
        // Mettre à jour les données du congé sélectionné avec les nouvelles valeurs des champs
        selectedConge.setAnnee(Integer.parseInt(annee.getText()));
        selectedConge.setMois(mois.getValue());
        selectedConge.setNbr_jours(Integer.parseInt(nbrTF.getText()));



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

