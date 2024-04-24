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
import tn.esprit.models.TypeCredit;
import tn.esprit.services.CreditService;
import tn.esprit.services.TypeCreditService;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class ModifierCredit {
    @FXML
    private TextField DescriptionTFM;
    @FXML
    private DatePicker DateTFM;
    @FXML
    private TextField NbrTFM;
    @FXML
    private TextField SoldeTFM;
    @FXML
    private TextField ContratTFM;
    @FXML
    private ComboBox<TypeCredit> TypeCreditCB; // ComboBox for selecting credit types

    private Credit selectedCredit;
    private final CreditService cs = new CreditService();
    private final TypeCreditService typeCreditService = new TypeCreditService();

    @FXML
    void initialize() {
        loadCredits();
    }

    private void loadCredits() {
        // Load credit types into the ComboBox
        List<TypeCredit> typeCredits = typeCreditService.getAll();
        ObservableList<TypeCredit> typeCreditList = FXCollections.observableArrayList(typeCredits);
        TypeCreditCB.setItems(typeCreditList);
    }
    @FXML
    void initData(Credit credit) {
        selectedCredit = credit;
        DescriptionTFM.setText(selectedCredit.getDescription());
        DateTFM.setValue(selectedCredit.getDate_debut_paiement().toLocalDate());
        NbrTFM.setText(String.valueOf(selectedCredit.getNbr_mois_paiement()));
        SoldeTFM.setText(String.valueOf(selectedCredit.getSolde_demande()));
        ContratTFM.setText(selectedCredit.getContrat());
        TypeCreditCB.setValue(typeCreditService.getOne(selectedCredit.getType_credit_id()));
    }




    @FXML
    void modifierC(ActionEvent event) {
        try {
            selectedCredit.setDescription(DescriptionTFM.getText());
            selectedCredit.setDate_debut_paiement(Date.valueOf(DateTFM.getValue()));
            selectedCredit.setNbr_mois_paiement(Integer.parseInt(NbrTFM.getText()));
            selectedCredit.setSolde_demande(Float.parseFloat(SoldeTFM.getText()));
            selectedCredit.setContrat(ContratTFM.getText());

            TypeCredit selectedTypeCredit = TypeCreditCB.getSelectionModel().getSelectedItem();
            if (selectedTypeCredit != null) {
                selectedCredit.setType_credit_id(selectedTypeCredit.getId());
            }

            cs.Update(selectedCredit);
            showAlert(Alert.AlertType.INFORMATION, "Modification réussie", null, "Les modifications ont été enregistrées avec succès.");
            RetourMLV(event);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification", e.getMessage());
        }
    }

    @FXML
    void RetourMLV(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListCredit.fxml"));
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

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
