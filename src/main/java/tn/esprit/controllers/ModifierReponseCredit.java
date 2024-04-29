package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Credit;
import tn.esprit.models.ReponseCredit;
import tn.esprit.services.CreditService;
import tn.esprit.services.ReponseCreditService;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class ModifierReponseCredit {
    @FXML
    private TextField DescriptionconfirmTFM;
    @FXML
    private DatePicker DateConfirmM;
    @FXML
    private TextField nbrconfirmTFM;
    @FXML
    private TextField SoldeàpTFM;
    @FXML
    private TextField autoFinancementTFM;

    @FXML
    private ComboBox<Credit> ReferenceCredit;

    private ReponseCredit selectedReponseCredit;
    private final ReponseCreditService rcs = new ReponseCreditService();
    private final CreditService creditService = new CreditService();

    @FXML
    void initialize() {
        loadCredits();
    }

    private void loadCredits() {
        List<Credit> credits = creditService.getAll();
        ObservableList<Credit> creditList = FXCollections.observableArrayList(credits);
        ReferenceCredit.setItems(creditList);
    }

    @FXML
    void initData(ReponseCredit reponsecredit) {
        selectedReponseCredit = reponsecredit;
        DescriptionconfirmTFM.setText(selectedReponseCredit.getDescription());
        DateConfirmM.setValue(selectedReponseCredit.getDate_debut_paiement().toLocalDate());
        nbrconfirmTFM.setText(String.valueOf(selectedReponseCredit.getNbr_mois_paiement()));
        SoldeàpTFM.setText(String.valueOf(selectedReponseCredit.getSolde_a_payer()));
        autoFinancementTFM.setText(String.valueOf(selectedReponseCredit.getauto_financement()));
        ReferenceCredit.setValue(creditService.getOne(selectedReponseCredit.getCredit_id()));
    }

    @FXML
    void ModifierRC(ActionEvent event) {
        try {
            Credit selectedCredit = ReferenceCredit.getValue();
            if (selectedCredit == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Veuillez sélectionner un crédit.");
                return;
            }

            selectedReponseCredit.setDescription(DescriptionconfirmTFM.getText());
            selectedReponseCredit.setDate_debut_paiement(Date.valueOf(DateConfirmM.getValue()));
            selectedReponseCredit.setNbr_mois_paiement(Integer.parseInt(nbrconfirmTFM.getText()));
            selectedReponseCredit.setSolde_a_payer(Float.parseFloat(SoldeàpTFM.getText()));
            selectedReponseCredit.setauto_financement(Float.parseFloat(autoFinancementTFM.getText()));
            selectedReponseCredit.setCredit_id(selectedCredit.getId());

            rcs.Update(selectedReponseCredit);
            showAlert(Alert.AlertType.INFORMATION, "Modification réussie", null, "Les modifications ont été enregistrées avec succès.");
            RetourReponseMLV(event);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification", e.getMessage());
        }
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


