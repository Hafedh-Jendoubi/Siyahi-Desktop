package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.models.TypeCredit;
import tn.esprit.services.TypeCreditService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ModifierTypeCredit {

    @FXML
    private TextField NomTypeCreditTFM;

    @FXML
    private TextField TauxCreditDirectTFM; // Champ pour le taux de crédit direct

    private TypeCredit selectedTypeCredit;
    private final TypeCreditService typeCreditService = new TypeCreditService();

    @FXML
    void initData(TypeCredit typeCredit) {
        selectedTypeCredit = typeCredit;
        NomTypeCreditTFM.setText(selectedTypeCredit.getNomTypeCredit());
        TauxCreditDirectTFM.setText(String.valueOf(selectedTypeCredit.getTauxCreditDirect())); // Affichage du taux de crédit direct
    }

    @FXML
    void ModifierTypeCredit(ActionEvent event) {
        try {
            selectedTypeCredit.setNomTypeCredit(NomTypeCreditTFM.getText());
            // Mise à jour du taux de crédit direct
            selectedTypeCredit.setTauxCreditDirect(Float.parseFloat(TauxCreditDirectTFM.getText()));
            typeCreditService.Update(selectedTypeCredit);
            showAlert(Alert.AlertType.INFORMATION, "Modification réussie", null, "Le type de crédit a été modifié avec succès.");
            RetourTLV(event);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer un nombre valide pour le taux de crédit direct.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification", e.getMessage());
        }
    }

    @FXML
    void RetourTLV(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListTypeCredit.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", e.getMessage());
        }
    }

    // Fonction utilitaire pour afficher une boîte de dialogue d'alerte
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Fonction utilitaire pour afficher une boîte de dialogue d'alerte avec en-tête
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
