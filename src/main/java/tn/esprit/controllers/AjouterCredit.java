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

public class AjouterCredit {

    @FXML
    private TextField ContratTF;

    @FXML
    private DatePicker DateTF;

    @FXML
    private TextField DescriptionTF;

    @FXML
    private TextField NbrTF;

    @FXML
    private TextField SoldeTF;

    @FXML
    private ComboBox<TypeCredit> TypeCreditCB; // ComboBox for selecting credit types

    private final CreditService creditService = new CreditService();
    private final TypeCreditService typeCreditService = new TypeCreditService();

    @FXML
    void initialize() {
        // Load credit types into the ComboBox
        List<TypeCredit> typeCredits = typeCreditService.getAll();
        ObservableList<TypeCredit> typeCreditList = FXCollections.observableArrayList(typeCredits);
        TypeCreditCB.setItems(typeCreditList);
    }

    @FXML
    void AjouterC(ActionEvent event) {
        try {
            // Retrieve selected type credit from the ComboBox
            TypeCredit selectedTypeCredit = TypeCreditCB.getSelectionModel().getSelectedItem();
            if (selectedTypeCredit == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a credit type.");
                return;
            }

            Credit credit = new Credit();
            credit.setNbr_mois_paiement(Integer.parseInt(NbrTF.getText()));
            credit.setDescription(DescriptionTF.getText());
            credit.setContrat(ContratTF.getText());
            credit.setSolde_demande(Float.parseFloat(SoldeTF.getText()));
            credit.setDate_debut_paiement(Date.valueOf(DateTF.getValue()));
            credit.setType_credit_id(selectedTypeCredit.getId()); // Set the selected type credit ID

            creditService.Add(credit);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Credit added successfully.");
            clearFields();
            RetourLV(event);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    void RetourLV(ActionEvent event) {
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

    private void clearFields() {
        DateTF.setValue(null);
        DescriptionTF.clear();
        NbrTF.clear();
        SoldeTF.clear();
        ContratTF.clear();
        TypeCreditCB.getSelectionModel().clearSelection(); // Clear the ComboBox selection
    }
}
