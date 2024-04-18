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
import tn.esprit.models.Credit;
import tn.esprit.models.ReponseCredit;
import tn.esprit.services.CreditService;
import tn.esprit.services.ReponseCreditService;

import java.io.IOException;
import java.sql.Date;

public class AjouterReponseCredit {
    @FXML
    private DatePicker DateConfirm;

    @FXML
    private TextField DescriptionconfirmTF;

    @FXML
    private TextField SoldeàpTF;

    @FXML
    private TextField nbrconfirmTF;
    private final ReponseCreditService cs = new ReponseCreditService();

    @FXML
    void AjouterRC(ActionEvent event) {
        try{
            cs.Add(new ReponseCredit(Integer.parseInt(nbrconfirmTF.getText()),DescriptionconfirmTF.getText(),Float.parseFloat(SoldeàpTF.getText()), Date.valueOf(DateConfirm.getValue())));
            showAlert(Alert.AlertType.INFORMATION, "Success", "Leave request added successfully.");
            clearFields();

            // } catch (IllegalArgumentException e) {
            //     System.out.println("Erreur : " + e.getMessage());
        }catch (Exception f) {
            showAlert(Alert.AlertType.ERROR, "Error", f.getMessage());

        }
    }
    @FXML
    void RetourReponseLV(ActionEvent event) {
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
    private void clearFields() {
        DateConfirm.setValue(null);
        DescriptionconfirmTF.clear();
        nbrconfirmTF.clear();
        SoldeàpTF.clear();
    }
}
