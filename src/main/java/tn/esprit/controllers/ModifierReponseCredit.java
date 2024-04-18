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
import tn.esprit.models.ReponseCredit;
import tn.esprit.services.ReponseCreditService;

import java.io.IOException;
import java.sql.Date;

public class ModifierReponseCredit {
    @FXML
    private TextField DescriptionconfirmTFM;
    @FXML
    private DatePicker DateConfirmM;
    @FXML
    private TextField nbrconfirmTFM;
    @FXML
    private TextField SoldeàpTFM;
    private ReponseCredit selectedReponseCredit;
    private final ReponseCreditService rcs = new ReponseCreditService();
    @FXML
    void initData(ReponseCredit reponsecredit) {
        selectedReponseCredit = reponsecredit;
        DescriptionconfirmTFM.setText(selectedReponseCredit.getDescription());
        DateConfirmM.setValue(selectedReponseCredit.getDate_debut_paiement().toLocalDate());
        nbrconfirmTFM.setText(String.valueOf(selectedReponseCredit.getNbr_mois_paiement()));
        SoldeàpTFM.setText(String.valueOf(selectedReponseCredit.getSolde_a_payer()));
    }


    // Méthode appelée lors de la sauvegarde des modifications
    @FXML
    void ModifierRC() {
        selectedReponseCredit.setDescription(DescriptionconfirmTFM.getText());
        selectedReponseCredit.setDate_debut_paiement(Date.valueOf(DateConfirmM.getValue()));
        selectedReponseCredit.setNbr_mois_paiement(Integer.parseInt(nbrconfirmTFM.getText())); // Conversion en entier
        selectedReponseCredit.setSolde_a_payer(Float.parseFloat(SoldeàpTFM.getText())); // Conversion en flottant

        rcs.Update(selectedReponseCredit);
        // Afficher un message de succès
        showAlert(Alert.AlertType.INFORMATION, "Modification réussie", null, "Les modifications ont été enregistrées avec succès.");
    }

    @FXML
    void RetourReponseMLV(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListReponseCredit.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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